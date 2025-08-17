import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ShowPurchasedBill extends JPanel {
    private JComboBox<String> partyCombo;
    private JTextField invoiceField;
    private JButton searchBtn;
    private JTable billTable;
    private DefaultTableModel tableModel;

    public ShowPurchasedBill() {
        setLayout(null);
        setBounds(100, 100, 1000, 700);

        JLabel heading = new JLabel("SHOW PURCHASED BILL");
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        heading.setBounds(360, 20, 400, 30);
        add(heading);

        JLabel lblParty = new JLabel("Select Party:");
        lblParty.setBounds(200, 80, 100, 25);
        add(lblParty);

        partyCombo = new JComboBox<>();
        partyCombo.setBounds(310, 80, 200, 25);
        add(partyCombo);
        loadParties();

        JButton refreshBtn = new JButton("↻");
        refreshBtn.setBounds(520, 80, 50, 25);
        add(refreshBtn);

        refreshBtn.addActionListener(e -> loadParties());


        JLabel lblInvoice = new JLabel("Invoice No:");
        lblInvoice.setBounds(530, 80, 100, 25);
        add(lblInvoice);

        invoiceField = new JTextField();
        invoiceField.setBounds(620, 80, 150, 25);
        add(invoiceField);

        searchBtn = new JButton("Show Bill");
        searchBtn.setBounds(400, 120, 200, 30);
        add(searchBtn);

        String[] cols = {"HSN Code", "Product Name", "Net Weight", "Category", "Unit Price", "Quantity", "Batch No", "Expiry Date"};
        tableModel = new DefaultTableModel(cols, 0);
        billTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(billTable);
        scrollPane.setBounds(50, 180, 900, 400);
        add(scrollPane);

        searchBtn.addActionListener(e -> showBill());
    }

    private void loadParties() {
        partyCombo.removeAllItems();
        try (Connection conn = DB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT PartyName FROM Parties")) {
            while (rs.next()) {
                partyCombo.addItem(rs.getString("PartyName"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading parties: " + e.getMessage());
        }
    }

    private void showBill() {
        tableModel.setRowCount(0); // Clear previous data
        String party = (String) partyCombo.getSelectedItem();
        String invoice = invoiceField.getText().trim();

        if (party == null || invoice.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a party and enter invoice number.");
            return;
        }

        try (Connection conn = DB.getConnection()) {
            // Validate the invoice belongs to the selected party
            String validateQuery = "SELECT * FROM bills WHERE Invoice_no = ? AND Party_name = ?";
            try (PreparedStatement validateStmt = conn.prepareStatement(validateQuery)) {
                validateStmt.setString(1, invoice);
                validateStmt.setString(2, party);
                ResultSet validRs = validateStmt.executeQuery();

                if (!validRs.next()) {
                    JOptionPane.showMessageDialog(null, "No such invoice found for selected party.");
                    return;
                }
            }

            String query = "SELECT Hsn_code, Product_name, Net_weight, Category, Unit_price, Quantity, Batch_no, Expiry_date FROM bill_items WHERE Invoice_no = ?";
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setString(1, invoice);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    Object[] row = {
                            rs.getString("Hsn_code"),
                            rs.getString("Product_name"),
                            rs.getString("Net_weight"),
                            rs.getString("Category"),
                            rs.getDouble("Unit_price"),
                            rs.getInt("Quantity"),
                            rs.getString("Batch_no"),
                            rs.getString("Expiry_date")
                    };
                    tableModel.addRow(row);
                }

                if (tableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "No bill items found for this invoice.");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error retrieving bill: " + e.getMessage());
        }
    }
    @Override
    public void addNotify() {
        super.addNotify();
        loadParties();  // Reload parties every time the panel becomes visible
    }

}

