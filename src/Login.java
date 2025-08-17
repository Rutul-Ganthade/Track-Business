import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

import Style.RoundButton;
import Style.RoundPasswordField;
import Style.RoundedTextfield;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

public class Login extends JFrame {
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel errorLabel;
	private JButton btnLogin;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(new FlatMacLightLaf());
				new Login().setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public Login() {
		setTitle("EasyBusiness Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 420);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon(getClass().getResource("/resources/login_icon.png")).getImage());

		contentPane = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				Color color1 = new Color(240, 245, 255);
				Color color2 = new Color(224, 255, 250);
				GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		setContentPane(contentPane);

		// LEFT PANEL - Logo
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(260, getHeight()));
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setOpaque(false);

		ImageIcon originalIcon = new ImageIcon(getClass().getResource("/resources/logolable.png"));
		JLabel logoLabel = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(originalIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		leftPanel.add(logoLabel, BorderLayout.CENTER);

		// RIGHT PANEL - Login Form using GridBagLayout
		JPanel rightPanel = new JPanel(new GridBagLayout());
		rightPanel.setOpaque(false);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(15, 0, 15, 0);
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;

		// Username Field
		usernameField = new RoundedTextfield("Username");
		usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		usernameField.setPreferredSize(new Dimension(200, 30));
		usernameField.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 255)));
		usernameField.setBackground(Color.WHITE);
		usernameField.setMargin(new Insets(4, 10, 4, 10));  // Padding inside field
		gbc.gridy = 0;
		rightPanel.add(usernameField, gbc);

		// Password Field
		passwordField = new RoundPasswordField("Password");
		passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		passwordField.setPreferredSize(new Dimension(200, 30));
		passwordField.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 255)));
		passwordField.setBackground(Color.WHITE);
		passwordField.setMargin(new Insets(4, 10, 4, 10));  // Padding inside field
		gbc.gridy = 1;
		rightPanel.add(passwordField, gbc);

		// Error Label
		errorLabel = new JLabel("");
		errorLabel.setForeground(new Color(220, 20, 60));
		errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		errorLabel.setPreferredSize(new Dimension(280, 20));
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gbc.gridy = 2;
		rightPanel.add(errorLabel, gbc);

		// Login Button
		btnLogin = new RoundButton("Login");
		btnLogin.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
		btnLogin.setBackground(new Color(0, 200, 180));
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setPreferredSize(new Dimension(120, 40));
		btnLogin.setFocusPainted(false);
		btnLogin.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		gbc.gridy = 3;
		rightPanel.add(btnLogin, gbc);

		// Add listeners
		btnLogin.addActionListener(e -> handleLogin());
		passwordField.addActionListener(e -> btnLogin.doClick());

		// Assemble UI
		contentPane.add(leftPanel, BorderLayout.WEST);
		contentPane.add(rightPanel, BorderLayout.CENTER);

		SwingUtilities.invokeLater(() -> {
			getRootPane().requestFocusInWindow();
		});
	}

	private void handleLogin() {
		String username = usernameField.getText().trim().toLowerCase();
		String password = new String(passwordField.getPassword());

		if (username.isEmpty() || password.isEmpty()) {
			errorLabel.setText("Please fill in both username and password.");
			return;
		}

		errorLabel.setText("");
		if (DB.verifyLogin(username, password)) {
			if (username.equals("admin")) {
				new AdminPanel().setVisible(true);
			} else {
				new generateInvoice().setVisible(true);
			}
			dispose();
		} else {
			errorLabel.setText("Invalid username or password!");
			passwordField.setText("");
		}
	}
}
