
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

public class MainPage extends JFrame {

    public MainPage() {
        setTitle("Welcome to Rankala Boat Booking");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Load background image from img folder
        BackgroundPanel background = new BackgroundPanel("Rankala1.jpeg"); // Path relative to the root folder (BoatTicketSystem/)
        background.setLayout(new BorderLayout());

        // Transparent overlay panel
        JPanel overlay = new JPanel();
        overlay.setOpaque(false);
        overlay.setLayout(new BoxLayout(overlay, BoxLayout.Y_AXIS));
        overlay.setBorder(BorderFactory.createEmptyBorder(50, 150, -300, 150));

        // Heading "Welcome to Rankala Lake" at the top
        JLabel title = new JLabel("Welcome to Rankala Lake");
        title.setFont(new Font("Serif", Font.BOLD, 48));
        title.setForeground(Color.BLACK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Description below the title, centered in section
        JLabel desc = new JLabel("<html>Rankala Lake is a scenic lake located in Kolhapur, Maharashtra.<br>"
                + "It is a famous tourist spot known for its peaceful environment,<br>"
                + "boating activities, and beautiful sunset views.</html>");
        desc.setFont(new Font("SansSerif", Font.PLAIN, 24));
        desc.setForeground(Color.BLACK);
        desc.setHorizontalAlignment(SwingConstants.CENTER);
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button panel at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));

        JButton continueButton = new JButton("Click Here to Continue");
        setButtonStyle(continueButton); // Apply hover and style
        buttonPanel.add(continueButton);

        // Add components to the overlay
        overlay.add(title);
        overlay.add(Box.createRigidArea(new Dimension(0, 30)));
        overlay.add(desc);
        overlay.add(Box.createVerticalGlue()); // Pushes the button down
        overlay.add(buttonPanel);

        background.add(overlay, BorderLayout.CENTER);
        add(background);

        continueButton.addActionListener(e -> {
    dispose(); // Closes the MainPage window
    new MainMenu(); // Opens the 3-tab login/register window
});

        setVisible(true);
    }

    // Method to style the button
    private void setButtonStyle(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(33, 150, 243)); // Blue
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
                button.setForeground(Color.BLACK);
            }
        });
    }

    // Custom background panel class
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String fileName) {
            try {
                // Load image from img folder (relative to the project directory)
                File imgFile = new File("img/Rankala1.jpeg"); // Path relative to the root of the project
                if (imgFile.exists()) {
                    backgroundImage = new ImageIcon(imgFile.getAbsolutePath()).getImage();
                } else {
                    System.err.println("Image not found: " + fileName);
                    // Use fallback if image not found
                    backgroundImage = new ImageIcon("C:/Users/ASUS/Desktop/Rankala1.jpeg").getImage();
                }
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainPage::new);
    }
}
