package kurt.access;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Kurt {
    static final String DUMP = "C:/Users/jacob/GitHub/kurtCollab-main/src/kurt/access/files/posts.dump";
    public static final String SAMPLE = "C:/Users/jacob/GitHub/kurtCollab-main/src/kurt/access/files/sample.vff";
    static final String KRAT = "C:/Users/jacob/GitHub/kurtCollab-main/src/kurt/access/files/index.krat";

    public static Map<String, User> users = new HashMap<>();
    public static Map<String, List<Post>> tagMap = new HashMap<>();
    public static Indexer indexer;
    private static User currentUser = null;

    static boolean hadError = false;

    public static void main(String[] args) throws IOException {
        Parser parser = new Parser(readFile(SAMPLE), new HashMap<>());
        indexer = new Indexer(readFile(KRAT), new HashMap<>());
        users = parser.map();
        tagMap = indexer.index();
        if (hadError) return;

        run();
    }

    private static void run() throws IOException {
        Scanner input = new Scanner(System.in);

        do {
            if (currentUser == null) {
                MainMenu(input);
            } else {
                UserMenu(input);
            }
        } while (true);
    }

    private static void MainMenu(Scanner input) throws IOException {
        System.out.println("\n=== Kurt Application ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Search by Tag");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");

        int choice = input.nextInt();
        input.nextLine();

        switch (choice) {
            case 1 -> login(input);
            case 2 -> register(input);
            case 3 -> search(input);
            case 4 -> {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid option!");
        }
    }

    private static void UserMenu(Scanner input) throws IOException {
        System.out.println("\n**** Welcome " + currentUser.getUsername() + " ****");
        System.out.println("1. View Profile");
        System.out.println("2. Search by Tag");
        System.out.println("3. Rate User");
        System.out.println("4. Make Post");
        System.out.println("5. Logout");
        System.out.print("Choose an option: ");

        int choice = input.nextInt();
        input.nextLine();

        switch (choice) {
            case 1 -> profile();
            case 2 -> search(input);
            case 3 -> rate(input);
            case 4 -> makePost(input);
            case 5 -> {
                System.out.println("Logging out...");
                currentUser = null;
            }
            default -> System.out.println("Invalid option, brochacho");
        }
    }

    private static void login(Scanner input) {
        System.out.println("\n**** Login ****");
        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();

        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("Login successful! Welcome back, " + username + "!");
        } else {
            System.out.println("Invalid username or password!");
        }
    }

    private static void register(Scanner input) throws IOException {
        System.out.println("\n=== Register ===");

        System.out.print("Username: ");
        String username = input.nextLine();

        if (users.containsKey(username)) {
            System.out.println("Username already exists!");
            return;
        }

        System.out.print("Password: ");
        String password = input.nextLine();
        System.out.print("Date of Birth (MM/DD/YYYY): ");
        String dob = input.nextLine();
        System.out.print("Email: ");
        String email = input.nextLine();

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
            return;
        }

        users.put(username, newUser);

        saveAllUsers();

        System.out.println("Registration successful! You can now login.");
    }

    public static void saveUser(User user) throws IOException {
        Serializer serializer = new Serializer(SAMPLE);

        if (users.size() == 1) {
            serializer.initialize(users.size(), "VFF02");
        }

        serializer.write(user);
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

    private static void makePost(Scanner input) throws IOException{
        Creator creator = new Creator(currentUser);

        System.out.println("**** Make Post ****");
        System.out.print("Enter new post: ");
        String content = input.nextLine();
        System.out.print("Enter tag for this post: ");
        String tag = input.nextLine();

        long offset = Indexer.dumpPost(content);
        Post post = new Post(currentUser.getUsername(), offset, content.length());
        creator.add(new Field.Posts(currentUser.getPosts() + 1));
        indexer.post(post, tag, tagMap);

        saveAllPosts();
        saveAllUsers();
    }

    private static void rate(Scanner input) throws IOException {
        System.out.println("*** Rate User ****");
        System.out.print("Enter user to rate: ");
        String target = input.nextLine();

        User user = users.get(target);
        if (user == null) {
            System.out.println("User does not exist!");
            return;
        }

        System.out.println("Enter your rating: ");
        float newRating = input.nextFloat();
        float rating = user.getReputation();
        float avg = (newRating + rating) / 2;

        Creator creator = new Creator(user);
        creator.add(new Field.Reputation(avg));
        System.out.printf("Success! Previous rating: %.2f, New Rating: %.2f", rating, avg);

        saveAllUsers(); // Save all after updates
    }

    public static void search(Scanner input) {
        System.out.println("\n=== Search by Tag ===");
        System.out.print("Enter tag to search: ");
        String tag = input.nextLine();

        List<Post> posts = tagMap.get(tag);
        if (posts == null || posts.isEmpty()) {
            System.out.println("No posts found for tag: " + tag);
            return;
        }

        System.out.println("\nFound " + posts.size() + " post(s) for tag '" + tag + "':");
        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            String snippet = getSnippet(post);
            System.out.println((i + 1) + ". " + post);
            System.out.println("   Preview: " + (snippet.length() > 50 ?
                    snippet.substring(0, 50) + "..." : snippet));
            System.out.println();
        }

        // Check if logged in to view full code snippet.
        if (currentUser == null) return;

        System.out.print("Enter post number to view full content (0 to go back): ");
        int postChoice = input.nextInt();
        input.nextLine();

        if (postChoice > 0 && postChoice <= posts.size()) {
            Post selectedPost = posts.get(postChoice - 1);
            String fullContent = getSnippet(selectedPost);
            System.out.println("\n**** Full Post ****");
            System.out.println("Poster: " + selectedPost.getPoster());
            System.out.println("Content: " + fullContent);
            System.out.println("Length: " + selectedPost.getLength() + " characters");
        }
    }

    private static void profile() {
        System.out.println("\n**** Your Profile ****");
        System.out.println(currentUser.toString());
    }

    public static String getSnippet(Post post) {
        int strlen = post.getLength();
        byte[] bytes = new byte[strlen];

        try (RandomAccessFile ra = new RandomAccessFile(DUMP, "r")) {
            ra.seek(post.getOffset());
            ra.readFully(bytes, 0, strlen);
        } catch (IOException e) {
            System.err.println("Bad file, muchacho.");
        }

        return new String(bytes, StandardCharsets.UTF_8);
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
