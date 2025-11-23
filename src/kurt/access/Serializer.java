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
     * @param num The number of elements you wish to
     * rewrite into this file.
     */
    public void initialize(int num, String header) throws IOException {
        Files.write(path, initHeader(num, header));
    }

    public void write(List<Post> posts, String tag) throws IOException {
        List<byte[]> bytes = new ArrayList<>();
        bytes.add(new byte[]{CONT_MARKER});
        bytes.add(createStringBytes(tag));
        bytes.addAll(postsToBytes(posts));

        for (byte[] bite : bytes)
            Files.write(path, bite, StandardOpenOption.APPEND);
    }

    public void write(User user) throws IOException {
        this.user = user;

        List<byte[]> bytes = fieldsToBytes();
        for (byte[] bite : bytes)
            Files.write(path, bite, StandardOpenOption.APPEND);
    }

    private List<byte[]> postsToBytes(List<Post> posts) {
        List<byte[]> bytes = new ArrayList<>();

        for (Post post : posts) {
            bytes.add(new byte[]{(byte)0xF0});
            bytes.addAll(postToBytes(post));
        }

        return bytes;
    }

    private List<byte[]> fieldsToBytes() {
        List<byte[]> bytes = new ArrayList<>();
        bytes.add(new byte[]{CONT_MARKER});
        for (Field field : user.getFieldMap().values())
            bytes.add(fieldToBytes(field));
        bytes.add(new byte[]{TERMINATE});
        return bytes;
    }

    private byte[] initHeader(int num, String header) {
        int len = header.length();
        byte[] bytes = new byte[len + 4];
        System.arraycopy(header.getBytes(StandardCharsets.UTF_8), 0, bytes, 0, len);
        return intToBytes(bytes, len, num);
    }

    private List<byte[]> postToBytes(Post post) {
        List<byte[]> bytes = new ArrayList<>();

        bytes.add(createStringBytes(post.getPoster()));
        bytes.add(intToBytes(new byte[INT.sizeOf()], 0, post.getLength()));
        bytes.add(intToBytes(new byte[LONG.sizeOf()], 0, post.getOffset()));

        return bytes;
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

    private byte[] intToBytes(byte[] bytes, int start, long value) {
        int shift = 64;
        while (start < bytes.length) {
            shift -= 8;
            bytes[start] = (byte)(value >>> shift);
            start++;
        }

        return bytes;
    }

    private byte[] createStringBytes(String string) {
        int len = string.length();
        byte[] value = new byte[len + 1];
        value[0] = (byte)len;
        System.arraycopy(string.getBytes(StandardCharsets.UTF_8), 0, value, 1, len);
        return value;
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