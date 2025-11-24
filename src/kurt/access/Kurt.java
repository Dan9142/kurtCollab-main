package kurt.access;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Kurt {
    public static final Path DUMP =
            Paths.get("kurtCollab-main", "src", "kurt", "access", "files", "posts.dump").toAbsolutePath();
    public static final Path SAMPLE =
            Paths.get("kurtCollab-main", "src", "kurt", "access", "files", "sample.vff").toAbsolutePath();
    public static final Path KRAT =
            Paths.get("kurtCollab-main", "src", "kurt", "access", "files", "index.krat").toAbsolutePath();

    public static Map<String, User> users = new HashMap<>();
    public static Map<String, List<Post>> tagMap = new HashMap<>();
    public static Indexer indexer;
    public static User currentUser = null;

    public static boolean hadError = false;

    public static boolean createUser(String username, String password, String dob, String email) {
        User newUser = new User();
        Creator creator = new Creator(newUser);

        creator.add(new Field.Name(username));
        creator.add(new Field.Password(password));
        creator.add(new Field.DOB(dob));
        creator.add(new Field.Email(email));
        creator.add(new Field.Reputation(0.0f));
        creator.add(new Field.Posts(0));

        if (newUser.getUsername() == null || newUser.getPassword() == null) {
            System.out.println("Registration failed: Username and password are required!");
            return true;
        }

        users.put(username, newUser);
        return false;
    }

    public static void saveAllUsers() throws IOException {
        Serializer serializer = new Serializer(SAMPLE);

        serializer.initialize(users.size(), "VFF02");
        for (User user : users.values())
            serializer.write(user);
    }

    public static void saveAllPosts() throws IOException {
        Serializer serializer = new Serializer(KRAT);

        serializer.initialize(tagMap.size(), "KRAT");
        for (Map.Entry<String, List<Post>> entry : tagMap.entrySet())
            serializer.write(entry.getValue(), entry.getKey());
    }

    public static String getSnippet(Post post) {
        int strlen = post.getLength();
        byte[] bytes = new byte[strlen];

        File dumpFile = DUMP.toFile();
        try (RandomAccessFile ra = new RandomAccessFile(dumpFile, "r")) {
            ra.seek(post.getOffset());
            ra.readFully(bytes, 0, strlen);
        } catch (IOException e) {
            System.err.println("Bad file, muchacho.");
        }

        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static byte[] readFile(Path path) throws IOException {
        return Files.readAllBytes(path);
    }

    /**
     * Reports errors encountered during file parsing to the console
     * and sets error flag to prevent further issues. Allows additional
     * context that may be useful in debugging.
     *
     * @param message Default error message returned by ExitCode.
     * @param context Additional context about the error.
     * @param pos Position in file where the parser encountered the error.
     */
    static void report(String message, String context, int pos) {
        System.out.println("ERROR: " + message + " -> " + context + " @offset[ " + pos + " ]");
        hadError = true;
    }

    /**
     * Reports errors encountered during file parsing to the console
     * and sets error flag to prevent further issues.
     *
     * @param message Default error message returned by ExitCode.
     * @param pos Position in file where the parser encountered the error.
     */
    static void report(String message, int pos) {
        System.out.println("ERROR: " + message + " @offset[ " + pos + " ]");
        hadError = true;
    }


}
