package main.java.ser322;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RoomType {
    static final String CREATE_ROOM_TYPE_TABLE_SQL = "sql/create_table_room_type.sql";

    public static void createTable(Connection conn) {
        Controller.createTable(conn, CREATE_ROOM_TYPE_TABLE_SQL);
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
}
