import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

class AdminInfo {

    JFrame adminFrame = new JFrame("Admin info");
    JTextArea infoTextArea = new JTextArea();
    JLabel welcomeL = new JLabel();

    AdminInfo() {
        adminFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        adminFrame.setSize(800, 800);
        adminFrame.setResizable(false);
        adminFrame.setLayout(new BorderLayout());

        welcomeL.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeL.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeL.setBorder(new EmptyBorder(10, 10, 10, 10));
        adminFrame.add(welcomeL, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(infoTextArea);
        adminFrame.add(scrollPane, BorderLayout.CENTER);

        displayInfo();

        adminFrame.setVisible(true);
    }

    private void displayInfo() {
        StringBuilder infoBuilder = new StringBuilder();
        infoBuilder.append("---- Transactions ----\n");

        // Read and display transaction information
        List<String> transactions = readTransactions();
        for (String transaction : transactions) {
            infoBuilder.append(transaction).append("\n");
        }

        // Read and display login/sign-up information
        infoBuilder.append("\n---- Logins/Sign-ups ----\n");
        List<String> logins = readLogins();
        for (String login : logins) {
            infoBuilder.append(login).append("\n");
        }

        infoTextArea.setText(infoBuilder.toString());
        infoTextArea.setEditable(false);
        infoTextArea.setCaretPosition(0);
    }

    private List<String> readTransactions() {
        List<String> transactions = new ArrayList<>();
        // Read the transactions from the "transactions.txt" file
        try {
            FileReader fr = new FileReader("transactions.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                transactions.add(line);
            }
            fr.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(adminFrame, "Failed to read transactions.");
        }
        return transactions;
    }

    private List<String> readLogins() {
        List<String> logins = new ArrayList<>();
        // Read the logins/sign-ups from the "logInInfo.txt" file
        try {
            FileReader fr = new FileReader("logInInfo.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                logins.add(line);
            }
            fr.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(adminFrame, "Failed to read logins.");
        }
        return logins;
    }
}
