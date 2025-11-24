package kurt.access;

import static kurt.access.ExitCode.*;
import static kurt.access.FieldType.*;

public class Creator implements Field.Visitor<ExitCode> {
    private final User user;
    private final Validator validator;

    public Creator(User user) {
        this.user = user;
        validator = new Validator();
    }

    public ExitCode add(Field field) {
        if (field == null) return EXIT_NULL_ID;
        return field.accept(this);
    }

    @Override
    public ExitCode visit(Field.Name field) {
        if (!validator.validate(field)) return EXIT_REQUIRED_FIELD;
        user.put(USERNAME, field);
        return EXIT_SUCCESS;
    }

    @Override
    public ExitCode visit(Field.Password field) {
        if (!validator.validate(field)) return EXIT_REQUIRED_FIELD;
        user.put(PWD, field);
        return EXIT_SUCCESS;
    }

    @Override
    public ExitCode visit(Field.DOB field) {
        if (!validator.validate(field)) return EXIT_PROCESSOR_FAILURE;
        user.put(DOB, field);
        return EXIT_SUCCESS;
    }

    @Override
    public ExitCode visit(Field.Email field) {
        if (!validator.validate(field)) return EXIT_PROCESSOR_FAILURE;
        user.put(EMAIL, field);
        return EXIT_SUCCESS;
    }

    public ExitCode visit(Field.Reputation field) {
        if (!validator.validate(field)) return EXIT_PROCESSOR_FAILURE;
        user.put(REP, field);
        return EXIT_SUCCESS;
    }

    public ExitCode visit(Field.Posts field) {
        if (!validator.validate(field)) return EXIT_PROCESSOR_FAILURE;
        user.put(POSTS, field);
        return EXIT_SUCCESS;
    }

    public User getUser() {
        return user;
    }
}
