package kurt.test;

public enum FieldType {
    USERNAME("USERNAME"), PWD("PASSWORD"),
    DOB("DATE OF BIRTH"), EMAIL("EMAIL"),
    REP("REPUTATION"), POSTS("POSTS"),
    TERMINATE("TERMINATE");

    FieldType(String name) {
        this.name = name;
    }

    private final String name;

    public String nameOf() {
        return name;
    }
}
