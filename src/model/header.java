package model;

import java.util.ArrayList;

public class header {


    int invoice_Num;
    String invoice_Date;
    String customer_Name;
    ArrayList<lines> lines;

    double total = 0.0;


    public header(int invoice_Num, String invoice_Date, String customer_Name) {
        this.invoice_Num = invoice_Num;
        this.invoice_Date = invoice_Date;
        this.customer_Name = customer_Name;
    }

    //properties to get updated data of header
    public int getInvoice_Num() {
        return invoice_Num;
    }

    public String getInvoice_Date() {
        return invoice_Date;
    }

    public String getCustomer_Name() {
        return customer_Name;
    }

    public ArrayList<lines> getInvoiceLines() {
        if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    //method to get header table total
    public double getHeaderTotal() {
        for (lines lines : getInvoiceLines()) {
            total += lines.getLineTotal();
        }
        return total;
    }

    //method to get header data as csv format
    public String getHeaderCsv() {
        return invoice_Num + "," + invoice_Date + "," + customer_Name;
    }

    //method to print header table data
    public String printInvoiceHeader() {
        return "header \n{\n" + "invoice_Num = " + invoice_Num
                + " , invoice_Date = " + invoice_Date + " , customer_Name = " + customer_Name + "\n}";
    }
}

