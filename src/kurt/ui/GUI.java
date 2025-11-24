package kurt.ui;

import kurt.access.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JFrame;

import static kurt.access.Kurt.*;


public class GUI
{

    //public static Indexer indexer;
    static int universalTagMax = 0;
    static boolean tagNeeded = false;
    static int profilePresses = 0;
    static int ratePresses = 0;
    static int postPresses = 0;
    //private static Map<String, List<Post>> tagMap = new HashMap<>();
    //private static Indexer indexer;
    private static User currentUser = null;
    static final String DUMP = "C:/Users/jacob/GitHub/kurtCollab-main/src/kurt/access/files/posts.dump";
    static final String SAMPLE = "C:/Users/jacob/GitHub/kurtCollab-main/src/kurt/access/files/sample.vff";
    static final String KRAT = "C:/Users/jacob/GitHub/kurtCollab-main/src/kurt/access/files/index.krat";


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
    static TextPrompt loginTagDisplayAreaTextPrompt = new TextPrompt("View Posts Here", loginTagDisplayArea);
    static JLabel loginTagDisplayAuthor = new JLabel();
    static JLabel loginTagDisplayLength = new JLabel();
    static JLabel loginTagDisplayReputation = new JLabel();
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
    static JLabel profilePosts = new JLabel();
    static JLabel profileReputation = new JLabel();

    static JLabel mainTagSearch = new JLabel();
    static JButton mainTagSearchButton = new JButton("Search");
    static JTextField mainTagSearchText = new JTextField(50);
    static TextPrompt mainTagSearchTextPrompt = new TextPrompt("Enter Tag", mainTagSearchText);
    static JTextArea mainTagDisplayArea = new JTextArea();
    static TextPrompt mainTagDisplayAreaTextPrompt = new TextPrompt("View Posts Here", mainTagDisplayArea);
    static JLabel mainTagDisplayAuthor = new JLabel();
    static JLabel mainTagDisplayLength = new JLabel();
    static JLabel mainTagDisplayNumberOfPosts = new JLabel();
    static JButton mainTagNextButton = new JButton("Next");
    static JButton mainTagPreviousButton = new JButton("Last");

    static JButton postButton = new JButton("Enter");
    static JTextArea mainPostDisplayArea = new JTextArea();
    static JLabel mainPostLabel  = new JLabel();
    static JTextField mainTagPostField = new JTextField(50);


    static JPanel rateUserPanel = new JPanel();
    static JButton rateUserSwitchButton = new JButton("Rate");
    static JButton rateUserEnterButton = new JButton("Enter");
    static JTextField rateUserText = new JTextField(50);
    static TextPrompt rateUserTextPrompt = new TextPrompt("Enter Username", rateUserText);
    static JLabel rateUserLabel = new JLabel();
    static JTextField rateUserNumberText = new JTextField(50);
    static TextPrompt rateUserNumberTextPrompt = new TextPrompt("Enter Rating",  rateUserNumberText);

    static JButton logoutButton = new JButton("Logout");

    //Main Menu Variables
    static boolean viewingProfile = false;
    static boolean ratingCurrently = false;





