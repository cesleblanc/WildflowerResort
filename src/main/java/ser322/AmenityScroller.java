package main.java.ser322;

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

/**
 * Amenity Table
 */
public class AmenityScroller {
    JTable table;
    DefaultTableModel model;
    public JScrollPane scrollPane;
    private static final Object[] columns = new Object[] {"AMENITYID", "TYPE", "HOURS"};
    private static final int AMENITYID_INDEX = 0;
    private static final int TYPE_INDEX = 1;
    private static final int HOURS_INDEX = 2;

    /**
     * Gets Data Amenity table from SQL request
     * @param query sql parsed into Amenity Table
     * @return arraylist of sorted [rows]["AMENITYID", "TYPE", "HOURS"]
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
                row.add(0, resultSet.getInt("AMENITYID"));
                System.out.println("AMENITYID: " + row.get(0));
                row.add(1, resultSet.getString("TYPE"));
                row.add(2, resultSet.getString("HOURS"));
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
    AmenityScroller(String query) {
        model = new DefaultTableModel(getData(query), columns){
            private static final long serialVersionUID = 1L;

            @Override
            public Class<?> getColumnClass(int index){
                switch(getColumnName(index)) {
                    case "AMENITYID":
                    case "TYPE":
                    case "HOURS":
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

        table.getColumnModel().getColumn(AMENITYID_INDEX).setCellRenderer(center);
        table.getColumnModel().getColumn(TYPE_INDEX).setCellRenderer(center);
        table.getColumnModel().getColumn(HOURS_INDEX).setCellRenderer(center);

        // Set Column Widths
        table.getColumnModel().getColumn(AMENITYID_INDEX).setPreferredWidth(100);
        table.getColumnModel().getColumn(TYPE_INDEX).setPreferredWidth(200);
        table.getColumnModel().getColumn(HOURS_INDEX).setPreferredWidth(100);

        // Don't let the table resize
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Add scroll bar to the table
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0,0, table.getWidth(), table.getHeight());
        scrollPane.setComponentZOrder(scrollPane.getVerticalScrollBar(), 0);
        scrollPane.setComponentZOrder(scrollPane.getViewport(), 1);
    }
}
