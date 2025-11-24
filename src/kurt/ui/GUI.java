package kurt.ui;

import kurt.access.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JFrame;

import static kurt.access.Kurt.*;


public class GUI
{

    static boolean tagNeeded = false;
    static int profilePresses = 0;
    static int postPresses = 0;

    public static Map<String, User> users = new HashMap<>();
    private static Map<String, List<Post>> tagMap = new HashMap<>();
    private static Indexer indexer;
    private static User currentUser = null;
    static final String DUMP = "C:/Users/jacob/GitHub/kurtCollab-main/src/kurt/access/files/posts.dump";
    static final String SAMPLE = "C:/Users/jacob/GitHub/kurtCollab-main/src/kurt/access/files/sample.vff";
    static final String KRAT = "C:/Users/jacob/GitHub/kurtCollab-main/src/kurt/access/files/test.krat";


    //Initial Setup
    static JFrame firstFrame = new JFrame("Kurt Collab");


    //Log-in Setup
    static JTextField loginText = new JTextField(50);
    static JButton loginEnterButton = new JButton("Enter");
    static JPanel loginPanel = new JPanel();
    static TextPrompt loginTextPrompt = new TextPrompt("Enter Username", loginText);
    static JLabel loginLabel = new JLabel();
    static JLabel loginPageRegisterLabel = new JLabel();
    static JButton registerSwitchButton = new JButton("Register");
    static JLabel loginTagSearch = new JLabel();
    static JButton loginTagSearchButton = new JButton("Search");
    static JLabel loginPostDisplayLabel = new JLabel();
    static JTextField loginTagSearchText = new JTextField(50);
    static TextPrompt loginTagSearchTextPrompt = new TextPrompt("Enter Tag", loginTagSearchText);
    static JTextArea loginTagDisplayArea = new JTextArea();
    static JLabel loginTagDisplayAuthor = new JLabel();
    static JLabel loginTagDisplayNumberOfPosts = new JLabel();
    static JButton loginTagNextButton = new JButton("Next");
    static JButton loginTagPreviousButton = new JButton("Last");

    //Login Variables
    static int loginPresses = 0;
    //static User user = new User();
    static boolean usernameRight = false;
    static String usernameStorage = "";
    static int tagCounter = 1;
    static String universalUsername = "";
    static String universalDOB = "";
    static String universalEmail = "";

    //Register Set-Up
    static JPanel registerPanel = new JPanel();
    static JTextField registerText = new JTextField();
    static JLabel registerLabel = new JLabel();
    static JButton registerEnterButton = new JButton("Enter");
    static TextPrompt registerTextPrompt  = new TextPrompt("Username", registerText);

    //Registration Variables
    static boolean usernameEntered = false;
    static boolean passwordEntered = false;
    static boolean DOBEntered = false;
    static boolean emailEntered = false;
    static int registerButtonPresses = 0;
    static String username = "";
    static String password = "";
    static String DOB = "";
    static String email = "";
    static Float reputation = 0.0f;
    static int posts = 0;

    //Main Menu Set-Up
    static JPanel mainMenuPanel = new JPanel();

    static JPanel profilePanel = new JPanel();
    static JButton viewProfile = new JButton("Profile");
    static JLabel profileUsername =  new JLabel();
    static JLabel profileDOB = new JLabel();
    static JLabel profileEmail = new JLabel();

    static JLabel mainTagSearch = new JLabel();
    static JButton mainTSearchButton = new JButton("Search");
    static JTextField loSearchText = new JTextField(50);
    static TextPrompt mainTagSearchTextPrompt = new TextPrompt("Enter Tag", loginTagSearchText);
    static JTextArea mainTagDisplayArea = new JTextArea();
    static JLabel mainTagDisplayAuthor = new JLabel();
    static JLabel mainTagDisplayNumberOfPosts = new JLabel();
    static JButton mainTagNextButton = new JButton("Next");
    static JButton mainTagPreviousButton = new JButton("Last");

    static JButton postButton = new JButton("Enter");
    static JTextArea mainPostDisplayArea = new JTextArea();
    static JLabel mainPostLabel  = new JLabel();

    static JButton rateUserEnterButton = new JButton("Rate");
    static JTextField rateUserText = new JTextField(50);
    static JLabel rateUserLabel = new JLabel();

