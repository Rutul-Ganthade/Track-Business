import javax.swing.*;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Invoice extends JPanel {
	private JTextField name;
	private JTextField pID;
	private JTextField pQuan;
	private JTable items;
	private JTextField UnitPrice;
	JLabel lblName;
	JLabel error;
	static int invo = 1;
	DefaultTableModel dtm;
	Object data[];
	JComboBox<String> cType, productCombo;
	ArrayList<String> comp = new ArrayList<String>();
	private JTextField dField;
	long total = 0;
	JLabel gtotal;

	public Invoice() {
		setLayout(null);

		JLabel lblCustomer = new JLabel("Customer");
		lblCustomer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCustomer.setBounds(88, 59, 97, 23);
		add(lblCustomer);

		cType = new JComboBox<String>();
		cType.setBounds(201, 62, 140, 20);
		add(cType);
		cType.addItem("Walk-in customer");
		cType.addItem("Company/customer name");
		cType.setSelectedIndex(1);
		cType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				boolean showName = cType.getSelectedIndex() == 1;
				lblName.setVisible(showName);
				name.setVisible(showName);
			}
		});

		lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(364, 59, 64, 23);
		add(lblName);

		name = new JTextField();
		name.setBounds(438, 62, 150, 20);
		add(name);
		name.setColumns(10);

		JLabel lblProduct = new JLabel("Product Name");
		lblProduct.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProduct.setBounds(88, 100, 97, 23);
		add(lblProduct);

		productCombo = new JComboBox<>();
		productCombo.setBounds(201, 100, 150, 20);
		add(productCombo);

		ArrayList<String> productNames = DB.getAllProductNames();
		for (String pname : productNames) {
			productCombo.addItem(pname);
		}

		JLabel lblHsnCode = new JLabel("HSN Code");
		lblHsnCode.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHsnCode.setBounds(88, 133, 80, 23);
		add(lblHsnCode);

		pID = new JTextField();
		pID.setBounds(201, 133, 150, 20);
		add(pID);
		pID.setColumns(10);
		pID.setEditable(false);

		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuantity.setBounds(88, 174, 97, 23);
		add(lblQuantity);

		pQuan = new JTextField();
		pQuan.setColumns(10);
		pQuan.setBounds(201, 177, 150, 20);
		add(pQuan);

		JLabel lblUnitPrice = new JLabel("Unit Price");
		lblUnitPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUnitPrice.setBounds(88, 217, 97, 23);
		add(lblUnitPrice);

		UnitPrice = new JTextField();
		UnitPrice.setColumns(10);
		UnitPrice.setBounds(201, 220, 150, 20);
		add(UnitPrice);

		productCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedProduct = (String) productCombo.getSelectedItem();
                ProductInfo info = null; // Get HSN and Netweight from 'products'
                try {
                    info = DB.getProductDetailsByName(selectedProduct);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (info != null) {
					pID.setText(info.getHsn());
					String hsn = info.getHsn();
                    StockInfo stock = null; // Get UnitPrice and quantity from 'stock'
                    try {
                        stock = DB.getStockInfo(hsn);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (stock != null && stock.getQuantity() > 0) {
						UnitPrice.setText(String.valueOf(stock.getUnitPrice()));
						error.setText("");
					} else {
						error.setText("Out of stock");
						UnitPrice.setText("");
					}
				} else {
					error.setText("Product not found");
					pID.setText("");
					UnitPrice.setText("");
				}
			}
		});

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (UnitPrice.getText().isEmpty() || pQuan.getText().isEmpty() || pID.getText().isEmpty()) {
					error.setText("Enter required data");
					return;
				} else {
					error.setText("");
				}

				long unitPrice, finalPrice, quantity;
				String hsnCode, Netweight = "";
				try {
					unitPrice = Long.parseLong(UnitPrice.getText().trim());
					quantity = Long.parseLong(pQuan.getText().trim());
				} catch (NumberFormatException ex) {
					error.setText("Invalid numeric values");
					return;
				}
				hsnCode = pID.getText().trim();

                String response = null;
                try {
                    response = DB.validateStockAndFetchNetweight(hsnCode, (int) quantity);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
				String[] parts = response.split("%");

				if (parts.length < 2) {
					error.setText("Unexpected response format: " + response);
					return;
				}

				Netweight = parts[0];
				String productName = parts[1];

				if (Netweight.equals("nill")) {
					error.setText("Invalid HSN Code!");
					return;
				} else if (Netweight.equals("item is out of stock")) {
					error.setText(Netweight);
					return;
				} else {
					error.setText("");
					comp.add(productName);
				}


				finalPrice = unitPrice * quantity;
				dtm.addRow(new Object[]{hsnCode, Netweight, unitPrice, quantity, finalPrice});
				total += finalPrice;
				gtotal.setText(String.valueOf(total));

				UnitPrice.setText("");
				pQuan.setText("");
				pID.setText("");
			}
		});
		btnAdd.setBounds(201, 265, 89, 23);
		add(btnAdd);

		String[] header = {"HSN Code", "Netweight", "Unit Price", "Quantity", "Final Price"};
		dtm = new DefaultTableModel(header, 0);
		items = new JTable(dtm);
		JScrollPane s = new JScrollPane(items);
		s.setBounds(361, 135, 392, 265);
		add(s);

		JLabel lblDeleteProduct = new JLabel("DELETE PRODUCT");
		lblDeleteProduct.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDeleteProduct.setBounds(88, 344, 132, 14);
		add(lblDeleteProduct);

		JLabel lblDeleteProductId = new JLabel("HSN Code");
		lblDeleteProductId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDeleteProductId.setBounds(88, 386, 80, 23);
		add(lblDeleteProductId);

		dField = new JTextField();
		dField.setColumns(10);
		dField.setBounds(201, 386, 89, 20);
		add(dField);

		JButton delbutton = new JButton("Delete");
		delbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String deleteHSN = dField.getText().trim();
				boolean found = false;

				for (int row = 0; row < dtm.getRowCount(); row++) {
					String hsn = dtm.getValueAt(row, 0).toString();
					if (hsn.equals(deleteHSN)) {
						int quantity = Integer.parseInt(dtm.getValueAt(row, 3).toString()); // Quantity column
						long finalPrice = Long.parseLong(dtm.getValueAt(row, 4).toString()); // Final Price column

						// Restore stock
						try {
							DB.increaseStock(hsn, quantity);
						} catch (SQLException ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error restoring stock for HSN: " + hsn);
						}

						// Update grand total
						total -= finalPrice;
						if (total < 0) total = 0; // prevent negative total
						gtotal.setText(String.valueOf(total));

						// Remove from table
						dtm.removeRow(row);
						dField.setText("");
						error.setText("");
						found = true;
						break;
					}
				}

				if (!found) {
					error.setText("HSN Code not found in bill items!");
				}
			}
		});

		delbutton.setBounds(201, 440, 89, 23);
		add(delbutton);

		JLabel lblGrandTotal = new JLabel("Grand Total");
		lblGrandTotal.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGrandTotal.setBounds(364, 449, 89, 14);
		add(lblGrandTotal);

		gtotal = new JLabel("");
		gtotal.setFont(new Font("Tahoma", Font.BOLD, 15));
		gtotal.setBounds(470, 449, 132, 14);
		add(gtotal);

		JButton btnFinalSubmit = new JButton("FINAL SUBMIT");
		btnFinalSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String customerName = "";
				if (cType.getSelectedIndex() == 1) {
					customerName = name.getText().trim();
					if (customerName.isEmpty()) {
						error.setText("Customer name required");
						return;
					}
				} else {
					customerName = "Walk-in customer";
				}

				if (dtm.getRowCount() == 0) {
					error.setText("No items to submit!");
					return;
				}

				int invoiceNo = invo++;
				boolean allSaved = true;

				for (int row = 0; row < dtm.getRowCount(); row++) {
					try {
						String hsn = dtm.getValueAt(row, 0).toString();
						String netWeight = dtm.getValueAt(row, 1).toString();
						double unitPrice = Double.parseDouble(dtm.getValueAt(row, 2).toString());
						int quantity = Integer.parseInt(dtm.getValueAt(row, 3).toString());
						double finalPrice = Double.parseDouble(dtm.getValueAt(row, 4).toString());

						DB.saveSale(invoiceNo, customerName, hsn, netWeight, unitPrice, quantity, finalPrice);
						DB.reduceStock(hsn, quantity);
					} catch (Exception ex) {
						allSaved = false;
						ex.printStackTrace();
						error.setText("Error occurred while saving!");
						break;
					}
				}

				if (allSaved) {
					error.setForeground(Color.GREEN);
					error.setText("Bill saved successfully!");
					gtotal.setText("");
					dtm.setRowCount(0);
					name.setText("");
				}
			}
		});
		btnFinalSubmit.setBounds(664, 411, 120, 52);
		add(btnFinalSubmit);

		error = new JLabel("");
		error.setForeground(Color.RED);
		error.setBounds(92, 319, 250, 14);
		add(error);
	}
}
