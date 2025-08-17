import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import Style.RoundedTextfield;

public class deleteProduct extends JPanel {

	RoundedTextfield hsnField;
	JButton btnDeleteProduct;
	JLabel error;
	JCheckBox cbStock, cbProduct;

	public deleteProduct() {
		setLayout(new BorderLayout());
		setBackground(new Color(245, 248, 250));

		// Title Panel
		JLabel lblTitle = new JLabel("DELETE PRODUCT", SwingConstants.CENTER);
		lblTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 30));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

		JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.setBackground(new Color(204, 0, 0));
		titlePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 20, 10, 20),
				BorderFactory.createBevelBorder(1, Color.DARK_GRAY, Color.GRAY)
		));
		titlePanel.add(lblTitle, BorderLayout.CENTER);
		add(titlePanel, BorderLayout.NORTH);

		// Outer Panel
		JPanel outerPanel = new JPanel(new GridBagLayout());
		outerPanel.setBackground(Color.WHITE);

		TitledBorder border = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(204, 0, 0), 2, true),
				"  PRODUCT DELETE  "
		);
		border.setTitleFont(new Font("Segoe UI Black", Font.BOLD, 18));
		border.setTitleColor(new Color(153, 0, 0));
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.ABOVE_TOP);

		outerPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(50, 80, 50, 80),
				border
		));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(15, 30, 15, 30);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// ===== HSN Code =====
		JLabel lblHSN = new JLabel("HSN Code");
		lblHSN.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblHSN.setForeground(new Color(50, 50, 50));
		gbc.gridx = 0;
		gbc.gridy = 0;
		outerPanel.add(lblHSN, gbc);

		hsnField = new RoundedTextfield("Enter HSN Code");
		hsnField.setPreferredSize(new Dimension(240, 35));
		gbc.gridx = 1;
		outerPanel.add(hsnField, gbc);

		// ===== Checkboxes =====
		JPanel checkboxPanel = new JPanel(new GridBagLayout());
		checkboxPanel.setOpaque(false);

		cbStock = new JCheckBox("Stock");
		cbProduct = new JCheckBox("Product");

		Dimension checkboxSize = new Dimension(240, 35);
		cbStock.setPreferredSize(checkboxSize);
		cbProduct.setPreferredSize(checkboxSize);

		cbStock.setFont(new Font("Segoe UI", Font.BOLD, 14));
		cbProduct.setFont(new Font("Segoe UI", Font.BOLD, 14));

		cbStock.setBackground(new Color(255, 255, 255));
		cbProduct.setBackground(new Color(255, 255, 255));

		cbStock.setFocusPainted(false);
		cbProduct.setFocusPainted(false);

		GridBagConstraints cbc = new GridBagConstraints();
		cbc.insets = new Insets(5, 0, 5, 0);
		cbc.gridx = 0;
		cbc.gridy = 0;
		checkboxPanel.add(cbStock, cbc);

		cbc.gridy = 1;
		checkboxPanel.add(cbProduct, cbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		outerPanel.add(checkboxPanel, gbc);

		// ===== Error Label =====
		error = new JLabel("", SwingConstants.CENTER);
		error.setForeground(Color.RED);
		error.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		outerPanel.add(error, gbc);

		// ===== Delete Button =====
		btnDeleteProduct = new JButton("Delete Product");
		btnDeleteProduct.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnDeleteProduct.setBackground(new Color(204, 0, 0));
		btnDeleteProduct.setForeground(Color.WHITE);
		btnDeleteProduct.setFocusPainted(false);
		btnDeleteProduct.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDeleteProduct.setPreferredSize(new Dimension(200, 45));
		btnDeleteProduct.setToolTipText("Deletes the product and/or stock with the given HSN code");

		// Hover effect (optional)
		btnDeleteProduct.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnDeleteProduct.setBackground(new Color(153, 0, 0));
			}

			public void mouseExited(MouseEvent e) {
				btnDeleteProduct.setBackground(new Color(204, 0, 0));
			}
		});

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));
		buttonPanel.setOpaque(false);
		buttonPanel.add(btnDeleteProduct);

		// Add panels to layout
		add(outerPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		// ===== Button Action =====
		btnDeleteProduct.addActionListener(e -> {
			String hsn = hsnField.getText().trim();

			if (hsn.isEmpty()) {
				error.setText("HSN Code is required.");
				return;
			}

			if (!cbStock.isSelected() && !cbProduct.isSelected()) {
				error.setText("Please select at least one option: Stock or Product.");
				return;
			}

			int confirm = JOptionPane.showConfirmDialog(
					this,
					"Are you sure you want to delete the selected entries?",
					"Confirm Delete",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE
			);

			if (confirm != JOptionPane.YES_OPTION) return;

			error.setText("");

			if (cbStock.isSelected()) {
				DB.deleteFromStock(hsn);  // must be defined in your DB class
			}

			if (cbProduct.isSelected()) {
				DB.deleteFromProduct(hsn);  // must be defined in your DB class
			}

			JOptionPane.showMessageDialog(this, "Product deleted successfully.");
			hsnField.setText("");
			cbStock.setSelected(false);
			cbProduct.setSelected(false);
		});
	}
}