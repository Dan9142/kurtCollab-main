package kurt.access;

import java.util.Objects;

public abstract class Field {
    public interface Visitor<V> {
        V visit(Name field);
        V visit(Password field);
        V visit(DOB field);
        V visit(Email field);
        V visit(Reputation field);
        V visit(Posts field);
    }

    public static class Name extends Field {
        public Name(String value) { this.value = value; }

        @Override
        public <V> V accept(Visitor<V> visitor) { return visitor.visit(this); }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Name)) return false;
            return Objects.equals(this.value, ((Name)obj).value);
        }

        final String value;
    }

    public static class Password extends Field {
        public Password(String value) { this.value = value; }

        @Override
        public <V> V accept(Visitor<V> visitor) { return visitor.visit(this); }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Password)) return false;
            return Objects.equals(this.value, ((Password)obj).value);
        }

        final String value;
    }

    public static class DOB extends Field {
        public DOB(String value) { this.value = value; }

        @Override
        public <V> V accept(Visitor<V> visitor) { return visitor.visit(this); }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof DOB)) return false;
            return Objects.equals(this.value, ((DOB)obj).value);
        }

        final String value;
    }

    public static class Email extends Field {
        public Email(String value) { this.value = value; }

        @Override
        public <V> V accept(Visitor<V> visitor) { return visitor.visit(this); }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Email)) return false;
            return Objects.equals(this.value, ((Email)obj).value);
        }

        final String value;
    }

    public static class Reputation extends Field {
        public Reputation(float value) { this.value = value; }

        @Override
        public <V> V accept (Visitor<V> visitor) { return visitor.visit(this); }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Reputation)) return false;
            return this.value == ((Reputation)obj).value;
        }

        final float value;
    }

    public static class Posts extends Field {
        public Posts(int value) { this.value = value; }

        @Override
        public <V> V accept(Visitor<V> visitor) { return visitor.visit(this); }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Posts)) return false;
            return this.value == ((Posts)obj).value;
        }

        final int value;
    }

    public abstract <V> V accept(Visitor<V> visitor);
}
