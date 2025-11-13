package kurt.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static kurt.test.FieldType.*;
import static kurt.test.NumberType.*;
import static kurt.test.ExitCode.*;

public class Parser extends ByteScanner {
    public Map<String, User> maps;
    private static final Map<Byte, FieldType> IDS;
    private static final byte CONT_MARKER = (byte)0xFF;

    static {
        IDS = new HashMap<>();
        IDS.put((byte)0x80, USERNAME);
        IDS.put((byte)0x90, PWD);
        IDS.put((byte)0xA0, DOB);
        IDS.put((byte)0xB0, EMAIL);
        IDS.put((byte)0xC0, REP);
        IDS.put((byte)0xD0, POSTS);
        IDS.put((byte)0xE0, TERMINATE);
    }

    public Parser(byte[] bytes, Map<String, User> maps) {
        super(bytes);
        this.maps = maps;
    }

    public Map<String, User> map() {
        try {
            return mapUsers();
        } catch (ParseFailure error) {
            return null;
        }
    }

    private Map<String, User> mapUsers() {
        verifyHeader();
        int numOfUsers = asInt(INT); // Retrieve int and advance.
        if (numOfUsers == 0) return Collections.emptyMap();

        while (!isEnd()) {
            if (!match(CONT_MARKER))
                throw error(EXIT_UNKNOWN_STATE, "0xFF expected before user fields");

            next();
            mapUser();
        }

        if (maps.size() != numOfUsers)
            throw error(EXIT_UNEXPECTED_USERS,
                    String.format("Expected %d users but found %d", numOfUsers, maps.size()));
        return maps;
    }

    private void mapUser() {
        FieldType type = getID();
        if (stop(type)) throw error(EXIT_NULL_ID);

        ExitCode code; // Assume that we failed to retrieve an ID.
        Creator creator = new Creator(new User());
        while (!isEnd() && !stop(type)) {
            // Creating appropriate user fields for every value.
            Field field = createField(type);
            code = creator.add(field);
            if (code != EXIT_SUCCESS) throw error(code);

            type = getID();
        }

        User user = creator.getUser();
        if (user.getUsername() == null || user.getPassword() == null)
            throw error(EXIT_REQUIRED_FIELD);
        maps.put(user.getUsername(), user);
    }

    private Field createField(FieldType type) {
        return switch (type) {
            case USERNAME -> new Field.Name(asString(asInt(BYTE)));
            case PWD -> new Field.Password(asString(asInt(BYTE)));
            case DOB -> new Field.DOB(asString(asInt(BYTE)));
            case EMAIL -> new Field.Email(asString(asInt(BYTE)));
            case REP -> new Field.Reputation(asFloat());
            case POSTS -> new Field.Posts(asInt(INT));
            default -> null; // We should never encounter this situation.
        };
    }

    private FieldType getID() {
        return isEnd() ? null : IDS.get(next());
    }

    private boolean stop(FieldType type) {
        return type == null || type == TERMINATE;
    }

    private void verifyHeader() {
        String header = asString(5);
        if (!header.equals("VFF02"))
            throw error(EXIT_INVALID_FILE);
    }
}
