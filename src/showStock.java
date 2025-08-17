import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class showStock extends JPanel {
	private JTable stockTable;
	private JComboBox<String> categoryComboBox;
	private DefaultTableModel model;

	public showStock() {
		System.out.println("showStock constructor called.");

		setLayout(null);
		setBounds(100, 100, 1000, 650);

		JLabel lblStock = new JLabel("AVAILABLE STOCK");
		lblStock.setBounds(420, 26, 182, 21);
		lblStock.setFont(new Font("Tahoma", Font.PLAIN, 17));
		add(lblStock);

		model = new DefaultTableModel();
		stockTable = new JTable(model);
		stockTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		model.addColumn("HSN Code");
		model.addColumn("Product Name");
		model.addColumn("Net Weight");
		model.addColumn("Category");
		model.addColumn("Unit Price");
		model.addColumn("Quantity");
		model.addColumn("Batch No");
		model.addColumn("Expiry Date");

		JScrollPane scroll = new JScrollPane(stockTable);
		scroll.setBounds(30, 112, 940, 397);
		add(scroll);

		categoryComboBox = new JComboBox<>();
		categoryComboBox.setBackground(Color.WHITE);
		categoryComboBox.setBounds(780, 81, 160, 20);
		add(categoryComboBox);

		JLabel lblCategory = new JLabel("Category");
		lblCategory.setBounds(780, 60, 161, 14);
		add(lblCategory);

		loadCategories();

		categoryComboBox.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				System.out.println("Category changed to: " + e.getItem());
				updateTable();
			}
		});

		JButton btnExportToExcel = new JButton("Export to Excel");
		btnExportToExcel.addActionListener(e -> {
			toExcel(stockTable, new File("availableStock.xls"));
			JOptionPane.showMessageDialog(null, "Export file created");
		});
		btnExportToExcel.setBounds(810, 525, 138, 23);
		add(btnExportToExcel);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(e -> {
			System.out.println("Refresh button clicked.");
			updateTable();
		});
		btnRefresh.setBounds(650, 525, 138, 23);
		add(btnRefresh);

		System.out.println("showStock UI initialized.");
	}

	private void loadCategories() {
		System.out.println("loadCategories() called.");
		categoryComboBox.removeAllItems();
		categoryComboBox.addItem("All");

		ArrayList<String> categories = DB.getAllCategories();
		if (categories == null) {
			System.out.println("Categories returned null.");
			return;
		}

		System.out.println("Categories loaded: " + categories.size());
		for (String category : categories) {
			System.out.println("Adding category: " + category);
			categoryComboBox.addItem(category);
		}
	}

	public void updateTable() {
		model.setRowCount(0);
		String selectedCategory = (String) categoryComboBox.getSelectedItem();
		System.out.println("updateTable() called. Selected category: " + selectedCategory);

		ArrayList<String> stock = DB.showStock(selectedCategory);
		if (stock == null) {
			System.out.println("Stock returned null.");
			return;
		}
		System.out.println("Stock items returned: " + stock.size());

		for (int x = 0; x < stock.size(); x += 8) {
			System.out.println("Adding row: " + stock.subList(x, Math.min(x + 8, stock.size())));
			model.addRow(new Object[]{
					stock.get(x), stock.get(x + 1), stock.get(x + 2), stock.get(x + 3),
					stock.get(x + 4), stock.get(x + 5), stock.get(x + 6), stock.get(x + 7)
			});
		}
	}

	public void toExcel(JTable table, File file) {
		try (FileWriter excel = new FileWriter(file)) {
			TableModel model = table.getModel();
			for (int i = 0; i < model.getColumnCount(); i++) {
				excel.write(model.getColumnName(i) + "\t");
			}
			excel.write("\n");

			for (int i = 0; i < model.getRowCount(); i++) {
				for (int j = 0; j < model.getColumnCount(); j++) {
					excel.write(model.getValueAt(i, j).toString() + "\t");
				}
				excel.write("\n");
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@Override
	public void setVisible(boolean aFlag) {
		super.setVisible(aFlag);
		System.out.println("showStock visibility set to: " + aFlag);
		if (aFlag) {
			updateTable();
		}
	}
}
