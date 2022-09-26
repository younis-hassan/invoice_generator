package controller;

import model.header;
import model.header_model;
import model.lines;
import model.line_model;
import view.header_dialogue;
import view.invoice_frame;
import view.line_dialogue;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class operation implements ActionListener, ListSelectionListener {

    //define data to access invoice header and line files
    JFileChooser readFileChooser, writeFileChooser;
    int readResult, saveResult;
    header header;
    ArrayList<header> headerArrayList = new ArrayList<>();
    header_model headermodel;
    lines lines;
    line_model linemodel;
    invoice_frame invoiceframe;
    header_dialogue invoiceDialog;
    line_dialogue linedialogue;

    //method to implement actions on file menu
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "loadFile" -> readFiles();
            case "saveFile" -> writeFiles();
            case "exit" -> System.exit(0);
            case "createHeader" -> createHeaderInvoice();
            case "deleteHeader" -> deleteHeaderInvoice();
            case "submitHeaderDialog" -> submitHeaderDialog();
            case "cancelHeaderDialog" -> cancelHeaderDialog();
            case "createLine" -> createLineInvoice();
            case "deleteLine" -> deleteLineInvoice();
            case "submitLineDialog" -> submitLineDialog();
            case "cancelLineDialog" -> cancelLineDialog();
        }
    }

    //method to show lines data after select header index
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = invoiceframe.getHeaderTable().getSelectedRow();
        if (selectedIndex != -1) {
            System.out.println("-----you select row number : " + selectedIndex + "-----");
            header selectedInvoice = invoiceframe.getHeaderArrayList().get(selectedIndex);
            invoiceframe.getNumberValueLbl().setText(String.valueOf(selectedInvoice.getInvoice_Num()));
            invoiceframe.getDateTxt().setText(selectedInvoice.getInvoice_Date());
            invoiceframe.getCustomerNameTxt().setText(selectedInvoice.getCustomer_Name());
            invoiceframe.getTotalValueLbl().setText(String.valueOf(selectedInvoice.getHeaderTotal()));
            linemodel = new line_model(selectedInvoice.getInvoiceLines());
            invoiceframe.getLineTable().setModel(linemodel);
            linemodel.fireTableDataChanged();
        }
    }

    //create constructor to get receive invoice_frame object and send it to FileOperation class
    public operation(invoice_frame invoiceframe) {
        this.invoiceframe = invoiceframe;
    }

    //method to read header and lines data from .csv file
    public void readFiles() {
        readFileChooser = new JFileChooser();
        try {
            readResult = readFileChooser.showOpenDialog(invoiceframe);
            if (readResult == JFileChooser.APPROVE_OPTION) {
                File headerFile = readFileChooser.getSelectedFile();  //save file path
                Path path = Paths.get(headerFile.getAbsolutePath());
                System.out.println("-----headerFile path is : " + path + "-----");
                List<String> headerLines = Files.readAllLines(path, Charset.defaultCharset());
                for (String line : headerLines) {
                    try {
                        String[] dataHeader = line.split(","); // use comma as separator
                        header = new header(Integer.parseInt(dataHeader[0]), dataHeader[1], dataHeader[2]); //update data to header model
                        headerArrayList.add(header);
                        String printTxt = header.printInvoiceHeader();
                        System.out.println(printTxt);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(invoiceframe, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }
                }
                System.out.println("-----end of read first file-----");
                readResult = readFileChooser.showOpenDialog(invoiceframe);
                if (readResult == JFileChooser.APPROVE_OPTION) {
                    File lineFile = readFileChooser.getSelectedFile();  //save file path
                    Path path2 = Paths.get(lineFile.getAbsolutePath());
                    System.out.println("lineFile path is : " + path);
                    List<String> dataLines = Files.readAllLines(path2, Charset.defaultCharset());
                    for (String line : dataLines) {
                        try {
                            String[] dataLine = line.split(","); // use comma as separator
                            int headerNum = Integer.parseInt(dataLine[0]);
                            String itemName = dataLine[1];
                            double itemPrice = Double.parseDouble(dataLine[2]);
                            int count = Integer.parseInt(dataLine[3]);
                            header invHeader = null;
                            for (model.header header : headerArrayList) {
                                if (header.getInvoice_Num() == headerNum) {
                                    invHeader = header;
                                    break;
                                }
                            }
                            lines = new lines(itemName, itemPrice, count, invHeader); //update data to header model
                            if (invHeader != null) {
                                invHeader.getInvoiceLines().add(lines);
                            }
                            String printTxt = lines.printInvoiceLine();
                            System.out.println(printTxt);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(invoiceframe, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                            System.exit(0);
                        }
                    }
                    System.out.println("-----end of read second file-----");
                }
                invoiceframe.setHeaderArrayList(headerArrayList);
                headermodel = new header_model(headerArrayList);
                invoiceframe.setHeaderTableModel(headermodel);
                invoiceframe.getHeaderTable().setModel(headermodel);
                invoiceframe.getHeaderTableModel().fireTableDataChanged();
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(invoiceframe, "can’t read file", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    //method to write header and lines data to .csv file
    public void writeFiles() {
        writeFileChooser = new JFileChooser();
        ArrayList<header> headersList = invoiceframe.getHeaderArrayList();
        StringBuilder headers = new StringBuilder();
        StringBuilder lines = new StringBuilder();
        for (header header : headersList) {
            String headerCsv = header.getHeaderCsv();
            headers.append(headerCsv);
            headers.append("\n");
            for (model.lines invoiceLine : header.getInvoiceLines()) {
                String lineCsv = invoiceLine.getLine();
                lines.append(lineCsv);
                lines.append("\n");
            }
        }
        System.out.println("-----we get data and save it to header and lines-----");
        try {
            saveResult = writeFileChooser.showSaveDialog(invoiceframe);
            if (saveResult == JFileChooser.APPROVE_OPTION) {
                File headerFile = writeFileChooser.getSelectedFile();
                FileWriter headerWriter = new FileWriter(headerFile);
                headerWriter.write(headers.toString());
                headerWriter.flush();
                headerWriter.close();
                System.out.println("-----data saved to header.csv-----");

                saveResult = writeFileChooser.showSaveDialog(invoiceframe);
                if (saveResult == JFileChooser.APPROVE_OPTION) {
                    File lineFile = writeFileChooser.getSelectedFile();
                    FileWriter lineWriter = new FileWriter(lineFile);
                    lineWriter.write(lines.toString());
                    lineWriter.flush();
                    lineWriter.close();
                    System.out.println("-----data saved to lines.csv-----");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(invoiceframe, "can’t save file", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    //method to create header to HeaderTable
    private void createHeaderInvoice() {
        invoiceDialog = new header_dialogue(invoiceframe);
        invoiceDialog.setVisible(true);
    }

    //method to delete header from HeaderTable
    private void deleteHeaderInvoice() {
        int selectedRow = invoiceframe.getHeaderTable().getSelectedRow();
        if (selectedRow != -1) {
            invoiceframe.getHeaderArrayList().remove(selectedRow);
            invoiceframe.getHeaderTableModel().fireTableDataChanged();

            //clear data after delete invoice
            invoiceframe.getNumberValueLbl().setText(null);
            invoiceframe.getDateTxt().setText(null);
            invoiceframe.getCustomerNameTxt().setText(null);
            invoiceframe.getTotalValueLbl().setText(null);
            System.out.println("-----invoice number : " + selectedRow + " is deleted successfully-----");
        } else {
            JOptionPane.showMessageDialog(invoiceframe, "you must select one header invoice at least", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    //method to submit creation of HeaderInvoice
    private void submitHeaderDialog() {
        String date = invoiceDialog.getDateTxt().getText();
        String customer = invoiceDialog.getCustomerNameTxt().getText();
        int num = invoiceframe.getNextInvoice_Num();
        try {
            String[] dateParts = date.split("-");
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            if (date.equals("") || customer.equals("")) {
                JOptionPane.showMessageDialog(invoiceframe, "you must enter all data", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (dateParts.length < 3 || day > 31 || month > 12) {
                JOptionPane.showMessageDialog(invoiceframe, "Wrong date format", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                header header = new header(num, date, customer);
                invoiceframe.getHeaderArrayList().add(header);
                invoiceframe.getHeaderTableModel().fireTableDataChanged();
                invoiceDialog.setVisible(false);
                invoiceDialog.dispose();
                invoiceDialog = null;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(invoiceframe, "Wrong date format", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void cancelHeaderDialog() {
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
    }


    private void createLineInvoice() {
        int selectedInvoice = invoiceframe.getHeaderTable().getSelectedRow();
        if (selectedInvoice == -1) {
            JOptionPane.showMessageDialog(invoiceframe, "you must select invoice header at first", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            linedialogue = new line_dialogue(invoiceframe);
            linedialogue.setVisible(true);
        }
    }


    private void deleteLineInvoice() {
        int selectedRow = invoiceframe.getLineTable().getSelectedRow();
        if (selectedRow != -1) {
            line_model linemodel = (line_model) invoiceframe.getLineTable().getModel();
            linemodel.getLineArrayList().remove(selectedRow);
            linemodel.fireTableDataChanged();
            invoiceframe.getHeaderTableModel().fireTableDataChanged();
            System.out.println("-----line number : " + selectedRow + " is deleted successfully-----");

            //clear data after delete line
            invoiceframe.getNumberValueLbl().setText(null);
            invoiceframe.getDateTxt().setText(null);
            invoiceframe.getCustomerNameTxt().setText(null);
            invoiceframe.getTotalValueLbl().setText(null);
        } else {
            JOptionPane.showMessageDialog(invoiceframe, "you must select one line invoice at least", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void submitLineDialog() {
        String itemName = linedialogue.getItemNameTxt().getText();
        String itemPrice = linedialogue.getItemPriceTxt().getText();
        String itemCount = linedialogue.getItemCountTxt().getText();
        int count = Integer.parseInt(itemCount);
        double price = Double.parseDouble(itemPrice);
        int selectedInvoice = invoiceframe.getHeaderTable().getSelectedRow();
        try {
            if (!itemName.equals("") || !itemPrice.equals("") || !itemCount.equals("")) {
                header invoice = invoiceframe.getHeaderArrayList().get(selectedInvoice);
                lines line = new lines(itemName, price, count, invoice);
                invoice.getInvoiceLines().add(line);
                line_model linemodel = (line_model) invoiceframe.getLineTable().getModel();
                linemodel.fireTableDataChanged();
                invoiceframe.getHeaderTableModel().fireTableDataChanged();
                linedialogue.setVisible(false);
                linedialogue.dispose();
                linedialogue = null;
            } else {
                JOptionPane.showMessageDialog(invoiceframe, "you must enter all data", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(invoiceframe, "you must enter valid type of data", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void cancelLineDialog() {
        linedialogue.setVisible(false);
        linedialogue.dispose();
        linedialogue = null;
    }

}

