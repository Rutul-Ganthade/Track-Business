import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import Style.RoundedTextfield;
import Style.RoundedComboBox;

public class addProduct extends JPanel {
	RoundedTextfield hsnField, nameField, unitPriceField, batchField, expiryField, quanField;
	RoundedComboBox netWeightBox, category;
	JButton addCategoryBtn, btnAddProduct;
	JLabel error;

	public addProduct() {
		setLayout(new BorderLayout());
		setBackground(new Color(245, 248, 250));

		JLabel lblTitle = new JLabel("ADD NEW PRODUCT", SwingConstants.CENTER);
		lblTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 30));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

		JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.setBackground(new Color(0, 153, 153));
		titlePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 20, 10, 20),
				BorderFactory.createBevelBorder(1, Color.DARK_GRAY, Color.GRAY)
		));
		titlePanel.add(lblTitle, BorderLayout.CENTER);
		add(titlePanel, BorderLayout.NORTH);

		JPanel outerPanel = new JPanel(new GridBagLayout());
		outerPanel.setBackground(new Color(255, 255, 255));

		TitledBorder border = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(0, 153, 153), 2, true),
				"  PRODUCT INFORMATION  "
		);
		border.setTitleFont(new Font("Segoe UI Black", Font.BOLD, 18));
		border.setTitleColor(new Color(0, 102, 102));
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.ABOVE_TOP);

		outerPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(20, 30, 20, 30),
				border
		));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(15, 30, 15, 30);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// ===== Field Row 1 =====
		hsnField = new RoundedTextfield("Enter HSN Code");
		nameField = new RoundedTextfield("Enter Product Name");
		addFormRow("HSN Code", hsnField, "Product Name", nameField, outerPanel, gbc, 0);

		// ===== Field Row 2 =====
		netWeightBox = new RoundedComboBox(new String[]{"gm", "kg", "ml", "litre", "piece"});
		unitPriceField = new RoundedTextfield("Enter Unit Price");
		addFormRow("Net Weight", netWeightBox, "Unit Price", unitPriceField, outerPanel, gbc, 1);

		// ===== Field Row 3 =====
		quanField = new RoundedTextfield("Enter Quantity");
		batchField = new RoundedTextfield("Enter Batch No");
		addFormRow("Quantity", quanField, "Batch No", batchField, outerPanel, gbc, 2);

		// ===== Field Row 4 =====
		expiryField = new RoundedTextfield("Enter Expiry Date");
		category = new RoundedComboBox(new String[]{}); // initially empty
		refreshCategories();

		addCategoryBtn = new JButton("+");
		addCategoryBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
		addCategoryBtn.setBackground(new Color(0, 153, 153));
		addCategoryBtn.setForeground(Color.WHITE);
		addCategoryBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		addCategoryBtn.setFocusPainted(false);
		addCategoryBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JPanel categoryPanel = new JPanel(new BorderLayout(5, 0));
		categoryPanel.setOpaque(false);
		categoryPanel.add(category, BorderLayout.CENTER);
		categoryPanel.add(addCategoryBtn, BorderLayout.EAST);

		addFormRow("Expiry Date", expiryField, "Category", categoryPanel, outerPanel, gbc, 3);

		// ===== Error Label =====
		error = new JLabel("", SwingConstants.CENTER);
		error.setForeground(Color.RED);
		error.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 4;
		outerPanel.add(error, gbc);

		// ===== Add Product Button =====
		btnAddProduct = new JButton("Add Product");
		btnAddProduct.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnAddProduct.setBackground(new Color(0, 153, 102));
		btnAddProduct.setForeground(Color.WHITE);
		btnAddProduct.setFocusPainted(false);
		btnAddProduct.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAddProduct.setPreferredSize(new Dimension(200, 45));

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));
		buttonPanel.setOpaque(false);
		buttonPanel.add(btnAddProduct);

		JPanel centerContainer = new JPanel(new BorderLayout());
		centerContainer.setOpaque(false);
		centerContainer.add(outerPanel, BorderLayout.CENTER);

		add(centerContainer, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		// ==== Actions ====
		addCategoryBtn.addActionListener(e -> {
			String newCat = JOptionPane.showInputDialog("Enter new category:");
			if (newCat != null && !newCat.trim().isEmpty()) {
				DB.addCategory(newCat.trim());
				refreshCategories();
			}
		});

		btnAddProduct.addActionListener(e -> {
			if (hsnField.getText().isEmpty() || quanField.getText().isEmpty()) {
				error.setText("HSN Code and Quantity are required.");
			} else {
				error.setText("");
				DB.addProduct(
						hsnField.getText().trim(),
						nameField.getText().trim(),
						netWeightBox.getSelectedItem().toString(),
						category.getSelectedItem().toString(),
						Double.parseDouble(unitPriceField.getText().trim()),
						Integer.parseInt(quanField.getText().trim()),
						batchField.getText().trim(),
						expiryField.getText().trim()
				);

				hsnField.setText("");
				nameField.setText("");
				unitPriceField.setText("");
				quanField.setText("");
				batchField.setText("");
				expiryField.setText("");
			}
		});
	}

	private void addFormRow(String label1, JComponent field1, String label2, JComponent field2,
							JPanel panel, GridBagConstraints gbc, int row) {
		gbc.gridy = row;

		gbc.gridx = 0;
		panel.add(createLabel(label1), gbc);
		gbc.gridx = 1;
		field1.setPreferredSize(new Dimension(240, 35));
		panel.add(field1, gbc);

		gbc.gridx = 2;
		panel.add(createLabel(label2), gbc);
		gbc.gridx = 3;
		field2.setPreferredSize(new Dimension(240, 35));
		panel.add(field2, gbc);
	}

	private JLabel createLabel(String text) {
		JLabel lbl = new JLabel(text);
		lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lbl.setForeground(new Color(50, 50, 50));
		return lbl;
	}

	private void refreshCategories() {
		category.removeAllItems();
		for (String cat : DB.getCategories()) {
			category.addItem(cat);
		}
	}
}
