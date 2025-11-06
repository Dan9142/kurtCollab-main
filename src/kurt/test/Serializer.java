package kurt.test;

import java.nio.charset.StandardCharsets;

class Serializer implements Field.Visitor<byte[]> {
    private final static Processor processor = new Processor();

    public byte[] serialize(Field field) {
        if (!processor.validate(field)) return new byte[0];
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
        return new byte[0];
    }

    @Override
    public byte[] visit(Field.Posts field) {
        return new byte[0];
    }

    private byte[] createStringBytes(byte id, String string) {
        byte[] src = string.getBytes(StandardCharsets.UTF_8);
        byte[] value = new byte[string.length() + 1];
        value[0] = id;
        System.arraycopy(src, 0, value, 1, src.length);
        return value;
    }
}
