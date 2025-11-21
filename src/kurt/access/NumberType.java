package kurt.access;

public enum NumberType {
    INT(4), LONG(8),
    FLOAT(4), DOUBLE(8),
    BYTE(1);

    private final int size;

    NumberType(int size) {
        this.size = size;
    }

    public int sizeOf() {
        return size;
    }
}
