package main.java.ser322;

import jdk.swing.interop.SwingInterOpUtils;

import javax.swing.SwingUtilities;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.sql.SQLException;
import java.text.ParseException;

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

            System.out.println("Custom credentials input:");
            System.out.println("\tURL:  " + DB_URL);
            System.out.println("\tUSER: " + USER);
            System.out.println("\tPASS: " + PASS + "\n");

            Controller.initializeDatabase(DB_URL, USER, PASS);

        } else {
            System.out.println("Using default credentials");

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
