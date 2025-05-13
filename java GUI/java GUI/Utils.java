import java.io.*;
import javax.swing.JOptionPane;

import java.util.*;

class Utils {
    static Map<String, String> readUsers() {
        Map<String, String> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) map.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            System.out.println("Error reading users.txt");
        }
        return map;
    }

    static void writeBooking(String bookingLine) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bookings.txt", true))) {
            writer.write(bookingLine);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static java.util.List<String> readBookings() {
        java.util.List<String> bookings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("bookings.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bookings.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    static void exportToCSV(String date) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("booking_export_" + date + ".csv"))) {
            writer.write("Username,Date,Time,Passengers\n");
            for (String line : readBookings()) {
                if (line.startsWith(date)) {
                    writer.write(line + "\n");
                }
            }
            JOptionPane.showMessageDialog(null, "Exported to booking_export_" + date + ".csv");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to export CSV.");
        }
    }
}
