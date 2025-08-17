import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class DB {

	private static final String URL = "jdbc:mysql://localhost:3306/easybusiness";
	private static final String USER = "root";
	private static final String PASS = "";

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  // MySQL Connector/J 8.x driver class
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "JDBC Driver not found: " + e.getMessage());
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}

	public static boolean verifyLogin(String email, String pass) {
		String query = "SELECT 1 FROM users WHERE Email = ? AND Password = ?";
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, email);
			stmt.setString(2, pass);
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}
	}

	public static void addProduct(String HsnCode, String Name, String NetWeight, String Category,
								  double UnitPrice, int Quantity, String BatchNo, String ExpiryDate) {

		// Insert only basic product details into products table
		String query = "INSERT INTO products (Hsn_code, Product_name, Net_weight, Category) VALUES (?, ?, ?, ?)";
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, HsnCode);
			stmt.setString(2, Name);
			stmt.setString(3, NetWeight);
			stmt.setString(4, Category);

			stmt.executeUpdate();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Database Error (products): " + e.getMessage());
		}

		// Full insert into stock table with price, quantity, batch, expiry
		String query1 = "INSERT INTO stock (Hsn_code, Product_name, Net_weight, Category, Unit_price, Quantity, Batch_no, Expiry_date) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query1)) {

			stmt.setString(1, HsnCode);
			stmt.setString(2, Name);
			stmt.setString(3, NetWeight);
			stmt.setString(4, Category);
			stmt.setDouble(5, UnitPrice);
			stmt.setInt(6, Quantity);
			stmt.setString(7, BatchNo);
			stmt.setString(8, ExpiryDate);

			stmt.executeUpdate();

			JOptionPane.showMessageDialog(null, "Product added successfully.");

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Database Error (stock): " + e.getMessage());
		}
	}



	public static void addCategory(String category) {
		String query = "INSERT INTO categories (name) VALUES (?)";
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, category);
			stmt.executeUpdate();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error adding category: " + e.getMessage());
		}
	}

	public static List<String> getCategories() {
		List<String> categories = new ArrayList<>();
		String query = "SELECT name FROM categories";

		try (Connection conn = getConnection();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				categories.add(rs.getString("name"));
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error fetching categories: " + e.getMessage());
		}
		return categories;
	}

	public static void addParty(String name, String gst, String location) {
		String query = "INSERT INTO Parties (PartyName, PartyGST_In_No, PartyLocation) VALUES (?, ?, ?)";
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, name);
			stmt.setString(2, gst);
			stmt.setString(3, location);
			int rows = stmt.executeUpdate();
			if (rows > 0) {
				JOptionPane.showMessageDialog(null, "Party added successfully.");
			} else {
				JOptionPane.showMessageDialog(null, "Failed to add party.");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public static void deleteParty(String partyName) {
		String query = "DELETE FROM Parties WHERE PartyName = ?";
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, partyName);
			int status = stmt.executeUpdate();

			if (status > 0) {
				JOptionPane.showMessageDialog(null, "Party deleted successfully.");
			} else {
				JOptionPane.showMessageDialog(null, "Party not found!");
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
		}
	}

	public static void deleteFromStock(String hsn) {
		String query = "DELETE FROM stock WHERE Hsn_code = ?";
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, hsn);
			stmt.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
		}
	}
	public static void deleteFromProduct(String hsn) {
		String query = "DELETE FROM products WHERE Hsn_code = ?";
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, hsn);
			stmt.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
		}
	}



	public static void updateProductToDB(String hsnCode, String productName, String netWeight, String category,
										 double unitPrice, int quantity, String batchNo, String expiryDate) {
		String query = "UPDATE stock SET Product_name=?, Net_weight=?, Category=?, Unit_price=?, Quantity=?, Batch_no=?, Expiry_date=? WHERE Hsn_code=?";
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, productName);
			stmt.setString(2, netWeight);
			stmt.setString(3, category);
			stmt.setDouble(4, unitPrice);
			stmt.setInt(5, quantity);
			stmt.setString(6, batchNo);
			stmt.setString(7, expiryDate);
			stmt.setString(8, hsnCode);

			int updated = stmt.executeUpdate();
			if (updated > 0) {
				JOptionPane.showMessageDialog(null, "Product updated successfully.");
			} else {
				JOptionPane.showMessageDialog(null, "No matching product found to update.");
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Database Error (update): " + e.getMessage());
		}
	}


	public static ArrayList<String> getAllCategories() {
		ArrayList<String> categoryList = new ArrayList<>();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = DB.getConnection(); // Your existing method to get DB connection
			String query = "SELECT DISTINCT category FROM stock ORDER BY category ASC";
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();

			while (rs.next()) {
				String category = rs.getString("category");
				if (category != null && !category.trim().isEmpty()) {
					categoryList.add(category);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {}
			try { if (pst != null) pst.close(); } catch (Exception e) {}
			try { if (con != null) con.close(); } catch (Exception e) {}
		}

		return categoryList;
	}



	public static void deleteProductToDB(String hsn) {

	}


	// Removed duplicate varifyLogin method, use verifyLogin above instead.

	public static void insertBill(String partyName, String invoiceNo, String paymentType,
								  String hsn, String productName, String netWeight, String category,
								  double unitPrice, int quantity, String batchNo, String expiryDate) throws SQLException {

		Connection conn = getConnection();  // Your existing DB connection method
		try {
			// 1. Insert into bills table if not already present
			String billQuery = "INSERT INTO bills (Invoice_no, Party_name, Payment_type) " +
					"SELECT ?, ?, ? FROM DUAL WHERE NOT EXISTS " +
					"(SELECT 1 FROM bills WHERE Invoice_no = ?)";
			PreparedStatement pstBill = conn.prepareStatement(billQuery);
			pstBill.setString(1, invoiceNo);
			pstBill.setString(2, partyName);
			pstBill.setString(3, paymentType);
			pstBill.setString(4, invoiceNo);
			pstBill.executeUpdate();

			// 2. Insert product into bill_items table
			String itemQuery = "INSERT INTO bill_items (Invoice_no, Hsn_code, Product_name, Net_weight, " +
					"Category, Unit_price, Quantity, Batch_no, Expiry_date) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstItem = conn.prepareStatement(itemQuery);
			pstItem.setString(1, invoiceNo);
			pstItem.setString(2, hsn);
			pstItem.setString(3, productName);
			pstItem.setString(4, netWeight);
			pstItem.setString(5, category);
			pstItem.setDouble(6, unitPrice);
			pstItem.setInt(7, quantity);
			pstItem.setString(8, batchNo);
			pstItem.setString(9, expiryDate);
			pstItem.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error storing bill: " + e.getMessage());
		}
	}


	public static StockInfo getStockInfo(String hsn) throws SQLException {
		String query = "SELECT Unit_price, Quantity FROM stock WHERE Hsn_code = ?";
		try (Connection conn = getConnection();
			 PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, hsn);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new StockInfo(rs.getDouble("Unit_price"), rs.getInt("Quantity"));
				}
			}
		}
		return null;
	}

	public static List<String> getMatchingProductNames(String prefix) {
		List<String> results = new ArrayList<>();
		try (Connection conn = getConnection();
			 PreparedStatement ps = conn.prepareStatement(
					 "SELECT DISTINCT Product_name FROM stock WHERE Product_name LIKE ?")) {
			ps.setString(1, prefix + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				results.add(rs.getString("Product_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}


	public static String getFullStockDetails(String productName) {
		StringBuilder result = new StringBuilder();
		try (Connection conn = getConnection();
			 PreparedStatement ps = conn.prepareStatement(
					 "SELECT Hsn_code, Product_name, Net_weight, Category, Unit_price, Quantity, Batch_no, Expiry_date " +
							 "FROM stock WHERE Product_name = ?")) {
			ps.setString(1, productName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result.append("HSN Code: ").append(rs.getString("Hsn_code")).append("\n")
						.append("Product Name: ").append(rs.getString("Product_name")).append("\n")
						.append("Net Weight: ").append(rs.getString("Net_weight")).append("\n")
						.append("Category: ").append(rs.getString("Category")).append("\n")
						.append("Unit Price: ").append(rs.getDouble("Unit_price")).append("\n")
						.append("Quantity: ").append(rs.getInt("Quantity")).append("\n")
						.append("Batch No: ").append(rs.getString("Batch_no")).append("\n")
						.append("Expiry Date: ").append(rs.getString("Expiry_date")).append("\n\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}



	public static String validateStockAndFetchNetweight(String hsn, int requestedQty) throws SQLException {
		String query = "SELECT p.Net_weight, s.Quantity FROM products p JOIN stock s ON p.Hsn_code = s.Hsn_code WHERE p.Hsn_code = ?";
		try (Connection conn = getConnection();
			 PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, hsn);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String netWeight = rs.getString("Net_weight");
					int availableQty = rs.getInt("Quantity");
					if (availableQty >= requestedQty) {
						return netWeight + "%" + availableQty;
					} else {
						return "item is out of stock";
					}
				} else {
					return "nill";
				}
			}
		}
	}



	public static void reduceStock(String hsn, int qty) throws SQLException {
		String query = "UPDATE stock SET Quantity = Quantity - ? WHERE Hsn_code = ?";
		try (Connection conn = getConnection();
			 PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, qty);
			ps.setString(2, hsn);
			ps.executeUpdate();
		}
	}


	public static void increaseStock(String hsn, int qty) throws SQLException {
		String query = "UPDATE stock SET Quantity = Quantity + ? WHERE Hsn_code = ?";
		try (Connection conn = getConnection();
			 PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, qty);
			ps.setString(2, hsn);
			ps.executeUpdate();
		}
	}


	public static ProductInfo getProductDetailsByName(String name) throws SQLException {
		String query = "SELECT Hsn_code, Net_weight FROM products WHERE Product_name = ?";
		try (Connection conn = getConnection();
			 PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, name);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new ProductInfo(rs.getString("Hsn_code"), rs.getString("Net_weight"));
				}
			}
		}
		return null;
	}






	public static ArrayList<String> getSale(String date, String comp) {
		ArrayList<String> r = new ArrayList<>();
		String query;
		if ("All".equalsIgnoreCase(comp)) {
			query = "SELECT Date, Hsn_code, Company, Quantity, Payment FROM sale WHERE Date = ?";
		} else {
			query = "SELECT Date, Hsn_code, Company, Quantity, Payment FROM sale WHERE Date = ? AND Company = ?";
		}
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, date);
			if (!"All".equalsIgnoreCase(comp)) {
				stmt.setString(2, comp);
			}
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					r.add(rs.getString("Date"));
					r.add(rs.getString("Hsn_code"));
					r.add(rs.getString("Company"));
					r.add(String.valueOf(rs.getInt("Quantity")));
					r.add(rs.getString("Payment"));
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return r;
	}

	public static ArrayList<String> showStock(String comp) {
		ArrayList<String> r = new ArrayList<>();
		String query;

		if ("All".equalsIgnoreCase(comp)) {
			query = "SELECT Hsn_code, Product_name, Net_weight, Category, Unit_price, Quantity, Batch_no, Expiry_date FROM stock";
		} else {
			query = "SELECT Hsn_code, Product_name, Net_weight, Category, Unit_price, Quantity, Batch_no, Expiry_date FROM stock WHERE Category = ?";
		}

		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {

			if (!"All".equalsIgnoreCase(comp)) {
				stmt.setString(1, comp);
			}

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					r.add(rs.getString("Hsn_code"));
					r.add(rs.getString("Product_name"));
					r.add(rs.getString("Net_weight"));
					r.add(rs.getString("Category"));
					r.add(String.valueOf(rs.getDouble("Unit_price")));
					r.add(String.valueOf(rs.getInt("Quantity")));
					r.add(rs.getString("Batch_no"));
					r.add(rs.getString("Expiry_date"));
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return r;
	}

	public static ArrayList<String> getAllProductNames() {
		ArrayList<String> list = new ArrayList<>();
		try (Connection conn = getConnection();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT Product_name FROM products")) {

			while (rs.next()) {
				list.add(rs.getString("Product_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}



	public static void saveSale(int invoiceNo, String customer, String hsn, String netWeight, double unitPrice, int quantity, double finalPrice) throws SQLException {
		Connection conn = getConnection();
		try {
			String q = "INSERT INTO sales (InvoiceNo, CustomerName, Hsn_code, Netweight, Unit_price, Quantity, Final_price) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(q);
			ps.setInt(1, invoiceNo);
			ps.setString(2, customer);
			ps.setString(3, hsn);
			ps.setString(4, netWeight);
			ps.setDouble(5, unitPrice);
			ps.setInt(6, quantity);
			ps.setDouble(7, finalPrice);
			ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}




}