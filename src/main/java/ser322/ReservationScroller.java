package main.java.ser322;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.Dimension;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class ReservationScroller {
    JTable table;
    DefaultTableModel model;
    public JScrollPane scrollPane;

    private static final Object[] columns = new Object[] {"RESERVATIONID", "CARDNUM", "ROOMID", "PRICE", "STARTDATE", "ENDDATE"};
    private static final int RESERVATIONID_INDEX = 0;
    private static final int CARDNUM_INDEX = 1;
    private static final int ROOMID_INDEX = 2;
    private static final int PRICE_INDEX = 3;
    private static final int STARTDATE_INDEX = 4;
    private static final int ENDDATE_INDEX = 5;

    /**
     * Gets Data Amenity table from SQL request
     * @param query sql parsed into Reservation Table
     * @return array of sorted [rows]["RESERVATIONID", "CARDNUM", "ROOMID", "PRICE", "STARTDATE", "ENDDATE"]
     */
    static Object[][] getData(String query) {
        ArrayList<ArrayList<Object>> dataset = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = Objects.requireNonNull(Controller.getConnection()).prepareStatement(query);
            resultSet = stmt.executeQuery();
            ArrayList<Object> row = new ArrayList<>();
            while (resultSet.next()) {

                row.add(0, resultSet.getInt("RESERVATIONID"));
                System.out.println("RESERVATIONID: " + row.get(0));
                row.add(1, resultSet.getString("CARDNUM"));
                row.add(2, resultSet.getInt("ROOMID"));
                row.add(3, resultSet.getFloat("PRICE"));
                row.add(4, resultSet.getDate("STARTDATE"));
                row.add(5, resultSet.getDate("ENDDATE"));
                dataset.add(row);
                row = new ArrayList<>();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Object[][] array = new Object[dataset.size()][];
        System.out.println("Data set size: " + dataset.size());
        for (int i = 0; i < dataset.size(); i++) {
            ArrayList<Object> row = dataset.get(i);
            array[i] = row.toArray(new Object[0]);
        }
        return array;
    }

    /**
     * Query and create table
     * @param query sql query
     */
    ReservationScroller(String query) {
        model = new DefaultTableModel(getData(query), columns){
            private static final long serialVersionUID = 1L;

            @Override
            public Class<?> getColumnClass(int index){
                switch(getColumnName(index)) {
                    case "RESERVATIONID":
                        return Integer.class;
                    case "CARDNUM":
                        return String.class;
                    case "ROOMID":
                        return Integer.class;
                    case "PRICE":
                        return Float.class;
                    case "STARTDATE":
                        return Date.class;
                    case "ENDDATE":
                        return Date.class;
                } return super.getColumnClass(index);

            }
        };

        table = new JTable(model){
            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(table.getWidth(), 360);
            }
        };

        // Set default cell values
        TableCellRenderer rendererFromHeader = table.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setVerticalTextPosition(JLabel.CENTER);
        table.setRowHeight(25);

        // Set column widths
        table.getColumnModel().getColumn(RESERVATIONID_INDEX).setPreferredWidth(100);
        table.getColumnModel().getColumn(CARDNUM_INDEX).setPreferredWidth(150);
        table.getColumnModel().getColumn(ROOMID_INDEX).setPreferredWidth(75);
        table.getColumnModel().getColumn(PRICE_INDEX).setPreferredWidth(75);
        table.getColumnModel().getColumn(STARTDATE_INDEX).setPreferredWidth(125);
        table.getColumnModel().getColumn(ENDDATE_INDEX).setPreferredWidth(125);


        // Set cell justifications
        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(JLabel.RIGHT);
        DefaultTableCellRenderer left = new DefaultTableCellRenderer();
        left.setHorizontalAlignment(JLabel.LEFT);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(RESERVATIONID_INDEX).setCellRenderer(center);
        table.getColumnModel().getColumn(CARDNUM_INDEX).setCellRenderer(center);
        table.getColumnModel().getColumn(ROOMID_INDEX).setCellRenderer(center);
        table.getColumnModel().getColumn(PRICE_INDEX).setCellRenderer(center);
        table.getColumnModel().getColumn(STARTDATE_INDEX).setCellRenderer(center);
        table.getColumnModel().getColumn(ENDDATE_INDEX).setCellRenderer(center);

        // Don't let the table resize
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Add scroll bar to the table
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0,0, table.getWidth(), table.getHeight());
        scrollPane.setComponentZOrder(scrollPane.getVerticalScrollBar(), 0);
        scrollPane.setComponentZOrder(scrollPane.getViewport(), 1);
    }
}
