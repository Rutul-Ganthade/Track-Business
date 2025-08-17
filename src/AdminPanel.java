import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;


public class AdminPanel extends JFrame implements ActionListener {

	// Declare this at the top of the class
	CardLayout cardLayout;
	JPanel centerPanel;

	ArrayList<JPanel> panels=new ArrayList<JPanel>();

	public AdminPanel() {
		System.setProperty("sun.java2d.uiScale","1.0");
		setIconImage(Toolkit.getDefaultToolkit().getImage("/resources/logo.png"));
		setTitle("Admin Panel");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 650);
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		// Use BorderLayout for main content pane
		getContentPane().setLayout(new BorderLayout(0, 0));

		// -------------------- Top Panel: Logo + Title --------------------
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBackground(new Color(0, 123, 167));  // Modern blue tone
		topPanel.setPreferredSize(new Dimension(getWidth(), 80));
		topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));


		// Logo Label on left
		ImageIcon originalIcon = new ImageIcon(getClass().getResource("/resources/logo.png"));
		Image scaledImage = originalIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
		logoLabel.setPreferredSize(new Dimension(70, 70));
		logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		topPanel.add(logoLabel, BorderLayout.WEST);


		// Title label center
		JLabel titleLabel = new JLabel("EasyBusiness - Admin Panel");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 26));
		titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		topPanel.add(titleLabel, BorderLayout.CENTER);

		getContentPane().add(topPanel, BorderLayout.NORTH);

		// -------------------- Left Panel: Menu Bar vertically --------------------
		JPanel leftMenuPanel = new JPanel();
		leftMenuPanel.setLayout(new BoxLayout(leftMenuPanel, BoxLayout.Y_AXIS));
		leftMenuPanel.setBackground(new Color(230, 240, 250));
		leftMenuPanel.setPreferredSize(new Dimension(180, 1000)); // or even 1200
		leftMenuPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(200, 210, 220)));

		// Create menu buttons instead of JMenuBar for better UI
		// Keep same menu items grouped logically

		JButton btnProduct = new JButton("Product");
		btnProduct.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnProduct.setFocusPainted(false);
		btnProduct.setBackground(new Color(0, 123, 167));
		btnProduct.setForeground(Color.WHITE);
		btnProduct.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnProduct.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		leftMenuPanel.add(btnProduct);
		leftMenuPanel.add(Box.createVerticalStrut(5));

		// Sub-items for Product
		JButton btnAddProduct = new JButton("Add Product");
		btnAddProduct.setFocusable(true);
		JButton btnUpdateProduct = new JButton("Update Product");
		JButton btnDeleteProduct = new JButton("Delete Product");
		JButton[] productBtns = { btnAddProduct, btnUpdateProduct, btnDeleteProduct };
		for (JButton b : productBtns) {
			b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			b.setFocusPainted(false);
			b.setBackground(new Color(240, 245, 255));
			b.setAlignmentX(Component.CENTER_ALIGNMENT);
			b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
			leftMenuPanel.add(b);
			leftMenuPanel.add(Box.createVerticalStrut(3));
			b.addActionListener(this);
		}

		leftMenuPanel.add(Box.createVerticalStrut(15));

		JButton btnParties = new JButton("Parties");
		btnParties.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnParties.setFocusPainted(false);
		btnParties.setBackground(new Color(0, 123, 167));
		btnParties.setForeground(Color.WHITE);
		btnParties.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnParties.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		leftMenuPanel.add(btnParties);
		leftMenuPanel.add(Box.createVerticalStrut(5));

		JButton btnAddParty = new JButton("Add Party");
		JButton btnDeleteParty = new JButton("Delete Party");
		JButton[] partiesBtns = { btnAddParty, btnDeleteParty };
		for (JButton b : partiesBtns) {
			b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			b.setFocusPainted(false);
			b.setBackground(new Color(240, 245, 255));
			b.setAlignmentX(Component.CENTER_ALIGNMENT);
			b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
			leftMenuPanel.add(b);
			leftMenuPanel.add(Box.createVerticalStrut(3));
			b.addActionListener(this);
		}

		leftMenuPanel.add(Box.createVerticalStrut(15));

		// ---------------- Bill Feeding Section ----------------
		JButton btnBillFeeding = new JButton("Bill Feeding");
		btnBillFeeding.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnBillFeeding.setFocusPainted(false);
		btnBillFeeding.setBackground(new Color(0, 123, 167));
		btnBillFeeding.setForeground(Color.WHITE);
		btnBillFeeding.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnBillFeeding.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		leftMenuPanel.add(btnBillFeeding);
		leftMenuPanel.add(Box.createVerticalStrut(5));

		JButton btnPurchasedBill = new JButton("Purchased Bill Feed");
		btnPurchasedBill.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnPurchasedBill.setFocusPainted(false);
		btnPurchasedBill.setBackground(new Color(240, 245, 255));
		btnPurchasedBill.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPurchasedBill.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		btnPurchasedBill.addActionListener(this);
		leftMenuPanel.add(btnPurchasedBill);
		leftMenuPanel.add(Box.createVerticalStrut(5));

		JButton btnShowPurchasedBill = new JButton("Show Purchased Bill");
		btnShowPurchasedBill.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnShowPurchasedBill.setFocusPainted(false);
		btnShowPurchasedBill.setBackground(new Color(240, 245, 255));
		btnShowPurchasedBill.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnShowPurchasedBill.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		btnShowPurchasedBill.addActionListener(this);
		leftMenuPanel.add(btnShowPurchasedBill);
		leftMenuPanel.add(Box.createVerticalStrut(5));



		JButton btnInvoice = new JButton("Invoice");
		btnInvoice.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnInvoice.setFocusPainted(false);
		btnInvoice.setBackground(new Color(0, 123, 167));
		btnInvoice.setForeground(Color.WHITE);
		btnInvoice.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnInvoice.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		leftMenuPanel.add(btnInvoice);
		leftMenuPanel.add(Box.createVerticalStrut(5));

		JButton btnGenerateInvoice = new JButton("Generate Invoice");
		btnGenerateInvoice.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnGenerateInvoice.setFocusPainted(false);
		btnGenerateInvoice.setBackground(new Color(240, 245, 255));
		btnGenerateInvoice.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnGenerateInvoice.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		btnGenerateInvoice.addActionListener(this);
		leftMenuPanel.add(btnGenerateInvoice);
		leftMenuPanel.add(Box.createVerticalStrut(15));

		JButton btnStock = new JButton("Stock");
		btnStock.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnStock.setFocusPainted(false);
		btnStock.setBackground(new Color(0, 123, 167));
		btnStock.setForeground(Color.WHITE);
		btnStock.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnStock.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		leftMenuPanel.add(btnStock);
		leftMenuPanel.add(Box.createVerticalStrut(5));

		JButton btnShowStock = new JButton("Show Stock");
		btnShowStock.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnShowStock.setFocusPainted(false);
		btnShowStock.setBackground(new Color(240, 245, 255));
		btnShowStock.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnShowStock.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		btnShowStock.addActionListener(this);
		leftMenuPanel.add(btnShowStock);
		leftMenuPanel.add(Box.createVerticalStrut(15));

		JButton btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnSearch.setFocusPainted(false);
		btnSearch.setBackground(new Color(0, 123, 167));
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSearch.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		leftMenuPanel.add(btnSearch);
		leftMenuPanel.add(Box.createVerticalStrut(5));

		JButton btnSearchProduct = new JButton("Search Product");
		JButton[] searchBtns = { btnSearchProduct };
		for (JButton b : searchBtns) {
			b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			b.setFocusPainted(false);
			b.setBackground(new Color(240, 245, 255));
			b.setAlignmentX(Component.CENTER_ALIGNMENT);
			b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
			leftMenuPanel.add(b);
			leftMenuPanel.add(Box.createVerticalStrut(3));
			b.addActionListener(this);
		}

		leftMenuPanel.add(Box.createVerticalStrut(15));

		JButton btnSale = new JButton("Sale");
		btnSale.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnSale.setFocusPainted(false);
		btnSale.setBackground(new Color(0, 123, 167));
		btnSale.setForeground(Color.WHITE);
		btnSale.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSale.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		leftMenuPanel.add(btnSale);
		leftMenuPanel.add(Box.createVerticalStrut(5));

		JButton btnPrintSale = new JButton("Print Sale");
		btnPrintSale.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnPrintSale.setFocusPainted(false);
		btnPrintSale.setBackground(new Color(240, 245, 255));
		btnPrintSale.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPrintSale.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		btnPrintSale.addActionListener(this);
		leftMenuPanel.add(btnPrintSale);
		leftMenuPanel.add(Box.createVerticalStrut(15));

		JButton btnAccount = new JButton("Account");
		btnAccount.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnAccount.setFocusPainted(false);
		btnAccount.setBackground(new Color(0, 123, 167));
		btnAccount.setForeground(Color.WHITE);
		btnAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAccount.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		leftMenuPanel.add(btnAccount);
		leftMenuPanel.add(Box.createVerticalStrut(5));

		JButton btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnLogout.setFocusPainted(false);
		btnLogout.setBackground(new Color(240, 245, 255));
		btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLogout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		btnLogout.addActionListener(this);
		leftMenuPanel.add(btnLogout);

		// Wrap leftMenuPanel in JScrollPane
		JScrollPane scrollPane = new JScrollPane(leftMenuPanel) {
			@Override
			public void setVerticalScrollBar(JScrollBar sb) {
				// Disable vertical scrollbar visually but retain scrolling
				sb.setPreferredSize(new Dimension(0, 0)); // hide scrollbar completely
				super.setVerticalScrollBar(sb);
			}
		};

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(null);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);
		leftMenuPanel.setOpaque(false);

