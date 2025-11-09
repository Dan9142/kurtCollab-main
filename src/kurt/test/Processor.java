package kurt.test;

class Processor implements Field.Visitor<Object> {
    public Object process(Field field) {
        return field.accept(this);
    }

    @Override
    public Object visit(Field.Name field) {
        return field.value;
    }

    @Override
    public Object visit(Field.Password field) {
        return field.value;
    }

    @Override
    public Object visit(Field.DOB field) {
        return field.value;
    }

    @Override
    public Object visit(Field.Email field) {
        return field.value;
    }

    @Override
    public Object visit(Field.Reputation field) {
        return field.value;
    }

    @Override
    public Object visit(Field.Posts field) {
        return field.value;
    }
}
