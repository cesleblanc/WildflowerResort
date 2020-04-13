package main.java.ser322;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The following class is used as a controller to the current resort database.
 * Users may select guests, registration, and room information as you would
 * expect from a hotel booking application.
 * @author Team 13, SER322 (Cesar, Emily, Hannah, Brandon)
 */
public class Controller {

    /**
     * Pulls out all guests' information from Wildflower Resort Database, including:
     *  Guest ID, First Name, Last Name, Email, and Phone Number
     * @param connection Connection to SQL server.
     * @throws SQLException No SQL server found/error.
     */
    public static void selectGuestInfo(Connection connection) throws SQLException {

        Statement s = connection.createStatement(); //Creating statement
        ResultSet r = s.executeQuery(
                "SELECT GUESTID, FIRSTNAME, LASTNAME, EMAIL, PHONENUM" +
                        " FROM GUEST"); //Result from statement

        //Printing resulting table to console
        System.out.printf("%s\n%s\n%s\n",
                " _____________________________________________________________________________________________________________",
                "|ID                   |First Name           |Last Name            |Email                |Phone Number         |",
                "|---------------------|---------------------|---------------------|---------------------|---------------------|");
        while(r.next()) {
            System.out.printf("| %-20s| %-20s| %-20s| %-20s| %-20s|\n",
                    r.getString(1),
                    r.getString(2),
                    r.getString(3),
                    r.getString(4),
                    r.getString(5)
            );
            System.out.println("|---------------------|---------------------|---------------------|---------------------|---------------------|");
        }
    }

    /**
     * Pulls all reservation information from WRD, including:
     * Guest ID, First Name, Reservation ID, Credit Card Information, and Room ID
     * @param connection Connection to SQL server.
     * @throws SQLException No SQL server found/error.
     */
    public static void selectReservationInfo (Connection connection) throws SQLException {

        Statement s = connection.createStatement(); //Creating statement
        ResultSet r = s.executeQuery(
                "SELECT GUEST.GUESTID, GUEST.FIRSTNAME, R.RESERVATIONID, R.CARDNUM, R.ROOMID " +
                        "FROM GUEST, RESERVATION AS R, CREDIT_CARD " +
                        "WHERE CREDIT_CARD.GUESTID = GUEST.GUESTID " +
                        "AND CREDIT_CARD.CARDNUM = R.CARDNUM "); //Result from statement

        //Printing resulting table to console
        System.out.printf("%s\n%s\n%s\n",
                " _____________________________________________________________________________________________________________",
                "|ID                   |First Name           |Reservation ID       |Credit Card          |Room Number          |",
                "|---------------------|---------------------|---------------------|---------------------|---------------------|");
        while(r.next()) {
            System.out.printf("| %-20s| %-20s| %-20s| %-20s| %-20s|\n",
                    r.getString(1),
                    r.getString(2),
                    r.getString(3),
                    r.getString(4),
                    r.getString(5)
            );
            System.out.println("|---------------------|---------------------|---------------------|---------------------|---------------------|");
        }
    }

    /**
     * Pulls the types of rooms available at the Wildflower Resort, including:
     * Room ID, Room Type, Max Occupancy, and Smoking (Y/N)
     * @param connection Connection to SQL server.
     * @throws SQLException No SQL server found/error.
     */
    public static void selectRoomTypes (Connection connection) throws SQLException {

        Statement s = connection.createStatement(); //Creating statement
        ResultSet r = s.executeQuery(
                "SELECT ROOMID, ROOMTYPE, MAXOCCUPANCY, ISSMOKING FROM ROOMTYPE "); //Result from statement

        //Printing resulting table to console
        System.out.printf("%s\n%s\n%s\n",
                " _______________________________________________________________________________________",
                "|ROOM ID              |ROOMTYPE             |MAXOCCUPANCY         |SMOKING?             |",
                "|---------------------|---------------------|---------------------|---------------------|");
        while(r.next()) {

            String smoking = "No";
            if (r.getString(4).equals("1")) {
                smoking = "Yes";
            }

            System.out.printf("| %-20s| %-20s| %-20s| %-20s|\n",
                    r.getString(1),
                    r.getString(2),
                    r.getString(3),
                    smoking
            );
            System.out.println("|---------------------|---------------------|---------------------|---------------------|");
        }
    }

    /**
     * Inserts a new guest into the Wildflower Database
     * @param connection Connection to database.
     * @param gid guest id
     * @param fname first name
     * @param lname last name
     * @param email email
     * @param pnum phone number
     * @throws SQLException
     */
    public static void insertGuest (Connection connection, int gid, String fname,
                                    String lname, String email, String pnum) throws SQLException {

        PreparedStatement p = connection.prepareStatement( //Creating statement
                "INSERT INTO GUEST (GUESTID, FIRSTNAME, LASTNAME, EMAIL, PHONENUM) VALUES (?, ?, ?, ?, ?)");

        p.setInt(1, gid);
        p.setString(2, fname);
        p.setString(3, lname);
        p.setString(4, email);
        p.setString(5, pnum);

        p.executeUpdate();
        System.out.println("SUCCESS");
    }

    /**
     * Inserts credit card information. You must first have a guest created to do this.
     * @param connection Connection to database.
     * @param gid guest id
     * @param cnum credit card number
     * @param cvv cvv
     * @param expm expiration month
     * @param expd expiration day
     * @param fname first name
     * @param lname last name
     * @throws SQLException Check for connection/error
     */
    public static void insertCreditCard (Connection connection, int gid, String cnum,
                                         int cvv, int expm, int expd, String fname, String lname) throws SQLException {

        PreparedStatement p = connection.prepareStatement( //Creating statement
                "INSERT INTO CREDIT_CARD (GUESTID, CARDNUM, CVV, EXPM, EXPD, OWNERFIRSTNAME, OWNERLASTNAME) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)");

        p.setInt(1, gid);
        p.setString(2, cnum);
        p.setInt(3, cvv);
        p.setInt(4, expm);
        p.setInt(5, expd);
        p.setString(6, fname);
        p.setString(7, lname);

        p.executeUpdate();
        System.out.println("SUCCESS");
    }

    /**
     * Creates a new reservation in the Wildflower Resort Database
     * @param connection Connection to database
     * @param resid reservation ID
     * @param cnum credit card number
     * @param roomid room id (The actual room number is used)
     * @param price price of stay (total)
     * @param sdate first day
     * @param edate last day
     * @throws SQLException Checks for connectivity.
     * @throws ParseException Checks for correct parsing.
     */
    public static void createReservation (Connection connection, int resid, String cnum,
                                          int roomid, float price, String sdate, String edate)
            throws SQLException, ParseException {

        PreparedStatement p = connection.prepareStatement( //Creating statement
                "INSERT INTO RESERVATION (RESERVATIONID, CARDNUM, ROOMID, PRICE, STARTDATE, ENDDATE) VALUES (?, ?, ?, ?, ?, ?)");

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/mm/dd");
        java.util.Date date = sdf1.parse(sdate);
        java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/mm/dd");
        java.util.Date date2 = sdf2.parse(sdate);
        java.sql.Date sqlEndDate = new java.sql.Date(date2.getTime());

        p.setInt(1, resid);
        p.setString(2, cnum);
        p.setInt(3, roomid);
        p.setFloat(4, price);
        p.setDate(5, sqlStartDate);
        p.setDate(6, sqlEndDate);

        p.executeUpdate();
        System.out.println("SUCCESS");
    }
}