    //Main Menu Variables
    static boolean viewingProfile = false;




    public static void loginSetup()
    {
        firstFrame.setResizable(false);
        firstFrame.setSize(700, 350);
        loginPanel.setLayout(null);
        loginText.setBounds(0,0,200,50);
        loginEnterButton.setBounds(200, 0, 100, 100);
        loginLabel.setBounds(5, 50, 300, 50);
        loginTextPrompt.setForeground( Color.BLACK );
        loginTextPrompt.changeAlpha(0.4f);
        loginTextPrompt.changeStyle(Font.BOLD + Font.ITALIC);
        loginPanel.add(loginText);
        loginEnterButton.setPreferredSize(new Dimension(200, 100));
        loginPanel.add(loginEnterButton);
        loginPanel.setBackground(Color.GRAY);
        loginPanel.add(loginLabel);
        loginPageRegisterLabel.setBounds(5, 150, 300, 50);
        loginPageRegisterLabel.setText("Don't have an account? Click to register!");
        loginPanel.add(loginPageRegisterLabel);
        registerSwitchButton.setBounds(80,200,100,100);
        loginPanel.add(registerSwitchButton);
        loginTagSearch.setBounds(505,50,300,50);
        loginTagSearch.setText("Search for posts by tag here!");
        loginPanel.add(loginTagSearch);
        loginTagSearchText.setBounds(500,0,200,50);
        loginPanel.add(loginTagSearchText);
        loginTagSearchTextPrompt.setForeground( Color.BLACK );
        loginTagSearchTextPrompt.changeAlpha(0.4f);
        loginTagSearchTextPrompt.changeStyle(Font.BOLD + Font.ITALIC);
        loginTagSearchButton.setBounds(400,0,100,100);
        loginPanel.add(loginTagSearchButton);
        loginTagDisplayArea.setBounds(400,150,250,100);
        loginTagDisplayArea.setText("Hi");
        loginTagDisplayArea.setLineWrap(true);
        loginTagDisplayArea.setWrapStyleWord(true);
        loginTagDisplayArea.setEditable(false);
        loginPanel.add(loginTagDisplayArea);
        loginTagDisplayAuthor.setBounds(400,110,200,50);
        loginTagNextButton.setBounds(340,145,60,60);
        loginPanel.add(loginTagNextButton);
        loginTagPreviousButton.setBounds(340,205,60,60);
        loginPanel.add(loginTagPreviousButton);
        loginPanel.add(loginTagDisplayAuthor);
        loginTagPreviousButton.setVisible(false);
        loginTagNextButton.setVisible(false);
        firstFrame.add(loginPanel);
        firstFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        firstFrame.setVisible(true);
    }


    public static void close()
    {
        firstFrame.dispose();
    }
    public static String getUsername() throws IOException
    {
        String username = loginText.getText();
        User user = users.get(username);
        if (user == null)
        {
            return "";
        }
        return username;

    }
    public static boolean checkingUsername() throws IOException
    {
        String username = loginText.getText();
        User user = users.get(username);
        if (user == null)
        {
            return false;
        }
        usernameStorage = username;
        return true;
    }

    public static boolean checkingPassword() throws IOException
    {
        String password = loginText.getText();
        User user = users.get(usernameStorage);
        if (!getUsername().isEmpty());
        {
            if (user.getPassword().equals(password))
            {
                loginText.setText("");
                return true;
            }
        }
        return false;
    }

