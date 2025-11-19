package kurt.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Kurt {
    static final String USERS = "kurtCollab-main/src/kurt/test/users.vff";
    static final String WRITE = "kurtCollab-main/src/kurt/test/writeTest.vff";

    static boolean hadError = false;

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        Parser parser = new Parser(readFile(WRITE), new HashMap<>());

        Map<String, User> users = parser.map();
        if (hadError) return;

        for (User user : users.values())
            System.out.println(user);

        Serializer writer = new Serializer(WRITE);
        writer.initialize(users.size());
        for (User user : users.values())
            writer.write(user);
    }

    public static byte[] readFile(String path) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }

    static void report(String message, String context, int pos) {
        System.out.println("ERROR: " + message + " -> " + context + "@offset[ " + pos + " ]");
        hadError = true;
    }

    static void report(String message, int pos) {
        System.out.println("ERROR: " + message + "@offset[ " + pos + " ]");
        hadError = true;
    }
}
