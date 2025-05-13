import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

class BookingCanceller extends JFrame {
    public BookingCanceller(String username) {
        setTitle("Cancel Booking");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full-screen size
        setLocationRelativeTo(null); // Center the frame

        // Panel for the form
        JPanel panel = new JPanel(new GridLayout(3, 2, 20, 20)); // Adjusted layout for spacing
        panel.setBackground(new Color(240, 248, 255)); // Light blue background for a professional look

        // Input field for booking date
        JLabel dateLabel = new JLabel("Booking Date to Cancel:");
        dateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField dateField = new JTextField();
        dateField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Cancel button
        JButton cancelBtn = new JButton("Cancel Booking");
cancelBtn.setBackground(new Color(70, 130, 180)); // Steel blue color
cancelBtn.setForeground(Color.WHITE);
cancelBtn.setFont(new Font("Arial", Font.BOLD, 16));
cancelBtn.setFocusPainted(false);
cancelBtn.setPreferredSize(new Dimension(150, 40)); // Set smaller size for the button

cancelBtn.addActionListener(e -> {
    String targetDate = dateField.getText().trim();
    boolean found = false;

    java.util.List<String> bookings = Utils.readBookings();
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("bookings.txt"))) {
        for (String line : bookings) {
            if (!line.startsWith(targetDate + ";" + username)) {
                writer.write(line);
                writer.newLine();
            } else {
                found = true;
            }
        }

        if (found) {
            JOptionPane.showMessageDialog(this, "Booking cancelled.");
        } else {
            JOptionPane.showMessageDialog(this, "No matching booking found.");
        }

        dispose();
    } catch (IOException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error occurred while cancelling booking.");
    }
});

// Back button
JButton backButton = new JButton("Back");
backButton.setBackground(new Color(70, 130, 180)); // Steel blue color
backButton.setForeground(Color.WHITE);
backButton.setFont(new Font("Arial", Font.BOLD, 16));
backButton.setFocusPainted(false);
backButton.setPreferredSize(new Dimension(150, 40)); // Set smaller size for the button

backButton.addActionListener(e -> {
    dispose();
    new UserDashboard(username); // Assuming UserDashboard is a class for the dashboard
});

// Add components to the panel
panel.add(dateLabel);
panel.add(dateField);
panel.add(cancelBtn);
panel.add(backButton);

        add(panel);
        setVisible(true);
    }
}