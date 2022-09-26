package model;

public class lines {


    String item_Name;
    double item_Price;
    int count;
    header header;


    public lines(String item_Name, double item_Price, int count, header header) {
        this.item_Name = item_Name;
        this.item_Price = item_Price;
        this.count = count;
        this.header = header;
    }


    public String getItem_Name() {
        return item_Name;
    }

    public double getItem_Price() {
        return item_Price;
    }

    public int getCount() {
        return count;
    }

    public header getInvoiceHeader() {
        return header;
    }

    public double getLineTotal() {
        return item_Price * count;
    }

    //method to get line data as csv format
    public String getLine() {
        return header.getInvoice_Num() + "," + item_Name + "," + item_Price + "," + count;
    }

    //method to print line table data
    public String printInvoiceLine() {
        return "lines\n{\n" + "invoiceNum = " + header.getInvoice_Num()
                + " , item_Name = " + item_Name + " , item_Price = " + item_Price + " , count = " + count + "\n}";
    }


}
