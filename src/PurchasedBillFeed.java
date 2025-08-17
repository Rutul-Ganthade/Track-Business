import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Vector;

public class PurchasedBillFeed extends JPanel {

    JTextField partyField, gstField, locationField, invoiceField, hsnField, nameField, unitPriceField, batchField, expiryField, quanField;
    JComboBox<String> paymentTypeBox, categoryBox, netWeightBox;
    JButton addBtn, finalBtn;
    JLabel errorLabel, totalLabel;
    JTable table;
    DefaultTableModel model;

    public PurchasedBillFeed() {
        setLayout(null);
        setBounds(100, 100, 1000, 800);

        JLabel heading = new JLabel("BILLING PANEL");
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        heading.setBounds(400, 20, 300, 30);
        add(heading);

        JLabel lblParty = new JLabel("Party Name:");
        lblParty.setBounds(60, 70, 150, 25);
        add(lblParty);

        partyField = new JTextField();
        partyField.setBounds(160, 70, 180, 25);
        add(partyField);

        JLabel lblGST = new JLabel("GSTIN:");
        lblGST.setBounds(380, 70, 100, 25);
        add(lblGST);

        gstField = new JTextField();
        gstField.setBounds(460, 70, 150, 25);
        add(gstField);

        JLabel lblLocation = new JLabel("Location:");
        lblLocation.setBounds(630, 70, 100, 25);
        add(lblLocation);

        locationField = new JTextField();
        locationField.setBounds(710, 70, 150, 25);
        add(locationField);

        JLabel lblInvoice = new JLabel("Invoice No:");
        lblInvoice.setBounds(60, 110, 150, 25);
        add(lblInvoice);

        invoiceField = new JTextField();
        invoiceField.setBounds(160, 110, 180, 25);
        add(invoiceField);

        JLabel lblPayment = new JLabel("Payment Type:");
        lblPayment.setBounds(380, 110, 150, 25);
        add(lblPayment);

        paymentTypeBox = new JComboBox<>(new String[]{"Cash", "Credit"});
        paymentTypeBox.setBounds(480, 110, 180, 25);
        add(paymentTypeBox);

        String[] cols = {"HSN Code", "Product Name", "Net Weight", "Category", "Unit Price", "Quantity", "Batch No", "Expiry Date"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 160, 900, 250);
        add(scrollPane);

        hsnField = new JTextField();
        nameField = new JTextField();
        unitPriceField = new JTextField();
        quanField = new JTextField();
        batchField = new JTextField();
        expiryField = new JTextField();
        netWeightBox = new JComboBox<>(new String[]{"gm", "kg", "ml", "litre", "piece"});
        categoryBox = new JComboBox<>();
        refreshCategories();

        int y = 430, spacing = 120;

        add(new JLabelField("HSN Code:", hsnField, 60, y));
        add(new JLabelField("Product Name:", nameField, 60 + spacing, y));
        add(new JLabelField("Net Weight:", netWeightBox, 60 + 2 * spacing, y));
        add(new JLabelField("Category:", categoryBox, 60 + 3 * spacing, y));
        add(new JLabelField("Unit Price:", unitPriceField, 60 + 4 * spacing, y));
        add(new JLabelField("Quantity:", quanField, 60 + 5 * spacing, y));
        add(new JLabelField("Batch No:", batchField, 60 + 6 * spacing, y));
        add(new JLabelField("Expiry:", expiryField, 60 + 7 * spacing, y));

        addBtn = new JButton("Add Product");
        addBtn.setBounds(400, 500, 180, 30);
        add(addBtn);

        finalBtn = new JButton("Submit Invoice");
        finalBtn.setBounds(600, 500, 180, 30);
        add(finalBtn);

        totalLabel = new JLabel("Total: ₹0.00");
        totalLabel.setBounds(800, 500, 200, 30);
        totalLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(totalLabel);

        errorLabel = new JLabel();
        errorLabel.setBounds(300, 550, 400, 25);
        errorLabel.setForeground(Color.RED);
        add(errorLabel);

        addBtn.addActionListener(e -> {
            try {
                String[] row = {
                        hsnField.getText(),
                        nameField.getText(),
                        netWeightBox.getSelectedItem().toString(),
                        categoryBox.getSelectedItem().toString(),
                        unitPriceField.getText(),
                        quanField.getText(),
                        batchField.getText(),
                        expiryField.getText()
                };
                model.addRow(row);
                updateTotal();
                clearFields();
            } catch (Exception ex) {
                errorLabel.setText("Error: " + ex.getMessage());
            }
        });

        finalBtn.addActionListener(e -> {
            try {
                String party = partyField.getText();
                String gst = gstField.getText();
                String location = locationField.getText();
                DB.addParty(party, gst, location);

                String invoice = invoiceField.getText();
                String payment = paymentTypeBox.getSelectedItem().toString();

                for (int i = 0; i < model.getRowCount(); i++) {
                    String hsn = model.getValueAt(i, 0).toString();
                    String name = model.getValueAt(i, 1).toString();
                    String netWeight = model.getValueAt(i, 2).toString();
                    String cat = model.getValueAt(i, 3).toString();
                    double unitPrice = Double.parseDouble(model.getValueAt(i, 4).toString());
                    int quantity = Integer.parseInt(model.getValueAt(i, 5).toString());
                    String batch = model.getValueAt(i, 6).toString();
                    String expiry = model.getValueAt(i, 7).toString();

                    DB.insertBill(party, invoice, payment, hsn, name, netWeight, cat, unitPrice, quantity, batch, expiry);
                    DB.addProduct(hsn, name, netWeight, cat, unitPrice, quantity, batch, expiry);
                }

                JOptionPane.showMessageDialog(null, "Invoice saved successfully!");
                model.setRowCount(0);
                updateTotal();
            } catch (Exception ex) {
                errorLabel.setText("Error: " + ex.getMessage());
            }
        });
    }

    private void refreshCategories() {
        categoryBox.removeAllItems();
        for (String cat : DB.getCategories()) {
            categoryBox.addItem(cat);
        }
    }

    private void clearFields() {
        hsnField.setText("");
        nameField.setText("");
        unitPriceField.setText("");
        quanField.setText("");
        batchField.setText("");
        expiryField.setText("");
    }

    private void updateTotal() {
        double total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            double price = Double.parseDouble(model.getValueAt(i, 4).toString());
            int qty = Integer.parseInt(model.getValueAt(i, 5).toString());
            total += price * qty;
        }
        totalLabel.setText("Total: ₹" + String.format("%.2f", total));
    }

    private class JLabelField extends JPanel {
        JLabelField(String text, Component field, int x, int y) {
            setLayout(null);
            setBounds(x, y, 110, 50);
            JLabel label = new JLabel(text);
            label.setBounds(0, 0, 100, 20);
            add(label);
            field.setBounds(0, 25, 100, 20);
            add(field);
        }
    }
}
