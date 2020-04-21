package main.java.ser322;

import java.sql.Connection;

public class Amenity {
    static final String CREATE_AMENITY_TABLE_SQL = "sql/create_table_amenity.sql";
    public static void createTable(Connection conn) {
        Controller.createTable(conn, CREATE_AMENITY_TABLE_SQL);
    }
}
