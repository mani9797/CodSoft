import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        balance = initialBalance;
    }
    public double getBalance() {
        return balance;
    }
    public void deposit(double amount) {
        balance += amount;
    }
    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false; 
        }
        balance -= amount;
        return true;
    }
}
public class ATM extends JFrame {
    private BankAccount bankAccount;
    private static final String API_BASE_URL = "https://api.examplebank.com/";

    public ATM(BankAccount account) {
        super("ATM Machine");
        bankAccount = account;

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton checkBalanceButton = new JButton("Check Balance");

        JLabel infoLabel = new JLabel("Welcome to the ATM Machine");

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(null, "Enter withdrawal amount:");
                if (input != null && !input.isEmpty()) {
                    double amount = Double.parseDouble(input);
                    boolean withdrawalStatus = performWithdrawal(amount);
                    if (withdrawalStatus) {
                        JOptionPane.showMessageDialog(null, "Withdrawal successful. New balance: " + bankAccount.getBalance());
                    } else {
                        JOptionPane.showMessageDialog(null, "Insufficient funds. Withdrawal failed.");
                    }
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(null, "Enter deposit amount:");
                if (input != null && !input.isEmpty()) {
                    double amount = Double.parseDouble(input);
                    bankAccount.deposit(amount);
                    JOptionPane.showMessageDialog(null, "Deposit successful. New balance: " + bankAccount.getBalance());
                }
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Your current balance: " + bankAccount.getBalance());
            }
        });

        add(infoLabel);
        add(withdrawButton);
        add(depositButton);
        add(checkBalanceButton);
    }

    private boolean performWithdrawal(double amount) {
        try {
            URL url = new URL(API_BASE_URL + "withdraw");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String postData = "amount=" + amount;
            conn.getOutputStream().write(postData.getBytes());

            int responseCode = conn.getResponseCode();
            conn.disconnect();

            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000); // Initial balance
        ATM atm = new ATM(account);
        atm.setVisible(true);
    }
}
