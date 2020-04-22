package main.java.ser322;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Reservation {

static final String CREATE_TABLE_RESERVATION_SQL = "sql/create_table_reservation.sql";

    public static void createTable(Connection conn) {
        Controller.createTable(conn, CREATE_TABLE_RESERVATION_SQL);
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
}