    public static void loginSetup()
    {
        firstFrame.setResizable(false);
        firstFrame.setSize(700, 350);
        loginPanel.setLayout(null);
        loginText.setBounds(0,0,200,50);
        loginPanel.setBackground(Color.GRAY);
        loginEnterButton.setBounds(200, 0, 100, 100);
        loginLabel.setBounds(5, 50, 300, 50);
        loginTextPrompt.setForeground(Color.BLACK);
        loginTextPrompt.changeAlpha(0.4f);
        loginTextPrompt.changeStyle(Font.BOLD + Font.ITALIC);
        loginPanel.add(loginText);
        loginPanel.add(loginEnterButton);
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
        loginTagSearchTextPrompt.setForeground(Color.BLACK);
        loginTagSearchTextPrompt.changeAlpha(0.4f);
        loginTagSearchTextPrompt.changeStyle(Font.BOLD + Font.ITALIC);
        loginPanel.add(loginTagSearchText);
        loginTagSearchButton.setBounds(400,0,100,100);
        loginPanel.add(loginTagSearchButton);
        loginTagDisplayArea.setBounds(400,150,250,100);
        loginTagDisplayArea.setText("");
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
        loginTagDisplayLength.setBounds(500,110,200,50);
        loginTagDisplayNumberOfPosts.setBounds(320,265,200,50);
        loginTagDisplayAreaTextPrompt.setForeground(Color.BLACK);
        loginTagDisplayAreaTextPrompt.changeAlpha(0.4f);
        loginTagDisplayAreaTextPrompt.changeStyle(Font.BOLD + Font.ITALIC);
        loginPanel.add(loginTagDisplayLength);
        loginPanel.add(loginTagDisplayNumberOfPosts);
        loginTagPreviousButton.setVisible(true);
        loginTagNextButton.setVisible(true);
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
                        User user = users.get(usernameStorage);
                        currentUser = user;
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
                            loginLabel.setText("");
                            //loginEnterButton.setVisible(false);
                            //loginText.setVisible(false);
                            //loginLabel.setBounds(100, 100, 200, 200);
                            //loginPanel.setVisible(false);
                            //firstFrame.setVisible(false);
                            loginPanel.setVisible(false);
                            tagCounter = 1;
                            loginTagPreviousButton.removeActionListener(this);
                            loginTagNextButton.removeActionListener(this);
                            System.out.println(currentUser.getPosts());
                            System.out.println(currentUser.getEmail());
                            System.out.println(currentUser.getDob());
                            loginTextPrompt.setText("Enter Username");
                            loginPresses = 0;
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
                    registerTextPrompt.setText("Enter a DOB (MM/DD/YYYY)");
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
                    users.put(username, newUser);

                    try {
                        saveAllUsers();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }


                    registerPanel.setVisible(false);
                    loginPanel.setVisible(true);
                    loginLabel.setText("Registration Successful!");
                    registerLabel.setText("");
                    registerButtonPresses = 0;
                    emailEntered = false;
                    usernameEntered = false;
                    passwordEntered = false;
                    DOBEntered = false;
                    emailEntered = false;
                }
            }
        });
    }

    public static void loginTagSearching()
    {
        String tag = loginTagSearchText.getText();

        List<Post> posts = Kurt.tagMap.get(tag);
        if (posts == null || posts.isEmpty()) {
            System.out.println("No posts found for tag: " + tag);
            return;
        }

        System.out.println("\nFound " + posts.size() + " post(s) for tag '" + tag + "':");
        Post post = posts.get(tagCounter-1);
        universalTagMax = posts.size();
        String snippet = getSnippet(post);
        loginTagDisplayArea.setText(snippet);
        loginTagDisplayAuthor.setText("" + post);
        loginTagDisplayLength.setText("Length: " + snippet.length() + " characters");
        loginTagDisplayNumberOfPosts.setText("Post Number: " + tagCounter + "/" + posts.size());
    }
    public static void mainTagSearching()
    {
        String tag = mainTagSearchText.getText();

        List<Post> posts = Kurt.tagMap.get(tag);
        if (posts == null || posts.isEmpty()) {
            System.out.println("No posts found for tag: " + tag);
            return;
        }

        System.out.println("\nFound " + posts.size() + " post(s) for tag '" + tag + "':");
        Post post = posts.get(tagCounter-1);
        universalTagMax = posts.size();
        String snippet = getSnippet(post);
        mainTagDisplayArea.setText(snippet);
        mainTagDisplayAuthor.setText("" + post);
        mainTagDisplayLength.setText("Length: " + snippet.length() + " characters");
        mainTagDisplayNumberOfPosts.setText("Post Number: " + tagCounter + "/" + posts.size());

    }
    public static void mainMenuSetup()
    {
        mainMenuPanel.setLayout(null);
        mainMenuPanel.setBackground(Color.GRAY);
        firstFrame.add(mainMenuPanel);
        viewProfile.setBounds(0,0,100,50);
        mainMenuPanel.add(viewProfile);
        postButton.setBounds(0, 100, 100, 50);
        mainMenuPanel.add(postButton);
        mainPostDisplayArea.setBounds(100, 100, 150, 150);
        mainMenuPanel.add(mainPostDisplayArea);
        mainPostLabel.setBounds(100, 50, 300, 50);
        mainPostLabel.setText("Make a post here! Enter tag on the right!");
        mainMenuPanel.add(mainPostLabel);
        mainPostDisplayArea.setLineWrap(true);
        mainPostDisplayArea.setWrapStyleWord(true);
        mainTagPostField.setBounds(251, 100, 150, 50);
        mainMenuPanel.add(mainTagPostField);
        logoutButton.setBounds(0, 265, 100, 50);
        mainMenuPanel.add(logoutButton);
        mainMenuPanel.setVisible(true);


        //Tag Stuff
        mainTagSearch.setBounds(505,50,300,50);
        mainTagSearch.setText("Search for posts by tag here!");
        mainMenuPanel.add(mainTagSearch);
        mainTagSearchText.setBounds(500,0,200,50);
        mainTagSearchTextPrompt.setForeground(Color.BLACK);
        mainTagSearchTextPrompt.changeAlpha(0.4f);
        mainTagSearchTextPrompt.changeStyle(Font.BOLD + Font.ITALIC);
        mainMenuPanel.add(mainTagSearchText);
        mainTagSearchButton.setBounds(400,0,100,100);
        mainMenuPanel.add(mainTagSearchButton);
        mainTagDisplayArea.setBounds(450,180,250,100);
        mainTagDisplayArea.setText("");
        mainTagDisplayArea.setLineWrap(true);
        mainTagDisplayArea.setWrapStyleWord(true);
        mainTagDisplayArea.setEditable(false);

        mainMenuPanel.add(mainTagDisplayArea);
        mainTagDisplayAuthor.setBounds(420,140,200,50);
        mainTagNextButton.setBounds(390,175,60,60);
        mainMenuPanel.add(mainTagNextButton);
        mainTagPreviousButton.setBounds(390,235,60,60);
        mainMenuPanel.add(mainTagPreviousButton);
        mainMenuPanel.add(mainTagDisplayAuthor);
        mainTagDisplayLength.setBounds(520,140,200,50);
        mainTagDisplayNumberOfPosts.setBounds(460,265,200,50);
        mainTagDisplayAreaTextPrompt.setForeground(Color.BLACK);
        mainTagDisplayAreaTextPrompt.changeAlpha(0.4f);
        mainTagDisplayAreaTextPrompt.changeStyle(Font.BOLD + Font.ITALIC);
        mainMenuPanel.add(mainTagDisplayLength);
        mainMenuPanel.add(mainTagDisplayNumberOfPosts);
        mainTagPreviousButton.setVisible(true);
        mainTagNextButton.setVisible(true);

        //Rating Users
        rateUserSwitchButton.setBounds(275, 265, 100, 50);
        mainMenuPanel.add(rateUserSwitchButton);
    }

    public static void profileSetup()
    {
        profilePanel.setLayout(null);
        profilePanel.setBackground(Color.GRAY);
    }

    public static void rateUserSetup()
    {
        rateUserPanel.setLayout(null);
        rateUserPanel.setBackground(Color.GRAY);
        rateUserEnterButton.setBounds(0, 125, 100, 50);
        rateUserPanel.add(rateUserEnterButton);
        rateUserText.setBounds(100,125,150,50);
        rateUserPanel.add(rateUserText);
        rateUserTextPrompt.setForeground(Color.BLACK);
        rateUserTextPrompt.changeAlpha(0.4f);
        rateUserTextPrompt.changeStyle(Font.BOLD + Font.ITALIC);
        rateUserLabel.setBounds(50,180,300,50);
        rateUserLabel.setText("Enter a Username! Rating on the right!");
        rateUserPanel.add(rateUserLabel);
        rateUserNumberText.setBounds(255, 125, 150, 50);
        rateUserPanel.add(rateUserNumberText);
        rateUserNumberTextPrompt.setForeground(Color.BLACK);
        rateUserNumberTextPrompt.changeAlpha(0.4f);
        rateUserNumberTextPrompt.changeStyle(Font.BOLD + Font.ITALIC);
    }

    public static void tagSearchButtonActionListener()
    {
        loginTagSearchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tagSearchNextButtonActionListener();
                tagSearchLastButtonActionListener();
                tagCounter = 1;
                loginTagSearching();
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
                if (tagCounter-1 != universalTagMax-1)
                {
                    tagCounter++;
                }
                loginTagSearching();
            }
        });
    }
    public static void tagSearchLastButtonActionListener()
    {
        loginTagPreviousButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tagCounter != 1)
                {
                    tagCounter--;
                }
                loginTagSearching();
            }
        });
    }

    public static void mainTagSearchButtonActionListener()
    {
        mainTagSearchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainTagSearchNextButtonActionListener();
                mainTagSearchLastButtonActionListener();
                tagCounter = 1;
                mainTagSearching();
            }
        });
    }
    public static void mainTagSearchNextButtonActionListener()
    {
        mainTagNextButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (tagCounter-1 != universalTagMax-1)
                {
                    tagCounter++;
                }
                mainTagSearching();
            }
        });
    }
    public static void mainTagSearchLastButtonActionListener()
    {
        mainTagPreviousButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tagCounter != 1)
                {
                    tagCounter--;
                }
                mainTagSearching();
            }
        });
    }

    public static void logoutButtonActionListener()
    {
        logoutButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                currentUser = null;
                mainMenuPanel.setVisible(false);
                loginPanel.setVisible(true);
                usernameRight = false;
                tagCounter = 1;
            }
        });

    }
    public static void postingButtonActionListener()
    {
        postButton.addActionListener(new ActionListener()
        {
            String content = "";
            String tag = "";
            @Override
            public void actionPerformed(ActionEvent e) {
                Creator creator = new Creator(currentUser);
                postPresses++;
                content = mainPostDisplayArea.getText();
                System.out.println(content);
                long offset;
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
                System.out.println(currentUser.getPosts());
                tag = mainTagPostField.getText();
                System.out.println(tag);
                mainPostDisplayArea.setText("");
                mainTagPostField.setText("");
                Kurt.indexer.post(post, tag, Kurt.tagMap);
                try
                {
                    saveAllPosts();
                    saveAllUsers();
                }
                catch (IOException ex)
                {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static void rateSwitchButtonActionListener()
    {
        rateUserSetup();
        rateUserSwitchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ratePresses++;
                if (!ratingCurrently && ratePresses % 2 != 0)
                {
                    firstFrame.add(rateUserPanel);
                    rateUserPanel.setVisible(true);
                    mainMenuPanel.setVisible(false);
                    rateUserPanel.add(rateUserSwitchButton);
                    rateUserPanel.setBackground(Color.GRAY);
                    rateUserSwitchButton.setText("Back");
                    ratingCurrently = true;
                }
                if (ratingCurrently && ratePresses % 2 == 0)
                {
                    rateUserPanel.setVisible(false);
                    mainMenuPanel.setVisible(true);
                    mainMenuSetup();
                    rateUserSwitchButton.setText("Rate");
                    rateUserText.setText("");
                    rateUserNumberText.setText("");
                    rateUserLabel.setText("Enter a Username! Rating on the right!");
                    ratingCurrently = false;

                }
            }
        });
    }
    public static void rateEnterButtonActionListener()
    {
        rateUserEnterButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String targetUser = rateUserText.getText();
                int rating = Integer.parseInt(rateUserNumberText.getText());
                User user = users.get(targetUser);
                if (user == null)
                {
                    rateUserLabel.setText("Username does not exist!");
                    return;
                }
                float newRating = rating;
                float currentRating = user.getReputation();
                float averageRating = (newRating + currentRating) / 2;
                Creator creator = new Creator(user);
                creator.add(new Field.Reputation(averageRating));
                rateUserLabel.setText("Success! " + targetUser + " now has a rating of " + averageRating);
                rateUserText.setText("");
                rateUserNumberText.setText("");

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
                    mainMenuPanel.setVisible(false);
                    profileUsername.setBounds(0,0,100,50);
                    profileDOB.setBounds(0,50,200,50);
                    profileUsername.setText("Username: " + currentUser.getUsername());
                    profileDOB.setText("DOB: " + currentUser.getDob());
                    profileEmail.setBounds(0,100,200,50);
                    profileEmail.setText("Email: " + currentUser.getEmail());
                    profilePosts.setBounds(0, 150, 100, 50);
                    profilePosts.setText("Posts: " + currentUser.getPosts());
                    profileReputation.setBounds(0, 200, 100, 50);
                    profileReputation.setText("Reputation: " + currentUser.getReputation());
                    profilePanel.add(profileUsername);
                    profilePanel.add(profileDOB);
                    profilePanel.add(profileEmail);
                    profilePanel.add(profilePosts);
                    profilePanel.add(profileReputation);
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
        Kurt.indexer = new Indexer(readFile(KRAT), new HashMap<>());
        users = parser.map();
        Kurt.tagMap = Kurt.indexer.index();
        loginSetup();
        loginActionListener();
        registerSwitchActionListener();
        registerActionListener();
        tagSearchButtonActionListener();
        mainTagSearchButtonActionListener();
        profileButtonActionListener();
        System.out.println("Username: " + usernameStorage);
        postingButtonActionListener();
        logoutButtonActionListener();
        rateSwitchButtonActionListener();
        rateEnterButtonActionListener();
    }

    public static byte[] readFile(String path) throws IOException
    {
        return Files.readAllBytes(Paths.get(path));
    }
}
