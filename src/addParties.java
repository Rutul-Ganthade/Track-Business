import Style.RoundedTextfield;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class addParties extends JPanel {

	JTextField partyNameField, gstNumberField, locationField;
	JButton btnAddParties;
	JLabel error;

	public addParties() {
		setLayout(new BorderLayout());
		setBackground(new Color(245, 248, 250)); // Light panel background

		// ====== Header Panel ======
		JLabel lblTitle = new JLabel("ADD PARTIES", SwingConstants.CENTER);
		lblTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 30));
		lblTitle.setForeground(Color.WHITE);

		JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.setBackground(new Color(0, 153, 153)); // #009999
		titlePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 20, 10, 20),
				BorderFactory.createBevelBorder(1, Color.DARK_GRAY, Color.GRAY)
		));
		titlePanel.add(lblTitle, BorderLayout.CENTER);
		add(titlePanel, BorderLayout.NORTH);

		// ====== Form Panel ======
		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setBackground(Color.WHITE);

		TitledBorder border = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(0, 153, 153), 2),
				"  PARTY DETAILS  ",
				TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP,
				new Font("Segoe UI Black", Font.BOLD, 18),
				new Color(0, 102, 102)
		);
		formPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(30, 50, 30, 50),
				border
		));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(20, 20, 20, 20);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// ====== Fields ======
		partyNameField = new RoundedTextfield("Party Name");
		gstNumberField = new RoundedTextfield("GST Number");
		locationField = new RoundedTextfield("Ex.Nagpur");

		addFormRow("Party Name", partyNameField, formPanel, gbc, 0);
		addFormRow("GST Number", gstNumberField, formPanel, gbc, 1);
		addFormRow("Party Location", locationField, formPanel, gbc, 2);

		// ====== Error Label ======
		error = new JLabel("", SwingConstants.CENTER);
		error.setForeground(Color.RED); // #FF0000
		error.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		formPanel.add(error, gbc);

		// ====== Add Button ======
		btnAddParties = new JButton("Add Party");
		btnAddParties.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnAddParties.setBackground(new Color(0, 153, 102)); // #009966
		btnAddParties.setForeground(Color.WHITE);
		btnAddParties.setFocusPainted(false);
		btnAddParties.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAddParties.setPreferredSize(new Dimension(200, 45));

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));
		buttonPanel.setOpaque(false);
		buttonPanel.add(btnAddParties);

		add(formPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		// ====== Action ======
		btnAddParties.addActionListener(e -> {
			String name = partyNameField.getText().trim();
			String gst = gstNumberField.getText().trim();
			String location = locationField.getText().trim();

			if (name.isEmpty() || gst.isEmpty() || location.isEmpty()) {
				error.setText("Please fill all fields");
			} else {
				error.setText("");
				DB.addParty(name, gst, location);
				JOptionPane.showMessageDialog(null, "Party added successfully!");
				partyNameField.setText("");
				gstNumberField.setText("");
				locationField.setText("");
			}
		});
	}

	private void addFormRow(String label, JTextField field, JPanel panel, GridBagConstraints gbc, int row) {
		gbc.gridy = row;

		gbc.gridx = 0;
		JLabel lbl = new JLabel(label);
		lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lbl.setForeground(new Color(50, 50, 50));
		panel.add(lbl, gbc);

		gbc.gridx = 1;
		field.setPreferredSize(new Dimension(240, 35));
		panel.add(field, gbc);
	}
}
