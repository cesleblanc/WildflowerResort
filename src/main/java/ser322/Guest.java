package main.java.ser322;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Guest {
    static final String CREATE_GUEST_TABLE_SQL = "sql/create_table_guest.sql";

    public static void createTable(Connection conn) {
        Controller.createTable(conn, CREATE_GUEST_TABLE_SQL);
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
}
