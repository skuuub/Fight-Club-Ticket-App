import javax.swing.*;
import javax.swing.border.Border;
import java.awt.Color;
import java.io.FileWriter;

// sign up class that contains the Signup page JFrame and other important java swing elements
public class SignUpPage extends JFrame {
    // instantiating important elements like text fields, buttons, and text labels
    JTextField newUsernameField,newPasswordField;
    JButton submitButton;
    JLabel newUserName, newPassword;

    // base constructor that contains the main operations of the class
    SignUpPage () {
        // creating and setting elements in the signup page frame
        setLayout(null);
        Border border = BorderFactory.createLineBorder(new Color(0, 143, 17), 3);
        newUserName = new JLabel(" New Username");
        newUserName.setForeground(new Color(0, 143, 17));
        newPassword = new JLabel(" New Password");
        newPassword.setForeground(new Color(0, 143, 17));
        newPassword.setBounds(10,60,100,30);
        newUserName.setBounds(10,20,100,30);
        newUsernameField = new JTextField(60);
        newUsernameField.setForeground(new Color(0, 255, 65));
        newUsernameField.setBackground(new Color(13, 2, 8));
        newUsernameField.setCaretColor(new Color(0, 143, 17));
        newUsernameField.setBorder(border);
        newPasswordField = new JPasswordField(60);
        newPasswordField.setForeground(new Color(0, 255, 65));
        newPasswordField.setBackground(new Color(13, 2, 8));
        newPasswordField.setCaretColor(new Color(0, 143, 17));
        newPasswordField.setBorder(border);
        submitButton = new JButton("Submit");
        newUsernameField.setBounds(150,20,80,30);
        newPasswordField.setBounds(150,60,80,30);
        submitButton.setBounds(200,200,80,30);

        // adding functionality to teh submit button using a lambda function
        submitButton.addActionListener(ae -> {
            /* the below code uses the FileWriter class to read in the username and password gotten form the text fields
             * and reads them into a file called "logInInfo.txt"*/
            try {
                FileWriter fw = new FileWriter("logInInfo.txt",true);
                fw.write(newUsernameField.getText() + ":" + newPasswordField.getText() + "\n");
                fw.close();
                JFrame f = new JFrame();
                JOptionPane.showMessageDialog(f,"Registration completed Successfully");
                dispose();
            } catch (Exception ignored) {}
        });
        // adding all the created elements to the signup page frame.
        add(newUserName);
        add(newPassword);
        add(newUsernameField);
        add(newPasswordField);
        add(submitButton);
    }
}