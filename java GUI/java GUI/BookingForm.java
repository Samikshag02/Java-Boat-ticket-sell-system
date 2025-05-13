import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class BookingForm extends JFrame {
    public BookingForm(String username) {
        setTitle("Book a Ticket");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full-screen size
        setLocationRelativeTo(null); // Center the frame

        // Panel for the form
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2, 20, 20)); // Adjusted layout for spacing
        panel.setBackground(new Color(230, 240, 250)); // Light blue-gray background for a professional look

        // Date selection (Drop-down with next 7 days)
        JLabel dateLabel = new JLabel("Select Date:");
        dateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JComboBox<String> dateComboBox = new JComboBox<>(getNextWeekDates());
        dateComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(dateLabel);
        panel.add(dateComboBox);

        // Time slot selection (Drop-down with predefined slots)
        JLabel timeLabel = new JLabel("Select Time:");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JComboBox<String> timeComboBox = new JComboBox<>(getTimeSlots());
        timeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(timeLabel);
        panel.add(timeComboBox);

        // Number of Passengers
        JLabel passengerCountLabel = new JLabel("Number of Passengers:");
        passengerCountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField passengerCountField = new JTextField();
        passengerCountField.setText("1"); // Default to 1 passenger
        passengerCountField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(passengerCountLabel);
        panel.add(passengerCountField);

        // Passenger Names Section
        JLabel passengerNamesLabel = new JLabel("Passenger Names:");
        passengerNamesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(passengerNamesLabel);

        // Panel to hold passenger name fields
        JPanel passengerPanel = new JPanel();
        passengerPanel.setLayout(new BoxLayout(passengerPanel, BoxLayout.Y_AXIS));
        passengerPanel.setBackground(new Color(230, 240, 250)); // Match the main panel background
        JTextField passengerNameField = new JTextField(20);
        passengerNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        passengerPanel.add(passengerNameField);
        panel.add(passengerPanel);

        // Button colors and styling
        Color buttonColor = new Color(70, 130, 180); // Steel blue color for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        // Back Button to navigate to Dashboard
        JButton backButton = new JButton("Back");
        backButton.setBackground(buttonColor);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(buttonFont);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            // Close the current window and open the user dashboard
            dispose();
            new UserDashboard(username); // Assuming UserDashboard is a class for the dashboard
        });

        // Submit button to finalize booking
        JButton submitBtn = new JButton("Book Ticket");
        submitBtn.setBackground(buttonColor);
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(buttonFont);
        submitBtn.setFocusPainted(false);
        submitBtn.addActionListener(e -> {
            String selectedDate = (String) dateComboBox.getSelectedItem();
            String selectedTime = (String) timeComboBox.getSelectedItem();

            // Get passenger name input
            String passengerName = passengerNameField.getText().trim();

            if (passengerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the passenger name.");
                return;
            }

            ArrayList<String> passengers = new ArrayList<>();
            passengers.add(passengerName);

            String bookingDetails = String.format("%s;%s;%s;%s", selectedDate, username, selectedTime, String.join(",", passengers));

            // Save the booking (implement writeBooking method in Utils)
            Utils.writeBooking(bookingDetails);

            JOptionPane.showMessageDialog(this, "Booking Successful!");
            dispose();
        });

        // Add components to the panel
        panel.add(submitBtn);
        panel.add(backButton);
        add(panel);

        setVisible(true);
    }

    // Method to get next week's dates
    private String[] getNextWeekDates() {
        LocalDate today = LocalDate.now();
        String[] dates = new String[7];
        for (int i = 0; i < 7; i++) {
            dates[i] = today.plusDays(i).format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        return dates;
    }

    // Method to get time slots
    private String[] getTimeSlots() {
        return new String[]{
                "10:00 AM", "12:00 PM", "02:00 PM", "04:00 PM", "06:00 PM", "08:00 PM"
        };
    }

    public static void main(String[] args) {
        // Test the BookingForm with a sample username
        new BookingForm("sampleUser");
    }
}