package kurt.access;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static kurt.access.NumberType.*;
import static kurt.access.ExitCode.*;

public class ByteScanner {
    static class ParseFailure extends RuntimeException {}

    private final ByteBuffer buf;
    private final int capacity;
    private byte save = (byte)0x00;

    public ByteScanner(byte[] buffer) {
        this.buf = ByteBuffer.wrap(buffer);
        this.capacity = buf.limit();
    }

    /**
     * Creates a string from the target buffer if possible.
     *
     * @param length How many bytes the new string should be.
     * @return New string if there is enough space in the buffer or
     * null otherwise.
     */
    public String asString(int length) {
        if (length > buf.remaining())
            throw error(EXIT_TRUNCATED_FILE,
                    "Attempted to move " + (length - buf.remaining()) + " byte(s) past buffer.");
        byte[] string = new byte[length];
        buf.get(string, 0, length);
        return new String(string, StandardCharsets.UTF_8);
    }

    /**
     * Creates integer from current buffer position and returns
     * minimum value if there aren't enough bytes.
     *
     * @param type The type of integer to read.
     * @return An integer if there is enough bytes in the buffer.
     * Otherwise, the minimum integer value will be returned.
     * */
    public int asInt(NumberType type) {
        if (type.sizeOf() > buf.remaining())
            throw error(EXIT_TRUNCATED_FILE,
                    "Attempted to move " + (type.sizeOf() - buf.remaining()) + " bytes past buffer");
        if (type == INT) return buf.getInt();
        else return buf.get();
    }

    public long asLong() {
        if (LONG.sizeOf() > buf.remaining())
            throw error(EXIT_TRUNCATED_FILE,
                    "Attempted to move " + (LONG.sizeOf() - buf.remaining()) + " bytes past buffer");
        return buf.getLong();
    }

    public double asDouble() {
        if (DOUBLE.sizeOf() > buf.remaining())
            throw error(EXIT_TRUNCATED_FILE,
                    "Attempted to move " + (DOUBLE.sizeOf() - buf.remaining()) + " bytes past buffer");
        return buf.getDouble();
    }

    public float asFloat() {
        if (FLOAT.sizeOf() > buf.remaining())
            throw error(EXIT_TRUNCATED_FILE,
                    "Attempted to move " + (FLOAT.sizeOf() - buf.remaining()) + " bytes past buffer");
        return buf.getFloat();
    }

    public void verifyHeader(String target) {
        int strlen = target.length();
        String header = asString(strlen);
        if (!header.equals(target))
            throw error(EXIT_INVALID_FILE);
    }

    public boolean isEnd() {
        return buf.position() >= capacity;
    }

    public boolean match(byte value) {
        byte comp = buf.get(buf.position());
        return buf.get(buf.position()) == value; // Check current
    }

    public byte next() {
        buf.position(buf.position() + 1); // Next
        return buf.get(buf.position() - 1);
    }

    public void save(byte value) {
        this.save = value;
    }

    public byte retrieve() {
        return this.save;
    }

    public int pos() {
        return buf.position();
    }

    public void pos(int newPos) {
        if (newPos > buf.remaining())
            throw error(EXIT_TRUNCATED_FILE,
                    "Attempted to move " + (newPos - buf.remaining()) + " bytes past buffer");
        buf.position(newPos);
    }

    ParseFailure error(ExitCode exitCode, String context) {
        Kurt.report(exitCode.toString(), context, pos());
        return new ParseFailure();
    }

    ParseFailure error(ExitCode exitCode) {
        Kurt.report(exitCode.toString(), pos());
        return new ParseFailure();
    }
}
