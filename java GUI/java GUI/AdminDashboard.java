import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class AdminDashboard extends JFrame {
    public AdminDashboard(String username) {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of the frame to full screen
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocationRelativeTo(null);

        // Set background color
        getContentPane().setBackground(new Color(100, 150, 255)); // Light blue

        // Title label
        JLabel titleLabel = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);

        // Buttons
        JButton viewAll = new JButton("View All Bookings");
        JButton stats = new JButton("View Statistics");
        JButton export = new JButton("Export Daily Bookings");
        JButton back = new JButton("Back");

        // Smaller button size and font
        Dimension buttonSize = new Dimension(160, 30);
        Font buttonFont = new Font("Arial", Font.PLAIN, 12);

        for (JButton button : new JButton[]{viewAll, stats, export, back}) {
            button.setPreferredSize(buttonSize);
            button.setFont(buttonFont);
        }

        // Action listeners
        viewAll.addActionListener(e -> new BookingViewer());
        stats.addActionListener(e -> {
            // Open the BookingStatistics window when the button is clicked
            new BookingStatistics();
        });
        export.addActionListener(e -> {
            String date = JOptionPane.showInputDialog("Enter date (YYYY-MM-DD):");
            if (date != null && !date.isEmpty()) {
                Utils.exportToCSV(date);
            }
        });
        back.addActionListener(e -> {
            dispose();
            new MainMenu();
        });

        // Panel layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 15));
        panel.add(titleLabel);
        panel.add(viewAll);
        panel.add(stats);
        panel.add(export);
        panel.add(back);
        panel.setBackground(new Color(100, 150, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard("admin"));
    }
}
