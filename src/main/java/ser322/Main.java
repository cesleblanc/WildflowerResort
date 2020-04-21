package main.java.ser322;

import java.sql.SQLException;
<<<<<<< Updated upstream
import java.sql.Statement;
=======
import java.text.ParseException;
>>>>>>> Stashed changes

public class Main   {
<<<<<<< Updated upstream
    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static String DB_URL;
    static String USER;
    static String PASS;
    static final String credentialsPath = "credentials.txt";

    public static void main(String[] args) {
        initializeDatabase();
    }
=======
    public static void main(String[] args) throws SQLException, ParseException {
        Controller.initializeDatabase();
//        Controller controller = new Controller();
//        Guest.createGuestTable(getConnection());
//        Guest.selectGuestInfo(getConnection());
//        Reservation.selectReservationInfo(getConnection());
//        controller.selectRoomTypes(getConnection());
//        Guest.insertGuest(getConnection(), 837, "Porter", "Robinson", "prob@gmail.com", "8467387266");
//        controller.insertCreditCard(getConnection(), 837, "7464746474647464", 756, 10, 3, "Porter", "Robinson");
//        Reservation.createReservation(getConnection(), 609, "7464746474647464", 2, 437.67f, "2020/10/1", "2020/12/13");
>>>>>>> Stashed changes

        AppFrame main = new AppFrame();

<<<<<<< Updated upstream
    /**
     * The initializeDatabase method initializes the WINDFLOWER schema.
     * If the schema doesn't exist, it is created.
     * Otherwise, nothing occurs.
     */
    public static void initializeDatabase(){
        login();
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(DRIVER);
            System.out.println("Attempting to connect...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected! Gathering data...");
            stmt = conn.createStatement();
            String sql = "CREATE SCHEMA IF NOT EXISTS WINDFLOWER";
            stmt.executeUpdate(sql);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
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
=======
>>>>>>> Stashed changes
    }
}
