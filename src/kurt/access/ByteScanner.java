package kurt.access;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static kurt.access.NumberType.*;
import static kurt.access.ExitCode.*;

public class ByteScanner {
    protected static class ParseFailure extends RuntimeException {}

    private final ByteBuffer buf;
    private final int capacity;

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
    protected String asString(int length) {
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
    protected int asInt(NumberType type) {
        if (type.sizeOf() > buf.remaining())
            throw error(EXIT_TRUNCATED_FILE,
                    "Attempted to move " + (type.sizeOf() - buf.remaining()) + " bytes past buffer");
        if (type == INT) return buf.getInt();
        else return buf.get();
    }

    protected long asLong() {
        if (LONG.sizeOf() > buf.remaining())
            throw error(EXIT_TRUNCATED_FILE,
                    "Attempted to move " + (LONG.sizeOf() - buf.remaining()) + " bytes past buffer");
        return buf.getLong();
    }

    protected double asDouble() {
        if (DOUBLE.sizeOf() > buf.remaining())
            throw error(EXIT_TRUNCATED_FILE,
                    "Attempted to move " + (DOUBLE.sizeOf() - buf.remaining()) + " bytes past buffer");
        return buf.getDouble();
    }

    protected float asFloat() {
        if (FLOAT.sizeOf() > buf.remaining())
            throw error(EXIT_TRUNCATED_FILE,
                    "Attempted to move " + (FLOAT.sizeOf() - buf.remaining()) + " bytes past buffer");
        return buf.getFloat();
    }

    /**
     * Checks beginning of file to verify file header matches the target string.
     *
     * @param target The target string that the file header needs to match.
     */
    protected void verifyHeader(String target) {
        int strlen = target.length();
        String header = asString(strlen);
        if (!header.equals(target))
            throw error(EXIT_INVALID_FILE);
    }

    protected boolean isEnd() {
        return buf.position() >= capacity;
    }

    protected boolean match(byte value) {
        byte comp = buf.get(buf.position());
        return buf.get(buf.position()) == value; // Check current
    }

    protected byte next() {
        buf.position(buf.position() + 1); // Next
        return buf.get(buf.position() - 1);
    }

    protected int pos() {
        return buf.position();
    }

    /**
     * Handles parse level failures and calls Kurt's report method
     * with additional context.
     *
     * @param exitCode The exit code that needs to be reported.
     * @param context Additional context relating to the error.
     *
     * @return A ParseFailure exception that the caller can optionally throw.
     */
    protected ParseFailure error(ExitCode exitCode, String context) {
        Kurt.report(exitCode.toString(), context, pos());
        return new ParseFailure();
    }

    /**
     * Handles parse level failures and calls Kurt's report method.
     *
     * @param exitCode The exit code that needs to be reported.
     *
     * @return A ParseFailure exception that the caller can optionally throw.
     */
    protected ParseFailure error(ExitCode exitCode) {
        Kurt.report(exitCode.toString(), pos());
        return new ParseFailure();
    }
}
