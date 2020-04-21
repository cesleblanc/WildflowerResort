package main.java.ser322;

import java.sql.Connection;

public class CreditCard {

    static final String CREATE_CREDIT_CARD_TABLE_SQL = "sql/create_table_credit_card.sql";

    public static void createTable(Connection conn) {
        Controller.createTable(conn, CREATE_CREDIT_CARD_TABLE_SQL);
    }


}
