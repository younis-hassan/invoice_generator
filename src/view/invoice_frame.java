package view;

import controller.operation;
import model.header;
import model.header_model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class invoice_frame extends JFrame {

    //definition for all frame data
    JMenuBar menuBar;
    private JMenu fileMenu;
    JMenuItem loadFileItem, saveFileItem, exitItem;
    private JPanel headerPanel, linePanel, dataPanel;
    JButton addBtn, deleteBtn, saveBtn, cancelBtn;
    JLabel numberLbl, numberValueLbl, dateLbl, nameLbl, totalLbl, totalValueLbl, itemsLbl;
    JTextField dateTxt, customerNameTxt;
    private JTable headerTable, lineTable;


    //define objects from ActionListeners classes
    operation operation;
    ArrayList<header> headerArrayList;
    header_model headermodel;

    //define properties for all data
    public JTable getHeaderTable() {
        return headerTable;
    }

    public ArrayList<header> getHeaderArrayList() {
        if (headerArrayList == null) {
            headerArrayList = new ArrayList<>();
        }
        return headerArrayList;
    }

    public void setHeaderArrayList(ArrayList<header> headerArrayList) {
        this.headerArrayList = headerArrayList;
    }

    public header_model getHeaderTableModel() {
        if (headermodel == null) {
            headermodel = new header_model(getHeaderArrayList());
        }
        return headermodel;
    }

    public void setHeaderTableModel(header_model headermodel) {
        this.headermodel = headermodel;
    }

    public JTable getLineTable() {
        return lineTable;
    }

    public JLabel getNumberValueLbl() {
        return numberValueLbl;
    }

    public JLabel getTotalValueLbl() {
        return totalValueLbl;
    }

    public JTextField getDateTxt() {
        return dateTxt;
    }

    public JTextField getCustomerNameTxt() {
        return customerNameTxt;
    }

    public operation getFileOperations() {
        return operation;
    }

    //constructor for invoiceFrame creation
    public invoice_frame() {

        //define frame data
        super("Invoice Frame");
        setBounds(50, 50, 1020, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        //declaration for objects from ActionListener classes
        operation = new operation(this);

        //declaration invoice menuBar
        menuBar = new JMenuBar();
        drawFileMenu();
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        //declaration for header panel
        drawHeaderPanel();
        add(headerPanel);

        //declaration for line panel
        drawLinePanel();
        add(linePanel);

    }

    //method to draw file menu
    void drawFileMenu() {
        fileMenu = new JMenu("File");

        loadFileItem = new JMenuItem("Load File", 'L');
        loadFileItem.addActionListener(operation);
        loadFileItem.setActionCommand("loadFile");

        saveFileItem = new JMenuItem("Save File", 'S');
        saveFileItem.addActionListener(operation);
        saveFileItem.setActionCommand("saveFile");

        exitItem = new JMenuItem("Exit", 'E');
        exitItem.addActionListener(operation);
        exitItem.setActionCommand("exit");

        fileMenu.add(loadFileItem);
        fileMenu.add(saveFileItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

    }

    //method to draw header invoice panel
    void drawHeaderPanel() {
        headerPanel = new JPanel();
        headerPanel.setBorder(BorderFactory.createBevelBorder(1));
        headerPanel.setLayout(new FlowLayout());

        headerPanel.add(new JLabel("Invoice Table"));

        drawHeaderTable();
        headerPanel.add(new JScrollPane(headerTable));

        addBtn = new JButton("Create New Invoice");
        addBtn.setActionCommand("createHeader");
        addBtn.addActionListener(operation);
        headerPanel.add(addBtn);

        deleteBtn = new JButton("Delete Invoice");
        deleteBtn.setActionCommand("deleteHeader");
        deleteBtn.addActionListener(operation);
        headerPanel.add(deleteBtn);
    }

    //method to draw header invoice table
    void drawHeaderTable() {
        headerTable = new JTable();
        headerTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{}
        ));
        headerTable.setModel(getHeaderTableModel());
        headerTable.getSelectionModel().addListSelectionListener(operation);
    }

    //method to draw line invoice panel
    void drawLinePanel() {
        linePanel = new JPanel();
        linePanel.setBorder(BorderFactory.createBevelBorder(1));
        linePanel.setLayout(null);

        numberLbl = new JLabel("Invoice Number");
        numberLbl.setBounds(20, 10, 100, 30);
        linePanel.add(numberLbl);

        numberValueLbl = new JLabel();
        numberValueLbl.setBounds(140, 10, 100, 30);
        linePanel.add(numberValueLbl);

        dateLbl = new JLabel("Invoice Date");
        dateLbl.setBounds(20, 50, 100, 30);
        linePanel.add(dateLbl);

        dateTxt = new JTextField(15);
        dateTxt.setBounds(140, 55, 300, 25);
        dateTxt.setEditable(false);
        linePanel.add(dateTxt);

        nameLbl = new JLabel("Customer Name");
        nameLbl.setBounds(20, 90, 100, 30);
        linePanel.add(nameLbl);

        customerNameTxt = new JTextField(15);
        customerNameTxt.setBounds(140, 95, 300, 25);
        customerNameTxt.setEditable(false);
        linePanel.add(customerNameTxt);

        totalLbl = new JLabel("Invoice Total");
        totalLbl.setBounds(20, 130, 100, 30);
        linePanel.add(totalLbl);

        totalValueLbl = new JLabel();
        totalValueLbl.setBounds(140, 130, 100, 30);
        linePanel.add(totalValueLbl);

        itemsLbl = new JLabel("Invoice Items");
        itemsLbl.setOpaque(true);
        itemsLbl.setForeground(Color.gray);
        itemsLbl.setBounds(20, 170, 100, 30);
        linePanel.add(itemsLbl);

        drawDataPanel();
        dataPanel.setBounds(25, 200, 450, 300);
        linePanel.add(dataPanel);

        saveBtn = new JButton("Create Item");
        saveBtn.setBounds(100, 550, 100, 30);
        saveBtn.setActionCommand("createLine");
        saveBtn.addActionListener(operation);
        linePanel.add(saveBtn);

        cancelBtn = new JButton("Delete Item");
        cancelBtn.setBounds(250, 550, 100, 30);
        cancelBtn.setActionCommand("deleteLine");
        cancelBtn.addActionListener(operation);
        linePanel.add(cancelBtn);

    }

    //method to draw line invoice data panel
    void drawDataPanel() {
        dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout());
        drawDefaultLineTable();
        dataPanel.add(new JScrollPane(lineTable));
    }

    //method to draw line invoice table
    void drawDefaultLineTable() {
        String[] cols = {"No.", "Item Name", "Item Price", "Count", "Total"};
        int rows = 0;
        DefaultTableModel defaultLineTable = new DefaultTableModel(rows, cols.length);
        defaultLineTable.setColumnIdentifiers(cols);
        lineTable = new JTable(defaultLineTable);
    }

    //method to add new invoice in new index of table
    public int getNextInvoice_Num() {
        int num = 0;
        for (model.header header : getHeaderArrayList()) {
            if (header.getInvoice_Num() > num)
                num = header.getInvoice_Num();
        }
        return ++num;
    }

}