// Enable smooth mouse wheel scrolling
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);

// Add to content pane instead of leftMenuPanel directly
		scrollPane.setPreferredSize(new Dimension(180, getHeight()));
		getContentPane().add(scrollPane, BorderLayout.WEST);




		// -------------------- Center Panel: Main Content --------------------
		// Use CardLayout for panels switching if you want
		// -------------------- Center Panel: Main Content --------------------
		cardLayout = new CardLayout();
		centerPanel = new JPanel(cardLayout);
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		panels.add(new addProduct());     // 0
		panels.add(new updateProduct());  // 1
		panels.add(new deleteProduct());  // 2
		panels.add(new addParties());     // 3
		panels.add(new deleteParties());// 4
		panels.add(new Invoice());     //5
		panels.add(new showStock());      // 6
		panels.add(new searchProduct());  // 7
		panels.add(new Sale());           // 8
		panels.add(new PurchasedBillFeed()); // 9
		panels.add(new ShowPurchasedBill()); // 10



		for (int i = 0; i < panels.size(); i++) {
			centerPanel.add(panels.get(i), String.valueOf(i));
		}

		getContentPane().add(centerPanel, BorderLayout.CENTER);
		cardLayout.show(centerPanel, "0"); // Show first panel by default

	}


	private JButton createNavButton(String text, String iconPath) {
		JButton btn = new JButton(text);
		btn.setFocusPainted(false);
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		btn.setHorizontalAlignment(SwingConstants.LEFT);
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn.setBackground(new Color(45, 118, 232));
		btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
		btn.setOpaque(true);
		btn.setIcon(loadIcon(iconPath, 22, 22));
		btn.setIconTextGap(10);
		return btn;
	}

	private Icon loadIcon(String path, int width, int height) {
		try {
			ImageIcon icon = new ImageIcon(getClass().getResource(path));
			Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
			return new ImageIcon(img);
		} catch (Exception e) {
			// fallback: no icon
			return null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		switch (cmd) {
			case "Add Product":
				cardLayout.show(centerPanel, "0");
				break;
			case "Update Product":
				cardLayout.show(centerPanel, "1");
				break;
			case "Delete Product":
				cardLayout.show(centerPanel, "2");
				break;
			case "Add Party":
				cardLayout.show(centerPanel, "3");
				break;
			case "Delete Party":
				cardLayout.show(centerPanel, "4");
				break;
			case "Generate Invoice":
				cardLayout.show(centerPanel, "5");
				break;
			case "Show Stock":
				cardLayout.show(centerPanel, "6");
				break;
			case "Search Product":
				cardLayout.show(centerPanel, "7");
				break;
			case "Print Sale":
				cardLayout.show(centerPanel, "8");
				break;
			case "Purchased Bill Feed":
				cardLayout.show(centerPanel, "9");
				break;
			case "Show Purchased Bill":
				cardLayout.show(centerPanel, "10");
				break;


			case "Logout":
				dispose(); // close the admin panel
				break;
			default:
				break;
		}
	}

}
