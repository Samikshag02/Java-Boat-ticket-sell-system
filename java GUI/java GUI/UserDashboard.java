import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class UserDashboard extends JFrame {
    String username;

    public UserDashboard(String username) {
        this.username = username;
        setTitle("User Dashboard - " + username);

        // Maximize the window to full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the frame on the screen

        // Set background color (light pastel blue)
        getContentPane().setBackground(new Color(220, 235, 247));

        // Create buttons with enhanced style
        JButton bookBtn = createStyledButton("Book Ticket");
        JButton viewBtn = createStyledButton("View My Bookings");
        JButton cancelBtn = createStyledButton("Cancel Booking");

        // Button actions
        bookBtn.addActionListener(e -> new BookingForm(username));
        viewBtn.addActionListener(e -> new BookingViewer());
        cancelBtn.addActionListener(e -> new BookingCanceller(username));

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 15, 15));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        buttonPanel.add(bookBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(cancelBtn);

        add(buttonPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(Color.WHITE); // Changed to white
        button.setForeground(Color.BLACK); // Changed to black
        button.setFocusPainted(false);

        Icon icon = null;
        if (text.equals("Book Ticket")) {
            icon = UIManager.getIcon("OptionPane.informationIcon");
        } else if (text.equals("View My Bookings")) {
            icon = UIManager.getIcon("OptionPane.questionIcon");
        } else if (text.equals("Cancel Booking")) {
            icon = UIManager.getIcon("OptionPane.warningIcon");
        }

        if (icon != null) {
            button.setIcon(icon);
        }

        button.setPreferredSize(new Dimension(180, 40));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // Hover effect: light grey on hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(230, 230, 230)); // light grey hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE); // back to white
            }
        });

        return button;
    }

    public static void main(String[] args) {
        new UserDashboard("sampleUser");
    }
}
