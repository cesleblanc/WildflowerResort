package main.java.ser322;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import static main.java.ser322.DebugMode.debug;


/**
 * The following class is used as a controller to the current resort database.
 * Users may select guests, registration, and room information as you would
 * expect from a hotel booking application.
 * @author Team 13, SER322 (Cesar, Emily, Hannah, Brandon)
 */
public class Controller {
    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static String DB_URL;
    static String USER;
    static String PASS;
    static final String credentialsPath = "credentials.txt";

    /**
     * Creates a table in the database by executing SQL found in the sql folder.
     * @param conn connection to the database
     * @param createTableReservationSql path to the sql file
     */
    static void createTable(Connection conn, String createTableReservationSql) {
        Statement stmt = null;
        StringBuilder sql = new StringBuilder();
        try {
            stmt = conn.createStatement();
            File file = new File(createTableReservationSql);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while((line = br.readLine()) != null){
                sql.append(line);
            }
            br.close();
            stmt.executeUpdate(sql.toString());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Helper method for initializeDatabase()
     * It creates all tables in the database provided they
     * do not already exist by executing code found in the
     * sql folder.
     */
    static void createAllTables(){
        Guest.createTable(getConnection());
        RoomType.createTable(getConnection());
        Amenity.createTable(getConnection());
        CreditCard.createTable(getConnection());
        Reservation.createTable(getConnection());

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
            debug("DATABASE URL: " + DB_URL);
            debug("USERNAME: " + USER);
            debug("PASSWORD: " + PASS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The initializeDatabase method initializes the WILDFLOWER schema
     * and its tables. If the schema and tables don't exist, they are created.
     * Otherwise, nothing occurs.
     */
    public static void initializeDatabase(String url, String name, String pass){

        if(url.equalsIgnoreCase("default")) {
            login();
        } else {
            DB_URL = url;
            USER = name;
            PASS = pass;
        }
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(DRIVER);
            System.out.println("Attempting to connect...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected! Gathering data...");
            stmt = conn.createStatement();
            String sql = "CREATE SCHEMA IF NOT EXISTS WILDFLOWER";
            stmt.executeUpdate(sql);
            createAllTables();
            insertDataValues();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
                    System.out.println("Successfully connected to Windflower Resort Database System.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The getConnection method gets a connection to the database
     * Every time getConnection is used, there should be matching
     * code to close the connection.
     * @return Connection to the database
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e){
            System.out.println("Unable to get a connection to the database.");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Insert data from sql/insertions.sql into the database
     */
    public static void insertDataValues() {
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = getConnection();
            if (conn == null){
                throw new Exception(new IOException());
            }
            stmt = conn.createStatement();
            File file = new File("sql/insertions.sql");
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while((line = br.readLine()) != null){
                try {
                    stmt.execute(line);
                } catch (SQLIntegrityConstraintViolationException e){
                    // e.printStackTrace();
                    // Assuming all integrity constraints are met,
                    // no side effects should occur
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
