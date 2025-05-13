import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookingViewer extends JFrame {

    public BookingViewer() {
        setTitle("Bookings");
        setSize(800, 500); // Increased size for better readability
        setLocationRelativeTo(null);

        // Create a panel to hold the filter and text area
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a text field for user input (filter)
        JTextField userFilterField = new JTextField();
        userFilterField.setFont(new Font("Arial", Font.PLAIN, 14));
        userFilterField.setToolTipText("Enter username to filter");

        // Create a text area to display the bookings
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false); // Make the text area non-editable
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setMargin(new Insets(10, 10, 10, 10));

        // Scroll pane for the text area
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel to hold filter field
        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Filter by User: "));
        filterPanel.add(userFilterField);
        panel.add(filterPanel, BorderLayout.NORTH);

        // Add the panel to the frame
        add(panel);

        // Fetch bookings using the Utils class (assumed to be defined elsewhere)
        List<String> bookings = Utils.readBookings();

        // Sort bookings based on a custom comparator (sorting by date)
        Collections.sort(bookings, new Comparator<String>() {
            @Override
            public int compare(String booking1, String booking2) {
                String[] parts1 = booking1.split(";");
                String[] parts2 = booking2.split(";");
                return parts1[0].compareTo(parts2[0]); // Assuming date is the first part
            }
        });

        // Method to update the displayed bookings
        Runnable updateBookings = new Runnable() {
            @Override
            public void run() {
                textArea.setText(""); // Clear the text area

                String userFilter = userFilterField.getText().trim();

                // Iterate through the bookings and filter based on userFilter
                for (String booking : bookings) {
                    String[] parts = booking.split(";");

                    if (parts.length >= 4) {
                        if (userFilter.isEmpty() || parts[1].equalsIgnoreCase(userFilter)) {
                            // Append formatted booking details to the text area
                            textArea.append("Date: " + parts[0] + ", User: " + parts[1] + ", Time: " + parts[2] + ", Passengers: " + parts[3] + "\n");
                        }
                    } else {
                        System.out.println("Invalid booking data: " + booking);
                    }
                }
            }
        };

        // Initial load of bookings
        updateBookings.run();

        // Add listener to update bookings when the filter changes
        userFilterField.addCaretListener(e -> updateBookings.run());

        // Back button to navigate to Admin Dashboard
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));

        backButton.addActionListener(e -> {
            dispose(); // Close the current frame
            new AdminDashboard("admin"); // Go back to Admin Dashboard
        });

        // Panel layout for the back button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Make the frame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Sample call to the constructor (no filter passed, users can filter dynamically)
        SwingUtilities.invokeLater(() -> new BookingViewer());
    }
}
