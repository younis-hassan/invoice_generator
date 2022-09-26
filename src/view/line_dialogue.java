package view;

import javax.swing.*;
import java.awt.*;

public class line_dialogue extends JDialog {
    //definition for all dialog data
    JLabel itemNameLbl, itemPriceLbl, itemCountLbl;
    JTextField itemNameTxt, itemPriceTxt, itemCountTxt;
    JButton submitBtn, cancelBtn;

    //define properties for all data
    public JTextField getItemPriceTxt() {
        return itemPriceTxt;
    }

    public JTextField getItemNameTxt() {
        return itemNameTxt;
    }

    public JTextField getItemCountTxt() {
        return itemCountTxt;
    }

    //constructor for invoice creation dialog creation
    public line_dialogue(invoice_frame invoiceframe) {
        setLocation(400, 400);
        setLayout(new GridLayout(4, 2));
        setTitle("Create Line Invoice");

        itemNameLbl = new JLabel("Item Name : ");
        itemNameTxt = new JTextField(20);

        itemPriceLbl = new JLabel("Item Price : ");
        itemPriceTxt = new JTextField(20);

        itemCountLbl = new JLabel("Item Count : ");
        itemCountTxt = new JTextField(20);

        submitBtn = new JButton("submit");
        submitBtn.setActionCommand("submitLineDialog");
        submitBtn.addActionListener(invoiceframe.getFileOperations());

        cancelBtn = new JButton("cancel");
        cancelBtn.setActionCommand("cancelLineDialog");
        cancelBtn.addActionListener(invoiceframe.getFileOperations());

        add(itemNameLbl);
        add(itemNameTxt);
        add(itemPriceLbl);
        add(itemPriceTxt);
        add(itemCountLbl);
        add(itemCountTxt);
        add(submitBtn);
        add(cancelBtn);

        pack();
    }
}
