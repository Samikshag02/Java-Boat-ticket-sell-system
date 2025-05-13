import javax.swing.*;
import java.awt.*;
import java.util.*;

public class BookingStatistics extends JFrame {

    public BookingStatistics() {
        // Set a larger frame size
        setTitle("Booking Statistics");
        setSize(800, 500); // Increased size for better readability
        setLocationRelativeTo(null);

        // Text area to show statistics
        JTextArea statsArea = new JTextArea();
        statsArea.setEditable(false);
        statsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        statsArea.setMargin(new Insets(10, 10, 10, 10));

        Map<String, Integer> userCounts = new HashMap<>();
        Map<String, Integer> slotCounts = new HashMap<>();
        int totalBookings = 0;
        int totalPassengers = 0;

        try {
            // Read and process bookings data
            for (String booking : Utils.readBookings()) {
                String[] parts = booking.split(";");
                if (parts.length < 4) {
                    continue; // Skip malformed booking entries
                }

                String user = parts[1];
                String time = parts[2];
                int passengers = parts[3].split(",").length;

                userCounts.put(user, userCounts.getOrDefault(user, 0) + 1);
                slotCounts.put(time, slotCounts.getOrDefault(time, 0) + 1);
                totalBookings++;
                totalPassengers += passengers;
            }
        } catch (Exception e) {
            statsArea.append("Error reading bookings data.\n");
            e.printStackTrace();
        }

        // Append total statistics
        statsArea.append("Total Bookings: " + totalBookings + "\n");
        statsArea.append("Total Passengers: " + totalPassengers + "\n\n");

        // Append user statistics
        statsArea.append("Bookings per User:\n");
        userCounts.forEach((user, count) -> statsArea.append(user + ": " + count + " bookings\n"));

        statsArea.append("\nBookings per Time Slot:\n");
        slotCounts.forEach((slot, count) -> statsArea.append(slot + ": " + count + " bookings\n"));

        JScrollPane scrollPane = new JScrollPane(statsArea);
        scrollPane.setPreferredSize(new Dimension(750, 350)); // Make the scrollable area smaller
        add(scrollPane, BorderLayout.CENTER);

        // Add a back button to navigate to Admin Dashboard
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));

        // Action for back button
        backButton.addActionListener(e -> {
            dispose(); // Close the current frame
            new AdminDashboard("admin"); // Create a new Admin Dashboard
        });

        // Layout for back button at the bottom of the window
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Make the frame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookingStatistics());
    }
}
