import javax.swing.*;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

// the ticket buying class contains the ticket buying frame and the Java swing elements that make it work
public class TicketBuyingPage {
    /* creating instances of the important elements like the main frame that holds all the elements and other
     * important data structures like the list and combo box.
     */
    private final JFrame ticketFrame = new JFrame("Welcome to Ticket Buying");
    private final JTextField moneyField = new JTextField();
    private final JComboBox<String> fightsComboBox = new JComboBox<>();
    private final JSlider ticketsSlider = new JSlider(JSlider.HORIZONTAL, 0, 15, 0);
    private final JList<String> purchasesList = new JList<>();
    private final List<Purchase> purchases = new ArrayList<>();
    private final String currentUser = LogInPage.getUserName();
    private final Map<String, Integer> availableFights = new HashMap<>();

    // base constructor for the class that runs when an object of the class is instantiated.
    public TicketBuyingPage() {
        // creating and setting elements in the frame
        availableFights.put("Fight 1", 15);
        availableFights.put("Fight 2", 15);
        availableFights.put("Fight 3", 15);
        JTextArea userNameLabel = new JTextArea();
        JLabel moneyLabel = new JLabel("Money available:");
        JLabel ticketsLabel = new JLabel("Number of tickets:");
        JButton buyButton = new JButton("Buy Tickets");
        JButton deleteButton = new JButton("Delete Tickets");
        JButton backToLogIn = new JButton("login");
        userNameLabel.setText("1 ticket = $25");
        userNameLabel.setBounds(10, 10, 200, 20);
        userNameLabel.setLineWrap(true);
        userNameLabel.setEditable(false);
        moneyLabel.setBounds(10, 40, 120, 20);
        moneyField.setForeground(new Color(0, 143, 17));
        moneyField.setBounds(130, 40, 80, 20);
        moneyField.setToolTipText("Enter any Amount of money > 0");
        fightsComboBox.setBounds(10, 70, 200, 20);
        fightsComboBox.addItem("Fight 1");
        fightsComboBox.addItem("Fight 2");
        fightsComboBox.addItem("Fight 3");
        ticketsLabel.setBounds(10, 100, 120, 20);
        ticketsSlider.setBounds(130, 100, 200, 50);
        ticketsSlider.setMajorTickSpacing(5);
        ticketsSlider.setMinorTickSpacing(1);
        ticketsSlider.setPaintTicks(true);
        ticketsSlider.setPaintLabels(true);
        buyButton.setBounds(10, 160, 100, 30);
        deleteButton.setBounds(120, 160, 120, 30);
        backToLogIn.setBounds(250, 160, 100, 30);

        // adding functionality to the back-to-login button using a lambda function
        backToLogIn.addActionListener(e -> {
            ticketFrame.dispose();
            new LogInPage();
        });

        // adding and creating other important Java swing elements to the ticket buying frame
        purchasesList.setBounds(10, 200, 320, 100);
        purchasesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(purchasesList);
        scrollPane.setBounds(10, 200, 320, 100);

        ticketFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ticketFrame.getContentPane().setBackground(Color.CYAN);
        ticketFrame.setSize(360, 350);
        ticketFrame.setResizable(false);
        ticketFrame.setLayout(null);
        ticketFrame.add(backToLogIn);
        ticketFrame.add(userNameLabel);
        ticketFrame.add(moneyLabel);
        ticketFrame.setLocationRelativeTo(null);
        ticketFrame.add(moneyField);
        ticketFrame.add(fightsComboBox);
        ticketFrame.add(ticketsLabel);
        ticketFrame.add(ticketsSlider);
        ticketFrame.add(buyButton);
        ticketFrame.add(deleteButton);
        ticketFrame.add(scrollPane);
        ticketFrame.setVisible(true);

        // adding functionality to the buy Button using a lambda functionality
        buyButton.addActionListener(e -> {
            // getting and converting the selected item from the combo box and casting it to a string
            String selectedFight = (String) fightsComboBox.getSelectedItem();
            int numTickets = ticketsSlider.getValue();
            double availableMoney = Double.parseDouble(moneyField.getText());

            if (numTickets > 0 && numTickets <= 15 && availableMoney >= numTickets * 25) {
                int availableTickets = availableFights.getOrDefault(selectedFight, 0);
                if (numTickets <= availableTickets) {
                    double totalCost = numTickets * 25;
                    Purchase purchase = new Purchase(currentUser, selectedFight, numTickets, totalCost);
                    purchases.add(purchase);
                    updatePurchasesList();

                    double remainingMoney = availableMoney - totalCost;
                    moneyField.setText(String.valueOf(remainingMoney));
                    updateAvailableTickets(selectedFight, -numTickets);
                    saveTransaction(purchase);
                } else {
                    JOptionPane.showMessageDialog(ticketFrame, "Insufficient tickets available for this fight.");
                }
            } else {
                JOptionPane.showMessageDialog(ticketFrame, "Invalid number of tickets or insufficient funds.");
            }
        });

        deleteButton.addActionListener(e -> {
            if (purchases.isEmpty()) {
                JOptionPane.showMessageDialog(ticketFrame, "No purchases to delete.");
            } else {
                int selectedIndex = purchasesList.getSelectedIndex();
                if (selectedIndex >= 0 && selectedIndex < purchases.size()) {
                    Purchase purchase = purchases.remove(selectedIndex);
                    updatePurchasesList();
                    updateAvailableTickets(purchase.getFight(), purchase.getNumTickets());
                    double refundedMoney = purchase.getTotalCost();
                    double currentMoney = Double.parseDouble(moneyField.getText());
                    double updatedMoney = currentMoney + refundedMoney;
                    moneyField.setText(String.valueOf(updatedMoney));
                    deleteTransaction(purchase);
                } else {
                    JOptionPane.showMessageDialog(ticketFrame, "Please select a purchase to delete.");
                }
            }
        });
    }


    public void updatePurchasesList() {
        purchasesList.setListData(purchases.stream().map(Purchase::toString).toArray(String[]::new));
    }

    public void updateAvailableTickets(String fight, int numTickets) {
        if (availableFights.containsKey(fight)) {
            int availableTickets = availableFights.get(fight);
            availableTickets += numTickets;
            availableFights.put(fight, availableTickets);
        }
    }


    public void saveTransaction(Purchase purchase) {
        String transactionData = purchase.getUsername() + ", Fight Number: " + purchase.getFight() + ", Number of tickets: " + purchase.getNumTickets() + ", cost: " + purchase.getTotalCost() + "\n";
        try {
            Files.write(Path.of("transactions.txt"), transactionData.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(ticketFrame, "Failed to save transaction.");
        }
    }
    public void deleteTransaction(Purchase purchase) {
        String transactionData = purchase.getUsername() + "," + purchase.getFight() + "," + purchase.getNumTickets();
        try {
            List<String> lines = Files.readAllLines(Path.of("transactions.txt"));
            lines.remove(transactionData);
            Files.write(Path.of("transactions.txt"), String.join("\n", lines).getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(ticketFrame, "Failed to delete transaction.");
        }
    }
}
