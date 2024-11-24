import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankBalanceApp extends JFrame implements ActionListener {
  // Declare GUI components
  private JTextField balanceField;
  private JButton depositButton;
  private JButton withdrawButton;

  // Declare balance variable
  private double balance;

  public BankBalanceApp() {
    // Set up the JFrame
    setTitle("Bank Balance Application");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(300, 150));

    // Create a JPanel and set its layout
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(3, 2, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Create and configure GUI components
    JLabel balanceLabel = new JLabel("Balance:");
    balanceField = new JTextField();
    balanceField.setEditable(false);

    depositButton = new JButton("Deposit");
    depositButton.addActionListener(this);

    withdrawButton = new JButton("Withdraw");
    withdrawButton.addActionListener(this);

    // Add components to the panel
    panel.add(balanceLabel);
    panel.add(balanceField);
    panel.add(depositButton);
    panel.add(withdrawButton);

    // Add panel to the JFrame and set the frame properties
    add(panel);
    pack();
    setLocationRelativeTo(null);
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

  // Main method to run the application
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      BankBalanceApp app = new BankBalanceApp();
      app.setVisible(true);
    });
  }
}