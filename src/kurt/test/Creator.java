package kurt.test;

import static kurt.test.ExitCode.*;

class Creator implements Field.Visitor<ExitCode> {
    private final User user;
    private final Processor proc;

    Creator(User user) {
        this.user = user;
        proc = new Processor();
    }

    public ExitCode add(Field field) {
        if (field == null) return EXIT_NULL_ID;
        return field.accept(this);
    }

    @Override
    public ExitCode visit(Field.Name field) {
        if (!proc.validate(field)) return EXIT_REQUIRED_FIELD;
        user.setUsername(field.value);
        return EXIT_SUCCESS;
    }

    @Override
    public ExitCode visit(Field.Password field) {
        if (!proc.validate(field)) return EXIT_REQUIRED_FIELD;
        user.setPassword(field.value);
        return EXIT_SUCCESS;
    }

    @Override
    public ExitCode visit(Field.DOB field) {
        if (!proc.validate(field)) return EXIT_PROCESSOR_FAILURE;
        user.setDob(field.value);
        return EXIT_SUCCESS;
    }

    @Override
    public ExitCode visit(Field.Email field) {
        if (!proc.validate(field)) return EXIT_PROCESSOR_FAILURE;
        user.setEmail(field.value);
        return EXIT_SUCCESS;
    }

    public ExitCode visit(Field.Reputation field) {
        if (!proc.validate(field)) return EXIT_PROCESSOR_FAILURE;
        user.setReputation(field.value);
        return EXIT_SUCCESS;
    }

    public ExitCode visit(Field.Posts field) {
        if (!proc.validate(field)) return EXIT_PROCESSOR_FAILURE;
        user.setPosts(field.value);
        return EXIT_SUCCESS;
    }

    public User getUser() {
        return user;
    }
}
