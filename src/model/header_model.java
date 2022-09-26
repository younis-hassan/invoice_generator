package model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class header_model extends AbstractTableModel {

    //define rows and cols of header table
    String[] cols = {"No.", "Date", "Customer", "Total"};
    ArrayList<header> headerArrayList;

    public header_model(ArrayList<header> headerArrayList) {
        this.headerArrayList = headerArrayList;
    }

    //override method to get size of table rows
    @Override
    public int getRowCount() {
        return headerArrayList.size();
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

    //override method to get data of header table
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        header header = headerArrayList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> header.getInvoice_Num();
            case 1 -> header.getInvoice_Date();
            case 2 -> header.getCustomer_Name();
            case 3 -> header.getHeaderTotal();
            default -> "";
        };
    }

}
