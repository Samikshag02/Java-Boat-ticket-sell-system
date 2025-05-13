import javax.swing.*;
import java.awt.*;
import java.io.File;

class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Boat Ticketing System - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setUndecorated(false);
        setLocationRelativeTo(null);

        // Load background image (Rankala.png)
        ImageIcon backgroundIcon = new ImageIcon("img/Rankala.png"); // Path to your image
        JLabel backgroundLabel;

        if (backgroundIcon.getIconWidth() > 0) {
            backgroundLabel = new JLabel(backgroundIcon) {
                // Override the paintComponent method to stretch the image to fill the frame
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Image image = backgroundIcon.getImage();
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);  // Stretch image to fit the window
                }
            };
        } else {
            backgroundLabel = new JLabel();
            backgroundLabel.setOpaque(true);
            backgroundLabel.setBackground(new Color(200, 220, 240)); // fallback color if image not found
        }
        backgroundLabel.setLayout(new GridBagLayout());

        // Create buttons
        JButton adminLoginBtn = createStyledButton("Admin Login");
        JButton userLoginBtn = createStyledButton("User Login");
        JButton registerBtn = createStyledButton("User Registration");

        // Add action listeners
        adminLoginBtn.addActionListener(e -> {
            dispose();
            new LoginFrame(true);
        });

        userLoginBtn.addActionListener(e -> {
            dispose();
            new LoginFrame(false);
        });

        registerBtn.addActionListener(e -> {
            dispose();
            new RegistrationFrame();
        });

        // Layout using GridBagLayout for responsive center alignment
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(adminLoginBtn);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(userLoginBtn);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(registerBtn);
        buttonPanel.add(Box.createVerticalGlue());

        backgroundLabel.add(buttonPanel);
        setContentPane(backgroundLabel);
        setVisible(true);
    }
private JButton createStyledButton(String text) {
    JButton button = new JButton(text);
    button.setAlignmentX(Component.CENTER_ALIGNMENT);
    button.setFont(new Font("Arial", Font.BOLD, 18));
    button.setBackground(Color.WHITE);
    button.setForeground(Color.BLACK);
    button.setFocusPainted(false);

    // Dynamic size based on screen width
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int buttonWidth = (int) (screenSize.width * 0.2); // 20% of screen width
    button.setMaximumSize(new Dimension(buttonWidth, 50));

    button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
    ));

    // Add hover effect
    button.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            button.setBackground(new Color(220, 220, 220)); // Light gray on hover
            button.setForeground(Color.BLUE); // Change text color on hover
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            button.setBackground(Color.WHITE); // Reset to default background
            button.setForeground(Color.BLACK); // Reset to default text color
        }
    });

    return button;
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
