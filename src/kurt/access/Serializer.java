package kurt.access;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static kurt.access.NumberType.*;

class Serializer implements Field.Visitor<byte[]> {
    private final static Validator VALIDATOR = new Validator();
    private final static byte CONT_MARKER = (byte)0xFF;
    private final static byte TERMINATE = (byte)0xE0;
    private Map<FieldType, Field> fields;
    private final Path path;
    private User user;

    public Serializer(String path) {
        this.path = Paths.get(path);
    }

    /**
     * Completely rewrites file with new values.
     *
     * @param numOfUsers The number of users you wish to
     * rewrite into this file.
     */
    public void initialize(int numOfUsers) throws IOException {
        Files.write(path, initHeader(numOfUsers));
    }

    public void write(User user) throws IOException {
        this.user = user;

        List<byte[]> bytes = fieldsToBytes();
        for (byte[] bite : bytes)
            Files.write(path, bite, StandardOpenOption.APPEND);
    }

    private List<byte[]> fieldsToBytes() {
        List<byte[]> bytes = new ArrayList<>();
        bytes.add(new byte[]{CONT_MARKER});
        for (Field field : user.getFieldMap().values())
            bytes.add(fieldToBytes(field));
        bytes.add(new byte[]{TERMINATE});
        return bytes;
    }

    private byte[] initHeader(int numOfUsers) {
        int len = "VFF02".length();
        byte[] header = new byte[len + 4];
        System.arraycopy("VFF02".getBytes(StandardCharsets.UTF_8), 0, header, 0, len);
        return intToBytes(header, 5, numOfUsers);
    }

    private byte[] fieldToBytes(Field field) {
        if (!VALIDATOR.validate(field)) return new byte[0];
        return field.accept(this);
    }

    @Override
    public byte[] visit(Field.Name field) {
        return createStringBytes((byte)0x80, field.value);
    }

    @Override
    public byte[] visit(Field.Password field) {
        return createStringBytes((byte)0x90, field.value);
    }

    @Override
    public byte[] visit(Field.DOB field) {
        return createStringBytes((byte)0xA0, field.value);
    }

    @Override
    public byte[] visit(Field.Email field) {
        return createStringBytes((byte)0xB0, field.value);
    }

    @Override
    public byte[] visit(Field.Reputation field) {
        int value = Float.floatToIntBits(field.value);
        byte[] bytes = new byte[FLOAT.sizeOf() + 1];
        bytes[0] = (byte)0xC0;
        return intToBytes(bytes, 1, value);
    }

    @Override
    public byte[] visit(Field.Posts field) {
        byte[] bytes = new byte[INT.sizeOf() + 1];
        bytes[0] = (byte)0xD0;
        return intToBytes(bytes, 1, field.value);
    }

    private byte[] intToBytes(byte[] bytes, int start, int value) {
        int shift = 32;
        while (start < bytes.length) {
            shift -= 8;
            bytes[start] = (byte)(value >>> shift);
            start++;
        }

        return bytes;
    }

    private byte[] createStringBytes(byte id, String string) {
        int len = string.length();
        byte[] value = new byte[len + 2];
        value[0] = id;
        value[1] = (byte)len;
        System.arraycopy(string.getBytes(StandardCharsets.UTF_8), 0, value, 2, len);
        return value;
    }
}