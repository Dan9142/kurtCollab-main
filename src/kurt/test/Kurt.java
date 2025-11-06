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
