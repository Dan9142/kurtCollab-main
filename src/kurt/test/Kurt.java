package kurt.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Kurt {
    static final String PATH = "kurtCollab/src/kurt/test/scaleTest.vff";
    static final String WRITE = "kurtCollab/src/kurt/test/writeTest.vff";

    static boolean hadError = false;

    public static void main(String[] args) throws IOException {
        Parser parser = new Parser(readFile(PATH), new HashMap<>());
        Scanner input = new Scanner(System.in);

        Map<String, User> users = parser.map();
        if (hadError) return;

        for (User user : users.values())
            System.out.println(user);

        System.out.print("Enter a username: ");
        String username = input.nextLine();

        if (!users.containsKey(username)) {
            System.out.println("Username not found :(");
            return;
        }

        User user = users.get(username);
        System.out.print("Enter password: ");
        String password = input.nextLine();

        if (!user.getPassword().equals(password)) {
            System.out.println("Wrong password :(");
            return;
        }

        System.out.println("Success :)");
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
