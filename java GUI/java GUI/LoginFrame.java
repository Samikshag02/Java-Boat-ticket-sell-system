import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class LoginFrame extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;
    boolean isAdmin;

    public LoginFrame(boolean isAdmin) {
        this.isAdmin = isAdmin;
        setTitle((isAdmin ? "Admin" : "User") + " Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setLocationRelativeTo(null);

        // Background image or fallback
        JLabel backgroundLabel;
        ImageIcon backgroundIcon = new ImageIcon("background.jpg");
        if (backgroundIcon.getIconWidth() > 0) {
            backgroundLabel = new JLabel(backgroundIcon);
        } else {
            backgroundLabel = new JLabel();
            backgroundLabel.setOpaque(true);
            backgroundLabel.setBackground(new Color(220, 230, 250));
        }
        backgroundLabel.setLayout(new GridBagLayout());

        // Title
        JLabel title = new JLabel((isAdmin ? "Admin" : "User") + " Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(30, 30, 90));

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setOpaque(false);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 10, 50));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton loginBtn = createStyledButton("Login");
        JButton backBtn = createStyledButton("Back");

        loginBtn.addActionListener(e -> login());
        backBtn.addActionListener(e -> {
            dispose();
            new MainMenu();  // Ensure this is working correctly and that the frame is not being disposed too soon
        });

        buttonPanel.add(loginBtn);
        buttonPanel.add(backBtn);

        // Center container
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.add(title, BorderLayout.NORTH);
        container.add(inputPanel, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);

        backgroundLabel.add(container);
        setContentPane(backgroundLabel);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 120, 215));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        return button;
    }

    void login() {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword());

        // Read the users from the stored data
        Map<String, String> users = Utils.readUsers();

        // Check if the user exists and the password is correct
        if (users.containsKey(user) && users.get(user).equals(pass)) {
            if (isAdmin && !user.equalsIgnoreCase("admin")) {
                JOptionPane.showMessageDialog(this, "Only 'admin' can log in as admin.");
                return;
            }

            // Close the login window and open the corresponding dashboard
            dispose();

            // Open the appropriate dashboard based on user type
            if (isAdmin) {
                new AdminDashboard(user);  // Make sure AdminDashboard is implemented properly
            } else {
                new UserDashboard(user);   // Make sure UserDashboard is implemented properly
            }
        } else {
            // If credentials are incorrect
            JOptionPane.showMessageDialog(this, "Invalid credentials");
        }
    }

    // Main method for testing purposes
    public static void main(String[] args) {
        // Test login frame for user (set isAdmin to false for user login)
        new LoginFrame(false);
    }
}
