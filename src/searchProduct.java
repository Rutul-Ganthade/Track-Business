import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;

public class searchProduct extends JPanel {

	private JTextField idField;
	private JTextArea stockInfoArea;
	private JLabel error;
	private JPopupMenu suggestionPopup;

	public searchProduct() {
		setLayout(null);
		setBounds(100, 100, 840, 619);

		JLabel lblSearchProduct = new JLabel("SEARCH PRODUCT");
		lblSearchProduct.setBounds(319, 84, 182, 21);
		lblSearchProduct.setFont(new Font("Tahoma", Font.PLAIN, 17));
		add(lblSearchProduct);

		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProductName.setBounds(253, 156, 124, 21);
		add(lblProductName);

		idField = new JTextField();
		idField.setBounds(449, 158, 200, 20);
		add(idField);
		idField.setColumns(10);

		suggestionPopup = new JPopupMenu();

		idField.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				showSuggestions();
			}
			public void removeUpdate(DocumentEvent e) {
				showSuggestions();
			}
			public void changedUpdate(DocumentEvent e) {
				showSuggestions();
			}
		});

		stockInfoArea = new JTextArea();
		stockInfoArea.setEditable(false);
		stockInfoArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		JScrollPane scroll = new JScrollPane(stockInfoArea);
		scroll.setBounds(150, 300, 550, 200);
		add(scroll);

		error = new JLabel("");
		error.setForeground(Color.RED);
		error.setBounds(349, 277, 217, 14);
		add(error);
	}

	private void showSuggestions() {
		String input = idField.getText().trim();
		suggestionPopup.setVisible(false);
		suggestionPopup.removeAll();

		if (input.isEmpty()) return;

		List<String> matches = DB.getMatchingProductNames(input);
		if (matches.isEmpty()) return;

		for (String match : matches) {
			JMenuItem item = new JMenuItem(match);
			item.setFont(new Font("Tahoma", Font.PLAIN, 13));
			item.addActionListener(e -> {
				idField.setText(match);
				suggestionPopup.setVisible(false);
				showStockInfo(match);
			});
			suggestionPopup.add(item);
		}

		suggestionPopup.show(idField, 0, idField.getHeight());
	}

	private void showStockInfo(String productName) {
		String result = DB.getFullStockDetails(productName);
		if (result != null && !result.isEmpty()) {
			error.setText("");
			stockInfoArea.setText(result);
		} else {
			stockInfoArea.setText("");
			error.setText("Product not found in stock.");
		}
	}
}
