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

public class GuestScroller {
    JTable table;
    DefaultTableModel model;
    public JScrollPane scrollPane;
    private static final Object[] columns = new Object[] {"GUESTID", "FIRSTNAME", "LASTNAME", "EMAIL", "PHONENUM"};
    private static final int EMAIL_INDEX = 3;
    private static final int ID_INDEX = 0;
    private static final int PHONE_INDEX = 4;

    /**
     * Query GUEST table and create array
     * @param query
     * @return [rows]["GUESTID", "FIRSTNAME", "LASTNAME", "EMAIL", "PHONENUM"]
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

                row.add(0, resultSet.getInt("GUESTID"));
                System.out.println("GUESTID: " + row.get(0));
                row.add(1, resultSet.getString("FIRSTNAME"));
                row.add(2, resultSet.getString("LASTNAME"));
                row.add(3, resultSet.getString("EMAIL"));
                row.add(4, resultSet.getString("PHONENUM"));
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

    GuestScroller(String query) {
        model = new DefaultTableModel(getData(query), columns){
            private static final long serialVersionUID = 1L;

            @Override
            public Class<?> getColumnClass(int index){
                switch(getColumnName(index)) {
                    case "GUESTID":
                        return Integer.class;
                    case "FIRSTNAME":
                    case "LASTNAME":
                    case "EMAIL":
                    case "PHONENUM":
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

        TableCellRenderer rendererFromHeader = table.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setVerticalTextPosition(JLabel.CENTER);
        table.setRowHeight(25);

        // Make ID column less wide & right justify
        table.getColumnModel().getColumn(ID_INDEX).setPreferredWidth(55);
        DefaultTableCellRenderer left = new DefaultTableCellRenderer();
        left.setHorizontalAlignment(JLabel.LEFT);
        table.getColumnModel().getColumn(ID_INDEX).setCellRenderer(left);

        // Make email column wider
        table.getColumnModel().getColumn(EMAIL_INDEX).setPreferredWidth(200);

        // Make phone column wider & set default right
        table.getColumnModel().getColumn(PHONE_INDEX).setPreferredWidth(100);
        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(PHONE_INDEX).setCellRenderer(right);

        // Don't let the table resize
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Add scroll bar to the table
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0,0, table.getWidth(), table.getHeight());
        scrollPane.setComponentZOrder(scrollPane.getVerticalScrollBar(), 0);
        scrollPane.setComponentZOrder(scrollPane.getViewport(), 1);
    }
}
