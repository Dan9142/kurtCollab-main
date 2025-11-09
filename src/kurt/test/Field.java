package kurt.test;

import java.util.Objects;

abstract class Field {
    interface Visitor<V> {
        V visit(Name field);
        V visit(Password field);
        V visit(DOB field);
        V visit(Email field);
        V visit(Reputation field);
        V visit(Posts field);
    }

    static class Name extends Field {
        Name(String value) { this.value = value; }

        @Override
        <V> V accept(Visitor<V> visitor) { return visitor.visit(this); }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Name)) return false;
            return Objects.equals(this.value, ((Name)obj).value);
        }

        final String value;
    }

    static class Password extends Field {
        Password(String value) { this.value = value; }

        @Override
        <V> V accept(Visitor<V> visitor) { return visitor.visit(this); }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Password)) return false;
            return Objects.equals(this.value, ((Password)obj).value);
        }

        final String value;
    }

    static class DOB extends Field {
        DOB(String value) { this.value = value; }

        @Override
        <V> V accept(Visitor<V> visitor) { return visitor.visit(this); }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof DOB)) return false;
            return Objects.equals(this.value, ((DOB)obj).value);
        }

        final String value;
    }

    static class Email extends Field {
        Email(String value) { this.value = value; }

        @Override
        <V> V accept(Visitor<V> visitor) { return visitor.visit(this); }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Email)) return false;
            return Objects.equals(this.value, ((Email)obj).value);
        }

        final String value;
    }

    static class Reputation extends Field {
        Reputation(float value) { this.value = value; }

        @Override
        <V> V accept (Visitor<V> visitor) { return visitor.visit(this); }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Reputation)) return false;
            return this.value == ((Reputation)obj).value;
        }

        final float value;
    }

    static class Posts extends Field {
        Posts(int value) { this.value = value; }

        @Override
        <V> V accept(Visitor<V> visitor) { return visitor.visit(this); }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Posts)) return false;
            return this.value == ((Posts)obj).value;
        }

        final int value;
    }

    abstract <V> V accept(Visitor<V> visitor);
}
