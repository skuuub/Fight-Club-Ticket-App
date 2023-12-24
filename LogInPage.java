import javax.swing.*;
import java.awt.*;
import java.io.*;

// log in page class that contains the code that creates a JFrame and other java swing elements that make the login page
public class LogInPage {

    // creating instance of the frame that contains the signup button, login button and clear button.
    JButton b2 = new JButton("Sign Up");
    JFrame logInFrame = new JFrame("Log In Page");
    JButton loginButton = new JButton("Log in");
    JButton clearButton = new JButton("Clear");

    //creating instance of Password fields, text labels, and an image icon object that had the background picture.
    static JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();
    JLabel userIdLabel = new JLabel("User ID");
    JLabel userPasswordLabel = new JLabel("Password");
    JLabel messageLabel = new JLabel();
    JLabel bgImage = new JLabel();
    ImageIcon icon = new ImageIcon("castle.jpg");

    /* constructor for the LogInPage class that contains the main operation to be run when an instance of the object is
       created
    */
    LogInPage() {
        /* setting up the layout and bounds for the background image, text labels, text files, etc.*/
        bgImage.setIcon(icon);
        bgImage.setBounds(0,-200,1000,1000);

        userIdLabel.setBounds(50,100,75,25);
        userIdLabel.setForeground(Color.white);
        userPasswordLabel.setBounds(50,150,75,25);
        userPasswordLabel.setForeground(Color.white);

        messageLabel.setBounds(100,300,250,35);
        messageLabel.setFont(new Font(null,Font.ITALIC,20));

        userIDField.setBounds(125,100,200,25);
        userPasswordField.setBounds(125,150,200,25);

        loginButton.setBounds(125,200,100,25);
        loginButton.setFocusable(false);
        /* adding functionality to the login button using a lambda function */
        loginButton.addActionListener(ae -> {
            // if statements that checks if and Admin is logging in, by checking if their input matches "Admin"
            if(userIDField.getText().equals("Admin") && String.valueOf(userPasswordField.getPassword()).equals("Admin")) {
                new AdminInfo();
            }

            /*  the below code uses FileReader and BufferedReader classes to read through the "logInInfo.txt" form the
                signup page to see if the username and password match and if they match, the boolean value is set to
                true and, the ticket buying page is open, if not, it sets a text label to display "no match"*/
            boolean matched = false;
            String uname = userIDField.getText();
            String pwd = String.valueOf(userPasswordField.getPassword());

            try{
                FileReader fr = new FileReader("logInInfo.txt");
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.equals(uname + ":" + pwd)) {
                        matched = true;
                        break;
                    }
                }
                fr.close();
            } catch (Exception ignored) {}

            if (matched) {
                logInFrame.dispose();
                new TicketBuyingPage();
            }else {
                messageLabel.setForeground(Color.red);
                messageLabel.setText("Incorrect username or password");
            }
        });


        b2.setBounds(125,250,100,25);

        // adding functionality to the signup button using a lambda function
        b2.addActionListener(ae -> {
            SignUpPage s = new SignUpPage();
            s.setVisible(true);
            s.setBounds(200,200,500,300);
            s.setLocationRelativeTo(null);
            s.getContentPane().setBackground(new Color(13, 2, 8));
        });

        clearButton.setBounds(225,200,100,25);
        clearButton.setFocusable(false);

        // adding functionality to the clear button using a lambda function
        clearButton.addActionListener(e -> {
            userIDField.setText("");
            userPasswordField.setText("");
            messageLabel.setText("");
        });

        // adding all the important components to the login frame
        logInFrame.add(b2);
        logInFrame.add(userIdLabel);
        logInFrame.add(userPasswordLabel);
        logInFrame.add(messageLabel);
        logInFrame.add(userIDField);
        logInFrame.add(userPasswordField);
        logInFrame.add(loginButton);
        logInFrame.add(clearButton);
        logInFrame.add(bgImage);
        logInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logInFrame.setSize(1000,600);
        logInFrame.setLayout(null);
        logInFrame.setVisible(true);
    }

    // getter method to get the username for use in other classes
    public static String getUserName() {
        return userIDField.getText();
    }
}