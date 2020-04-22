package main.java.ser322;

import static main.java.ser322.DebugMode.debug;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.Dimension;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class RoomTypeScroller {
    JTable table;
    DefaultTableModel model;
    public JScrollPane scrollPane;
    private static final Object[] columns = new Object[] {"ROOMID", "ROOMTYPE", "MAXOCCUPANCY", "ISSMOKING"};
    private static final int ROOMID_INDEX = 0;
    private static final int ROOMTYPE_INDEX = 1;
    private static final int MAXOCCUPANCY_INDEX = 2;
    private static final int ISSMOKING_INDEX = 3;


    /**
     * Query ROOMTYPE table and return 2d array of roomtype objects
     * @param query sql query
     * @return [rows]["ROOMID", "ROOMTYPE", "MAXOCCUPANCY", "ISSMOKING"]
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
                row.add(0, resultSet.getInt("ROOMID"));
                debug("ROOMID: " + row.get(0));
                row.add(1, resultSet.getString("ROOMTYPE"));
                row.add(2, resultSet.getString("MAXOCCUPANCY"));
                row.add(3, resultSet.getString("ISSMOKING"));
                dataset.add(row);
                row = new ArrayList<>();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Object[][] array = new Object[dataset.size()][];
        debug("Data set size: " + dataset.size());
        for (int i = 0; i < dataset.size(); i++) {
            ArrayList<Object> row = dataset.get(i);
            array[i] = row.toArray(new Object[0]);
        }
        return array;
    }

    RoomTypeScroller(String query) {
        model = new DefaultTableModel(getData(query), columns){
            private static final long serialVersionUID = 1L;

            @Override
            public Class<?> getColumnClass(int index){
                switch(getColumnName(index)) {
                    case "ROOMID":
                    case "MAXOCCUPANCY":
                    case "ISSMOKING":
                        return Integer.class;
                    case "ROOMTYPE":
                        return String.class;
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

        // Justify all columns
        DefaultTableCellRenderer left = new DefaultTableCellRenderer();
        left.setHorizontalAlignment(JLabel.LEFT);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(ROOMID_INDEX).setCellRenderer(center);
        table.getColumnModel().getColumn(ROOMTYPE_INDEX).setCellRenderer(center);
        table.getColumnModel().getColumn(MAXOCCUPANCY_INDEX).setCellRenderer(center);
        table.getColumnModel().getColumn(ISSMOKING_INDEX).setCellRenderer(center);

        // Set Column Widths
        table.getColumnModel().getColumn(ROOMID_INDEX).setPreferredWidth(55);
        table.getColumnModel().getColumn(ROOMTYPE_INDEX).setPreferredWidth(200);
        table.getColumnModel().getColumn(MAXOCCUPANCY_INDEX).setPreferredWidth(100);
        table.getColumnModel().getColumn(ISSMOKING_INDEX).setPreferredWidth(100);

        // Don't let the table resize
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Add scroll bar to the table
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0,0, table.getWidth(), table.getHeight());
        scrollPane.setComponentZOrder(scrollPane.getVerticalScrollBar(), 0);
        scrollPane.setComponentZOrder(scrollPane.getViewport(), 1);
    }
}
