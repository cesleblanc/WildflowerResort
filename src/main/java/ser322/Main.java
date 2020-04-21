package main.java.ser322;

import javax.swing.SwingUtilities;
import java.sql.SQLException;
import java.text.ParseException;
public class Main   {
    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static String DB_URL;
    static String USER;
    static String PASS;
    static final String credentialsPath = "credentials.txt";


    public static void main(String[] args) throws SQLException, ParseException {
        Controller.initializeDatabase();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppFrame();
            }
        });
    }
}
