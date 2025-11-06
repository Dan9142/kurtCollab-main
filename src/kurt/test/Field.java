package kurt.test;

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

        final String value;
    }

    static class Password extends Field {
        Password(String value) { this.value = value; }

        @Override
        <V> V accept(Visitor<V> visitor) { return visitor.visit(this); }

        final String value;
    }

    static class DOB extends Field {
        DOB(String value) { this.value = value; }

        @Override
        <V> V accept(Visitor<V> visitor) { return visitor.visit(this); }

        final String value;
    }

    static class Email extends Field {
        Email(String value) { this.value = value; }

        @Override
        <V> V accept(Visitor<V> visitor) { return visitor.visit(this); }

        final String value;
    }

    static class Reputation extends Field {
        Reputation(double value) { this.value = value; }

        @Override
        <V> V accept (Visitor<V> visitor) { return visitor.visit(this); }

        final double value;
    }

    static class Posts extends Field {
        Posts(int value) { this.value = value; }

        @Override
        <V> V accept(Visitor<V> visitor) { return visitor.visit(this); }

        final int value;
    }

    abstract <V> V accept(Visitor<V> visitor);
}
