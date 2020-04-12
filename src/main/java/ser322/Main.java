package main.java.ser322;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

public class Main   {
    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static String DB_URL;
    static String USER;
    static String PASS;
    static final String credentialsPath = "credentials.txt";
    static Connection conn;

    public static void main(String[] args) throws SQLException, ParseException {
        initializeDatabase();
        Controller controller = new Controller();
        controller.selectGuestInfo(conn);
        controller.selectReservationInfo(conn);
        controller.selectRoomTypes(conn);
        controller.insertGuest(conn, 837, "Porter", "Robinson", "prob@gmail.com", "8467387266");
        controller.insertCreditCard(conn, 837, "7464746474647464", 756, 10, 3, "Porter", "Robinson");
        controller.createReservation(conn, 609, "7464746474647464", 2, 437.67f, "2020/10/1", "2020/12/13");
        conn.close();
    }

    /**
     * The login method reads the credentials.txt file to connect to the main database.
     * The credentials.txt file is located within the main project directory.
     * The first line in the file is the database's url.
     * The second line in the file is the user's username.
     * The third line in the file is the user's password.
     */
    public static void login(){
        File file = new File(credentialsPath);
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.out.println("Could not find " + file.getAbsolutePath());
            e.printStackTrace();
        }
        try {
            assert fr != null;
            BufferedReader br = new BufferedReader(fr);
            DB_URL = br.readLine();
            USER = br.readLine();
            PASS = br.readLine();
            System.out.println("DATABASE URL: " + DB_URL);
            System.out.println("USERNAME: " + USER);
            System.out.println("PASSWORD: " + PASS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The initializeDatabase method initializes the WILDFLOWER schema.
     * If the schema doesn't exist, it is created.
     * Otherwise, nothing occurs.
     */
    public static void initializeDatabase(){
        login();
        conn = null;
        Statement stmt = null;
        try{
            Class.forName(DRIVER);
            System.out.println("Attempting to connect...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected! Gathering data...");
            stmt = conn.createStatement();
            String sql = "CREATE SCHEMA IF NOT EXISTS WILDFLOWER";
            stmt.executeUpdate(sql);
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    //conn.close();
                System.out.println("Successfully connected to Windflower Resort Database System.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
