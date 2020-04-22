package main.java.ser322;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppFrame {
    final String APPLICATION_NAME = "Windflower Resort Database System";
    JFrame mainWindow;
    JTabbedPane tabbedPane;
    JPanel guestPanel = initializeGuestPanel();
    JPanel reservationPanel = initializeReservationPanel();
    JPanel roomPanel = initializeRoomPanel();
    JPanel amenityPanel = initializeAmenityPanel();
    static GuestScroller gs;
    static ReservationScroller rs;

    AppFrame() {
        mainWindow = new JFrame(APPLICATION_NAME);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(800, 500);
        mainWindow.setVisible(true);
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Guests", guestPanel);
        tabbedPane.addTab("Reservations", reservationPanel);
        tabbedPane.addTab("Rooms", roomPanel);
        tabbedPane.add("Amenities", amenityPanel);
        mainWindow.add(tabbedPane);
    }

    static JPanel initializeGuestPanel() {
        // Add scroll panel
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel panel  = new JPanel(gbl);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        // Add search panel
        JTextField textField = new JTextField();
        textField.setMinimumSize(new Dimension(300, 25));
        textField.setPreferredSize(new Dimension(300, 25));
        JButton submit = new JButton("Submit");
        JPanel searchPanel = new JPanel();
        searchPanel.add(textField, BorderLayout.CENTER);
        searchPanel.add(submit, BorderLayout.EAST);
        panel.add(searchPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 25;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //gbc.gridwidth = 3;
        gs = new GuestScroller("SELECT * FROM GUEST");
        panel.add(gs.scrollPane, gbc);
        // Add action listener to submit button
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                panel.remove(gs.scrollPane);
                gs = new GuestScroller(textField.getText());
                System.out.println(textField.getText());
                gbc.gridx = 0;
                gbc.gridy = 25;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                panel.add(gs.scrollPane, gbc);
                panel.repaint();
                panel.revalidate();
            }
        });

        return panel;
    }

    static JPanel initializeReservationPanel() {

//        JPanel panel  = new JPanel();
//        panel.add(rs.scrollPane, BorderLayout.SOUTH);
//        return panel;

        // Add scroll panel
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel panel  = new JPanel(gbl);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Add search panel
        JTextField textField = new JTextField();
        textField.setMinimumSize(new Dimension(300, 25));
        textField.setPreferredSize(new Dimension(300, 25));
        JButton submit = new JButton("Submit");
        JPanel searchPanel = new JPanel();
        searchPanel.add(textField, BorderLayout.CENTER);
        searchPanel.add(submit, BorderLayout.EAST);
        panel.add(searchPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 25;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        rs = new ReservationScroller("SELECT * FROM RESERVATION");
        panel.add(rs.scrollPane, gbc);

        // Add action listener to submit button
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                panel.remove(rs.scrollPane);
                rs = new ReservationScroller(textField.getText());
                System.out.println(textField.getText());
                gbc.gridx = 0;
                gbc.gridy = 25;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                panel.add(rs.scrollPane, gbc);
                panel.repaint();
                panel.revalidate();
            }
        });
        return panel;
    }

    static JPanel initializeRoomPanel() {
        return new JPanel();
    }

    static JPanel initializeAmenityPanel() {
        return new JPanel();
    }

}
