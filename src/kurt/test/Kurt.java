package kurt.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static kurt.test.FieldType.*;

public class Kurt {
    static final String PATH = "src/kurt/test/sample.vff";
    static final String WRITE = "src/kurt/test/writeTest.vff";

    static boolean hadError = false;

    public static void main(String[] args) throws IOException {
        Parser parser = new Parser(readFile(PATH), new HashMap<>());
        Scanner input = new Scanner(System.in);

        Map<String, User> users = parser.map();
        if (hadError) return;

        for (User user : users.values())
        {
            System.out.print(user.getUsername() + " ");
            System.out.println(user.getPassword());
        }


        System.out.print("Enter username: ");
        String name = input.nextLine();

        User user = users.get(name);
        if (user == null) {
            System.out.println("User does not exist");
            return;
        }

        System.out.print("Enter password: ");
        String pass = input.nextLine();

        if (!pass.equals(user.getPassword())) {
            System.out.println("Incorrect password.");
            return;
        }

        System.out.println("You're in.");
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
