import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class RegistrationFrame extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;

    public RegistrationFrame() {
        setTitle("User Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setLocationRelativeTo(null);

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
        JLabel title = new JLabel("User Registration", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(30, 30, 90));

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setOpaque(false);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 10, 50));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        inputPanel.add(new JLabel("New Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("New Password:"));
        inputPanel.add(passwordField);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton registerBtn = createStyledButton("Register");
        JButton backBtn = createStyledButton("Back");

        registerBtn.addActionListener(e -> register());
        backBtn.addActionListener(e -> {
            dispose();
            new MainMenu();
        });

        buttonPanel.add(registerBtn);
        buttonPanel.add(backBtn);

        // Combine panels
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
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(140, 40));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        return button;
    }

    void register() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        Map<String, String> users = Utils.readUsers();
        if (users.containsKey(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(username + "," + password);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Registration successful!");
            dispose();
            new MainMenu();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to register user.");
        }
    }
}