    public static void loginActionListener()
    {
        loginEnterButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    if (checkingUsername() == true && usernameRight == false)
                    {
                        loginLabel.setText("Hello, " + getUsername() + ". Enter Your Password.");
                        loginTextPrompt.setText("Enter Password");
                        usernameRight = true;
                        loginText.setText("");
                        loginPresses++;
                        //loginEnterButton.removeActionListener(this);
                    }
                    else if (checkingUsername() == false && usernameRight == false)
                    {
                        loginLabel.setText("Username Not Found. Try Again!");
                        System.out.println("akdmkasdk");
                        //initialActionListener();
                    }

                    if (usernameRight == true)
                    {
                        if (checkingPassword() == true)
                        {
                            User user = users.get(usernameStorage);
                            loginLabel.setText("Welcome to Kurt Collab!");
                            loginEnterButton.setVisible(false);
                            loginText.setVisible(false);
                            loginLabel.setBounds(100, 100, 200, 200);
                            //loginPanel.setVisible(false);
                            //firstFrame.setVisible(false);
                            loginPanel.setVisible(false);
                            tagCounter = 1;
                            currentUser = user;
                            mainMenuSetup();
                        }
                        else if (checkingPassword() == false)
                        {
                            System.out.println("ferferfer");
                            if (loginPresses > 1)
                            {
                                loginLabel.setText("Incorrect Password. Try Again!");
                            }
                            loginPresses++;
                        }
                    }
                }
                catch (IOException ex)
                {
                    throw new RuntimeException(ex);
                }

            }

        });
    }

    public static void registerSwitchActionListener()
    {
        registerSwitchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                try
                {
                    registeringSetup();
                }
                catch (IOException ex)
                {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static void registeringSetup() throws IOException {
        loginPanel.setVisible(false);
        registerPanel.setVisible(true);
        registerPanel.setLayout(null);
        registerText.setBounds(60,0,200,50);
        registerEnterButton.setBounds(260, 0, 100, 100);
        registerLabel.setBounds(5, 50, 300, 50);
        registerTextPrompt.setForeground( Color.BLACK );
        registerTextPrompt.changeAlpha(0.4f);
        registerTextPrompt.changeStyle(Font.BOLD + Font.ITALIC);
        registerTextPrompt.setText("Enter a Username");
        registerPanel.add(registerText);
        //registerEnterButton.setPreferredSize(new Dimension(200, 100));
        registerPanel.add(registerEnterButton);
        registerPanel.setBackground(Color.GRAY);
        registerPanel.add(registerLabel);
        firstFrame.add(registerPanel);
        firstFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerActionListener();
    }

    public static void registerActionListener() throws IOException
    {
        registerEnterButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                registerButtonPresses++;
                if (!usernameEntered && registerButtonPresses == 1)
                {
                    username = registerText.getText();
                    registerLabel.setText("Now Enter a Password");
                    registerTextPrompt.setText("Enter a Password");
                    registerText.setText("");
                    usernameEntered = true;
                }
                if (registerButtonPresses == 2 && !passwordEntered)
                {
                    password = registerText.getText();
                    registerLabel.setText("Now Enter a DOB");
                    registerTextPrompt.setText("Enter a DOB (MM-DD-YYYY)");
                    registerText.setText("");
                    passwordEntered = true;
                }
                if (registerButtonPresses == 3 && !DOBEntered)
                {
                    DOB = registerText.getText();
                    registerLabel.setText("Now Enter an Email");
                    registerTextPrompt.setText("Enter an Email");
                    registerText.setText("");
                    DOBEntered = true;
                }
                if (registerButtonPresses == 4 && !emailEntered)
                {
                    email = registerText.getText();
                    emailEntered = true;
                }
                if (emailEntered && DOBEntered && passwordEntered && usernameEntered)
                {
                    User newUser = new User();
                    Creator creator = new Creator(newUser);

                    creator.add(new Field.Name(username));
                    creator.add(new Field.Password(password));
                    creator.add(new Field.DOB(DOB));
                    creator.add(new Field.Email(email));
                    creator.add(new Field.Reputation(0.0f));
                    creator.add(new Field.Posts(0));

                    if (newUser.getUsername() == null || newUser.getPassword() == null)
                    {
                        System.out.println("Registration failed: Username and password are required!");
                        return;
                    }
                    Kurt.users.put(username, newUser);

                    try {
                        saveAllUsers();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }


                    registerPanel.setVisible(false);
                    loginPanel.setVisible(true);
                    loginLabel.setText("Registration Successful!");
                }
            }
        });
    }

    public static void tagSearching()
    {
        String tag = loginTagSearchText.getText();

        List<Post> posts = tagMap.get(tag);
        if (posts == null || posts.isEmpty()) {
            System.out.println("No posts found for tag: " + tag);
            return;
        }

        System.out.println("\nFound " + posts.size() + " post(s) for tag '" + tag + "':");
        Post post = posts.get(tagCounter-1);
        String snippet = getSnippet(post);
            //System.out.println((i + 1) + ". " + post);
            //System.out.println("   Preview: " + (snippet.length() > 50 ?
            //        snippet.substring(0, 50) + "..." : snippet));
            //System.out.println();
        loginTagDisplayArea.setText(snippet);
        loginTagDisplayAuthor.setText("" + post);
        loginTagPreviousButton.setVisible(true);
        loginTagNextButton.setVisible(true);
    }
    public static void mainMenuSetup()
    {
        mainMenuPanel.setLayout(null);
        mainMenuPanel.setBackground(Color.GRAY);
        firstFrame.add(mainMenuPanel);
        viewProfile.setBounds(0,0,100,50);
        mainMenuPanel.add(viewProfile);
        postButton.setBounds(100, 100, 100, 50);
        mainMenuPanel.add(postButton);
        mainPostDisplayArea.setBounds(200, 100, 150, 150);
        mainMenuPanel.add(mainPostDisplayArea);
        mainPostLabel.setBounds(200, 50, 150, 50);
        mainPostLabel.setText("Make a post here!");
        mainMenuPanel.add(mainPostLabel);
    }

    public static void profileSetup()
    {
        profilePanel.setLayout(null);
        profilePanel.setBackground(Color.GRAY);

    }

    public static void tagSearchButtonActionListener()
    {
        loginTagSearchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tagSearching();
            }
        });
    }
    public static void tagSearchNextButtonActionListener()
    {
        loginTagNextButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tagCounter++;
                tagSearching();
            }
        });
    }
    public static void tagSearchLastButtonActionListener()
    {
        loginTagPreviousButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tagCounter--;
                tagSearching();
            }
        });
    }
    public static void postingButtonActionListener()
    {
        User user = users.get(usernameStorage);
        postButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                postPresses++;
                if (tagNeeded == false && postPresses %2 == 1)
                {
                    Creator creator = new Creator(currentUser);
                    String content = mainPostDisplayArea.getText();
                    long offset = 0;
                    try
                    {
                        offset = Indexer.dumpPost(content);
                    }
                    catch (IOException ex)
                    {
                        throw new RuntimeException(ex);
                    }
                    Post post = new Post(currentUser.getUsername(), offset, content.length());
                    creator.add(new Field.Posts(currentUser.getPosts() + 1));
                    try {
                        saveAllPosts();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    mainPostDisplayArea.setText("");
                    postButton.setText("Tag");
                }
            }
        });
    }

    public static void profileButtonActionListener()
    {
        profileSetup();
        viewProfile.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("sasada");
                profilePresses++;
                if (!viewingProfile && profilePresses % 2 != 0)
                {
                    profilePanel.setVisible(true);
                    User user = users.get(usernameStorage);
                    mainMenuPanel.setVisible(false);
                    profileUsername.setBounds(0,0,100,50);
                    profileDOB.setBounds(0,50,100,50);
                    profileUsername.setText("Username: " + user.getUsername());
                    profileDOB.setText("DOB: " + user.getDob());
                    profileEmail.setBounds(0,100,100,50);
                    profileEmail.setText("Email: " + user.getEmail());
                    profilePanel.add(profileUsername);
                    profilePanel.add(profileDOB);
                    profilePanel.add(profileEmail);
                    firstFrame.add(profilePanel);
                    viewProfile.setText("Back");
                    viewProfile.setBounds(200,0,100,50);
                    profilePanel.add(viewProfile);
                    viewingProfile = true;
                }
                if (viewingProfile && profilePresses % 2 == 0)
                {
                    profilePanel.setVisible(false);
                    mainMenuPanel.setVisible(true);
                    mainMenuPanel.add(viewProfile);
                    viewProfile.setText("Profile");
                    mainMenuSetup();
                    viewingProfile = false;

                }
            }
        });
    }



    public static void main(String[] args) throws IOException
    {
        Parser parser = new Parser(readFile(SAMPLE), new HashMap<>());
        indexer = new Indexer(readFile(KRAT), new HashMap<>());
        users = parser.map();
        tagMap = indexer.index();
        loginSetup();
        loginActionListener();
        registerSwitchActionListener();
        tagSearchButtonActionListener();
        tagSearchNextButtonActionListener();
        tagSearchLastButtonActionListener();
        profileButtonActionListener();
    }

    public static byte[] readFile(String path) throws IOException
    {
        return Files.readAllBytes(Paths.get(path));
    }
}
