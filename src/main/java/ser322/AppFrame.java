package main.java.ser322;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class AppFrame {
    final String APPLICATION_NAME = "Windflower Resort Database System";
    JFrame mainWindow;
    JTabbedPane tabbedPane;
    JPanel guestPanel = initializeGuestPanel();
    JPanel reservationPanel = initializeReservationPanel();
    JPanel roomPanel = initializeRoomPanel();
    JPanel amenityPanel = initializeAmenityPanel();

    AppFrame() {
        mainWindow = new JFrame(APPLICATION_NAME);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(400, 300);
        mainWindow.setVisible(true);
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Guests", guestPanel);
        tabbedPane.addTab("Reservations", reservationPanel);
        tabbedPane.addTab("Rooms", roomPanel);
        tabbedPane.add("Amenities", amenityPanel);
        mainWindow.add(tabbedPane);
    }

    static JPanel initializeGuestPanel() {
        return new JPanel();
    }

    static JPanel initializeReservationPanel() {
        return new JPanel();
    }

    static JPanel initializeRoomPanel() {
        return new JPanel();
    }

    static JPanel initializeAmenityPanel() {
        return new JPanel();
    }

}
