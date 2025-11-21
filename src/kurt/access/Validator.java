package kurt.access;

class Validator implements Field.Visitor<Boolean> {
    public boolean validate(Field field) {
        return field.accept(this);
    }

    @Override
    public Boolean visit(Field.Name field) {
        return field.value != null &&
                !field.value.isEmpty();
    }

    @Override
    public Boolean visit(Field.Password field) {
        return field.value != null &&
                !field.value.isEmpty();
    }

    @Override
    public Boolean visit(Field.DOB field) {
        String dob = field.value;
        String regex = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
        return dob != null && dob.length() == 10
                && dob.matches(regex);
    }

    @Override
    public Boolean visit(Field.Email field) {
        String email = field.value;
        String emailRegex = "^[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return email != null && email.matches(emailRegex);
    }

    public Boolean visit(Field.Reputation field) {
        double rep = field.value;
        return rep >= 0 && rep <= 5;
    }

    public Boolean visit(Field.Posts field) {
        return field.value >= 0;
    }
}
