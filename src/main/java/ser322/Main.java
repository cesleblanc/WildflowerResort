package main.java.ser322;

import jdk.swing.interop.SwingInterOpUtils;

import javax.swing.SwingUtilities;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.sql.SQLException;
import java.text.ParseException;

import static main.java.ser322.DebugMode.debug;

public class Main   {
    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static String DB_URL;
    static String USER;
    static String PASS;
    static final String credentialsPath = "credentials.txt";


    public static void main(String[] args) throws SQLException, ParseException {

        if(args.length == 3) {
            DB_URL = args[0];
            USER = args[1];
            PASS = args[2];

            debug("Custom credentials input:");
            debug("\tURL:  " + DB_URL);
            debug("\tUSER: " + USER);
            debug("\tPASS: " + PASS + "\n");

            Controller.initializeDatabase(DB_URL, USER, PASS);

        } else {
            debug("Using default credentials");

            // These defaults will be overwritten in the Controller.login method
            Controller.initializeDatabase("default", "default", "default");
        }


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppFrame();
            }
        });
    }
}
