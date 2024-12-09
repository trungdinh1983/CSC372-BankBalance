import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BankBalanceApp extends JFrame implements ActionListener {
  // Declare GUI components
  private JTextField balanceField;
  private JButton depositButton;
  private JButton withdrawButton;
  private JTextArea transactionHistoryArea;

  // Declare balance variable and transaction history list
  private double balance;
  private List<String> transactionHistory;

  public BankBalanceApp() {
    // Set up the JFrame
    setTitle("Bank Balance Application");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(400, 300));

    // Create a JPanel and set its layout
    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Create and configure GUI components
    JLabel balanceLabel = new JLabel("Balance:");
    balanceField = new JTextField();
    balanceField.setEditable(false);

    depositButton = new JButton("Deposit");
    depositButton.addActionListener(this);

    withdrawButton = new JButton("Withdraw");
    withdrawButton.addActionListener(this);

    JLabel transactionHistoryLabel = new JLabel("Transaction History:");
    transactionHistoryArea = new JTextArea(10, 30);
    transactionHistoryArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(transactionHistoryArea);

    // Add components to the panel using GridBagConstraints
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.anchor = GridBagConstraints.WEST;
    constraints.insets = new Insets(5, 5, 5, 5);
    panel.add(balanceLabel, constraints);

    constraints.gridx = 1;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.weightx = 1.0;
    panel.add(balanceField, constraints);

    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.gridwidth = 2;
    constraints.fill = GridBagConstraints.NONE;
    constraints.anchor = GridBagConstraints.CENTER;
    constraints.weightx = 0.0;
    panel.add(depositButton, constraints);

    constraints.gridy = 2;
    panel.add(withdrawButton, constraints);

    constraints.gridx = 0;
    constraints.gridy = 3;
    constraints.gridwidth = 2;
    constraints.fill = GridBagConstraints.BOTH;
    constraints.weighty = 1.0;
    panel.add(scrollPane, constraints);

    // Add panel to the JFrame
    add(panel);
    pack();
    setLocationRelativeTo(null);

    // Initialize transaction history list
    transactionHistory = new ArrayList<>();
  }

  // ActionListener method to handle button clicks
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == depositButton) {
      // Handle deposit button click
      String input = JOptionPane.showInputDialog(this, "Enter deposit amount:");
      if (input != null) {
        try {
          double amount = Double.parseDouble(input);
          if (amount > 0) {
            balance += amount;
            updateBalance();
            addTransactionHistory("Deposit", amount);
          } else {
            JOptionPane.showMessageDialog(this, "Invalid deposit amount.", "Error", JOptionPane.ERROR_MESSAGE);
          }
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    } else if (e.getSource() == withdrawButton) {
      // Handle withdraw button click
      String input = JOptionPane.showInputDialog(this, "Enter withdrawal amount:");
      if (input != null) {
        try {
          double amount = Double.parseDouble(input);
          if (amount > 0 && amount <= balance) {
            balance -= amount;
            updateBalance();
            addTransactionHistory("Withdrawal", amount);
          } else {
            JOptionPane.showMessageDialog(this, "Invalid withdrawal amount.", "Error", JOptionPane.ERROR_MESSAGE);
          }
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    }
  }

  // Method to update the balance displayed in the text field
  private void updateBalance() {
    balanceField.setText(String.format("$%.2f", balance));
  }

  // Method to add a transaction to the transaction history
  private void addTransactionHistory(String transactionType, double amount) {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String timestamp = now.format(formatter);
    String transaction = String.format("%s | %s: $%.2f | Balance: $%.2f",
        timestamp, transactionType, amount, balance);
    transactionHistory.add(transaction);
    updateTransactionHistoryArea();
  }

  // Method to update the transaction history text area
  private void updateTransactionHistoryArea() {
    StringBuilder sb = new StringBuilder();
    for (String transaction : transactionHistory) {
      sb.append(transaction).append("\n");
    }
    transactionHistoryArea.setText(sb.toString());
  }

  // Main method to run the application
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      BankBalanceApp app = new BankBalanceApp();
      app.setVisible(true);
    });
  }
}