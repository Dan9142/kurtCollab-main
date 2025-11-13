package kurt.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Kurt {
    static final String PATH = "kurtCollab/src/kurt/test/sample.vff";
    static final String WRITE = "kurtCollab/src/kurt/test/writeTest.vff";

    static boolean hadError = false;

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);

        /*
        * In order to create new users, there are two
        * approaches we can take. The first one is as
        * follows:
        */

        Creator creator = new Creator(new User()); // User instantiated within Creator

        System.out.print("Enter new username: ");
        String name = input.nextLine();
        // Add new field into user with this method.
        creator.add(new Field.Name(name)); // It's worth noting that this method returns an error code

        System.out.print("Enter new password: ");
        String pass = input.nextLine();
        creator.add(new Field.Password(pass));

        System.out.println(creator.getUser());

        /*
        * The downside of this is that we have to get
        * the user back using the Creator's getUser().
        * method. The second approach is as follows:
        */

        User user = new User(); // Keep reference to user instead
        creator = new Creator(user);

        System.out.print("Enter new username: ");
        name = input.nextLine();
        creator.add(new Field.Name(name)); // Same functionality

        System.out.print("Enter new password: ");
        pass = input.nextLine();
        creator.add(new Field.Password(pass));

        System.out.println(user);
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
