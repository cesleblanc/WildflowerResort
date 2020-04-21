package main.java.ser322;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
    private static final Object[] columns = new Object[] {"ID", "Card #", "Room ID", "Price", "Start Date", "End Date"};
    private static final int ID_INDEX = 0;
    private static final int CARD_INDEX = 1;

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

    ReservationScroller(String query) {
        model = new DefaultTableModel(getData(query), columns){
            private static final long serialVersionUID = 1L;

            @Override
            public Class<?> getColumnClass(int index){
                switch(getColumnName(index)) {
                    case "ID":
                        return Integer.class;
                    case "Card #":
                        return String.class;
                    case "Room ID":
                        return Integer.class;
                    case "Price":
                        return Float.class;
                    case "Start Date":
                        return Date.class;
                        case "End Date":
                            return Date.class;
                } return super.getColumnClass(index);

            }
        };

        table = new JTable(model){
            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(table.getWidth(), 125);
            }
        };
        TableCellRenderer rendererFromHeader = table.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setVerticalTextPosition(JLabel.CENTER);
        table.setRowHeight(25);

        // Make ID column less wide
        table.getColumnModel().getColumn(ID_INDEX).setPreferredWidth(50);

        // Make card column wider
         table.getColumnModel().getColumn(CARD_INDEX).setPreferredWidth(150);

        // Don't let the table resize
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Add scroll bar to the table
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0,0, table.getWidth(), table.getHeight());
        scrollPane.setComponentZOrder(scrollPane.getVerticalScrollBar(), 0);
        scrollPane.setComponentZOrder(scrollPane.getViewport(), 1);
    }
}
