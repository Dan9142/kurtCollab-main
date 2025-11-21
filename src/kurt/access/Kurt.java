package kurt.access;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Kurt {
    static final String KRAT = "kurtCollab-main/src/kurt/access/files/index.krat";
    static final String USERS = "kurtCollab-main/src/kurt/access/files/users.vff";
    static final String WRITE = "kurtCollab-main/src/kurt/access/files/writeTest.vff";

    static boolean hadError = false;

    public static void main(String[] args) throws IOException {
        Indexer indexer = new Indexer(readFile(KRAT), new HashMap<>());
        Map<String, List<Post>> tags = indexer.index();
        if (hadError) return;
        System.out.println("Success");

        for (String tag : tags.keySet())
            System.out.println(tag);

        for (List<Post> posts : tags.values())
            System.out.println(posts);
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
