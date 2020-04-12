package main.java.ser322;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {

    public static void query1 (Connection connection) throws SQLException {

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
