import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class updateProduct extends JPanel {
	JTextField hsnField, nameField, unitPriceField, batchField, expiryField, quanField;
	JComboBox<String> netWeightBox, category;
	JButton btnUpdateProduct, addCategoryBtn;
	JLabel error;

	public updateProduct() {
		setLayout(null);
		setBounds(100, 100, 900, 650);

		JLabel lblTitle = new JLabel("UPDATE PRODUCT");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitle.setBounds(340, 30, 220, 30);
		add(lblTitle);

		// HSN Code
		JLabel lblHsnCode = new JLabel("HSN Code:");
		lblHsnCode.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHsnCode.setBounds(240, 90, 150, 25);
		add(lblHsnCode);

		hsnField = new JTextField();
		hsnField.setBounds(420, 90, 180, 25);
		add(hsnField);

		// Product Name
		JLabel lblName = new JLabel("Product Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(240, 130, 150, 25);
		add(lblName);

		nameField = new JTextField();
		nameField.setBounds(420, 130, 180, 25);
		add(nameField);

		// Net Weight
		JLabel lblNetWeight = new JLabel("Net Weight:");
		lblNetWeight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNetWeight.setBounds(240, 170, 150, 25);
		add(lblNetWeight);

		netWeightBox = new JComboBox<>(new String[]{"gm", "kg", "ml", "litre", "piece"});
		netWeightBox.setBounds(420, 170, 180, 25);
		add(netWeightBox);

		// Category Label
		JLabel lblCategory = new JLabel("Category:");
		lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCategory.setBounds(240, 210, 150, 25);
		add(lblCategory);

		// Category ComboBox
		category = new JComboBox<>();
		category.setBounds(420, 210, 130, 25);
		refreshCategories();
		add(category);

		// Add New Category Button
		addCategoryBtn = new JButton("+");
		addCategoryBtn.setBounds(560, 210, 40, 25);
		add(addCategoryBtn);

		addCategoryBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newCategory = JOptionPane.showInputDialog("Enter new category:");
				if (newCategory != null && !newCategory.trim().isEmpty()) {
					DB.addCategory(newCategory.trim());
					refreshCategories();
				}
			}
		});

		// Unit Price
		JLabel lblUnitPrice = new JLabel("Unit Price:");
		lblUnitPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUnitPrice.setBounds(240, 250, 150, 25);
		add(lblUnitPrice);

		unitPriceField = new JTextField();
		unitPriceField.setBounds(420, 250, 180, 25);
		add(unitPriceField);

		// Quantity
		JLabel lblQuantity = new JLabel("Quantity:");
		lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuantity.setBounds(240, 290, 150, 25);
		add(lblQuantity);

		quanField = new JTextField();
		quanField.setBounds(420, 290, 180, 25);
		add(quanField);

		// Batch No
		JLabel lblBatch = new JLabel("Batch No:");
		lblBatch.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBatch.setBounds(240, 330, 150, 25);
		add(lblBatch);

		batchField = new JTextField();
		batchField.setBounds(420, 330, 180, 25);
		add(batchField);

		// Expiry Date
		JLabel lblExpiry = new JLabel("Expiry Date:");
		lblExpiry.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblExpiry.setBounds(240, 370, 150, 25);
		add(lblExpiry);

		expiryField = new JTextField();
		expiryField.setBounds(420, 370, 180, 25);
		add(expiryField);

		// Error Label
		error = new JLabel("");
		error.setForeground(Color.RED);
		error.setBounds(320, 410, 300, 20);
		add(error);

		// Update Product Button
		btnUpdateProduct = new JButton("Update Product");
		btnUpdateProduct.setBounds(370, 450, 150, 35);
		btnUpdateProduct.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpdateProduct.setBackground(new Color(0, 150, 200));
		btnUpdateProduct.setForeground(Color.WHITE);
		btnUpdateProduct.setFocusPainted(false);
		btnUpdateProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(btnUpdateProduct);

		btnUpdateProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hsnField.getText().isEmpty() || quanField.getText().isEmpty()) {
					error.setText("HSN Code and Quantity are required.");
				} else {
					error.setText("");
					try {
						String hsn = hsnField.getText().trim();
						String name = nameField.getText().trim();
						String netWeight = netWeightBox.getSelectedItem().toString();
						String cat = category.getSelectedItem().toString();
						double unitPrice = Double.parseDouble(unitPriceField.getText().trim());
						int quantity = Integer.parseInt(quanField.getText().trim());
						String batch = batchField.getText().trim();
						String expiry = expiryField.getText().trim();

						// Call to DB to update product
						DB.updateProductToDB(hsn,name,netWeight,cat,unitPrice,quantity,batch,expiry
						);

						JOptionPane.showMessageDialog(null, "Product updated successfully!");

						// Clear fields
						hsnField.setText(""); nameField.setText(""); unitPriceField.setText("");
						quanField.setText(""); batchField.setText(""); expiryField.setText("");

					} catch (Exception ex) {
						error.setText("Invalid data: " + ex.getMessage());
					}
				}
			}
		});
	}

	private void refreshCategories() {
		category.removeAllItems();
		for (String cat : DB.getCategories()) {
			category.addItem(cat);
		}
	}
}
