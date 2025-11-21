package kurt.ui;

import kurt.access.Parser;
import kurt.access.User;

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
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JFrame;


public class GUI
{

    static final String PATH = "CSProject/kurtCollab-main/src/kurt/access/sample.vff";
    static JFrame frame = new JFrame("Kurt Collab");
    static JTextField text = new JTextField(50);
    static JButton enterButton = new JButton("Enter");
    static JPanel panel = new JPanel();
    static TextPrompt testTextPrompt = new TextPrompt("Enter Username", text);
    static JLabel label = new JLabel();
    static int buttonPresses = 0;
    //static User user = new User();
    static boolean usernameRight = false;
    static String usernameStorage = "";


    public static void initialSetup()
    {
        frame.setResizable(false);
        frame.setSize(400, 400);
        panel.setLayout(null);
        text.setBounds(60,0,200,50);
        enterButton.setBounds(260, 0, 100, 100);
        label.setBounds(5, 50, 300, 50);
        //System.out.println(text.getBounds());
        //System.out.println(enterButton.getBounds());
        //System.out.println(label.getBounds());
        testTextPrompt.setForeground( Color.BLACK );
        testTextPrompt.changeAlpha(0.4f);
        testTextPrompt.changeStyle(Font.BOLD + Font.ITALIC);
        panel.add(text);
        enterButton.setPreferredSize(new Dimension(200, 100));
        panel.add(enterButton);
        panel.setBackground(Color.GRAY);
        panel.add(label);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static String getUsername() throws IOException
    {
        Parser parser = new Parser(readFile(PATH), new HashMap<>());
        Map<String, User> users = parser.map();
        String username = text.getText();
        User user = users.get(username);
        if (user == null)
        {
            return "";
        }
        return username;

    }
    public static boolean checkingUsername() throws IOException
    {
        Parser parser = new Parser(readFile(PATH), new HashMap<>());
        String username = text.getText();
        Map<String, User> users = parser.map();
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
        Parser parser = new Parser(readFile(PATH), new HashMap<>());
        String password = text.getText();
        Map<String, User> users = parser.map();
        User user = users.get(usernameStorage);
        if (!getUsername().isEmpty());
        {
            if (user.getPassword().equals(password))
            {
                text.setText("");
                return true;
            }
        }
        return false;
    }

    public static void initialActionListener()
    {
        enterButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    if (checkingUsername() == true && usernameRight == false)
                    {
                        label.setText("Hello, " + getUsername() + ". Enter Your Password.");
                        testTextPrompt.setText("Enter Password");
                        usernameRight = true;
                        text.setText("");
                        buttonPresses++;
                        //enterButton.removeActionListener(this);
                    }
                    else if (checkingUsername() == false && usernameRight == false)
                    {
                        label.setText("Username Not Found. Try Again!");
                        System.out.println("akdmkasdk");
                        //initialActionListener();
                    }

                    if (usernameRight == true)
                    {
                        if (checkingPassword() == true)
                        {
                            label.setText("Welcome to Kurt Collab!");
                            enterButton.setVisible(false);
                            text.setVisible(false);
                            label.setBounds(100, 100, 200, 200);
                        }
                        else if (checkingPassword() == false)
                        {
                            System.out.println("ferferfer");
                            if (buttonPresses > 1)
                            {
                                label.setText("Incorrect Password. Try Again!");
                            }
                            buttonPresses++;
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

    public static void main(String[] args) throws IOException
    {
        Parser parser = new Parser(readFile(PATH), new HashMap<>());
        Map<String, User> users = parser.map();
        initialSetup();
        initialActionListener();
    }

    public static byte[] readFile(String path) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }
}
