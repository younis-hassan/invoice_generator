package model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class line_model extends AbstractTableModel {

    //define rows and cols of line table
    String[] cols = {"No.", "Item Name", "Item Price", "Count", "Total"};
    ArrayList<lines> lineArrayList;

    public line_model(ArrayList<lines> lineArrayList) {
        this.lineArrayList = lineArrayList;
    }

    public ArrayList<lines> getLineArrayList() {
        return lineArrayList;
    }

    //override method to get size of table rows
    @Override
    public int getRowCount() {
        return lineArrayList.size();
    }

    //override method to get length of table cols
    @Override
    public int getColumnCount() {
        return cols.length;
    }

    //override method to get name of table cols
    @Override
    public String getColumnName(int column) {
        return cols[column];
    }

    //override method to get data of line table
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        lines lines = lineArrayList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> lines.getInvoiceHeader().getInvoice_Num();
            case 1 -> lines.getItem_Name();
            case 2 -> lines.getItem_Price();
            case 3 -> lines.getCount();
            case 4 -> lines.getLineTotal();
            default -> "";
        };
    }

}
