package kurt.access;

import java.util.HashMap;
import java.util.Map;

public class User {
    private final Map<FieldType, Field> fieldMap = new HashMap<>(3);

    public void put(FieldType id, Field field) {
        fieldMap.put(id, field);
    }

    public Map<FieldType, Field> getFieldMap() {
        return fieldMap;
    }

    public String getUsername() {
        Field.Name name = (Field.Name)fieldMap.get(FieldType.USERNAME);
        return name == null ? "N/A" : name.value;
    }

    public String getPassword() {
        Field.Password pass = (Field.Password)fieldMap.get(FieldType.PWD);
        return pass == null ? "N/A" : pass.value;
    }

    public String getDob() {
        Field.DOB date = (Field.DOB)fieldMap.get(FieldType.DOB);
        return date == null ? "N/A" : date.value;
    }

    public String getEmail() {
        Field.Email email = (Field.Email)fieldMap.get(FieldType.EMAIL);
        return email == null ? "N/A" : email.value;
    }

    public float getReputation() {
        Field.Reputation rep = (Field.Reputation)fieldMap.get(FieldType.REP);
        return rep == null ? 0 : rep.value;
    }

    public int getPosts() {
        Field.Posts posts = (Field.Posts)fieldMap.get(FieldType.POSTS);
        return posts == null ? 0 : posts.value;
    }

    @Override
    public String toString() {
        String format = """
                {
                    Username: [ %s ], Password: [ %s ],
                    Date of Birth: [ %s ], Email: [ %s ],
                    Reputation: [ %.2f ], Posts: [ %d ]
                }""";
        return String.format(format, getUsername(), getPassword(), getDob(),
                    getEmail(), getReputation(), getPosts());
    }
}
