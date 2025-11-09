package kurt.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class User {
    List<Field> fields = new ArrayList<>(2);
    private final Map<FieldType, Field> fieldMap = new HashMap<>(2);

    public void add(Field field) {
        fields.add(field);
    }

    public void put(FieldType id, Field field) {
        fieldMap.put(id, field);
    }

    public String getUsername() {
        Field.Name name = (Field.Name)fieldMap.get(FieldType.USERNAME);
        return name.value;
    }

    public String getPassword() {
        Field.Password pass = (Field.Password)fieldMap.get(FieldType.PWD);
        return pass.value;
    }

    public String getDob() {
        Field.DOB date = (Field.DOB)fieldMap.get(FieldType.DOB);
        return date.value;
    }

    public String getEmail() {
        Field.Email email = (Field.Email)fieldMap.get(FieldType.EMAIL);
        return email.value;
    }

    public double getReputation() {
        Field.Reputation rep = (Field.Reputation)fieldMap.get(FieldType.REP);
        return rep.value;
    }

    public int getPosts() {
        Field.Posts posts = (Field.Posts)fieldMap.get(FieldType.POSTS);
        return posts.value;
    }
}
