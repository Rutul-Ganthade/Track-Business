import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class deleteParties extends JPanel {

	private JTextField partyNameField;
	private JLabel error;

	public deleteParties() {
		setLayout(null);
		setBounds(100, 100, 840, 619);

		JLabel lblDeleteParty = new JLabel("DELETE PARTY");
		lblDeleteParty.setBounds(328, 45, 182, 21);
		lblDeleteParty.setFont(new Font("Tahoma", Font.PLAIN, 17));
		add(lblDeleteParty);

		JLabel lblPartyName = new JLabel("Party Name");
		lblPartyName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPartyName.setBounds(246, 104, 124, 21);
		add(lblPartyName);

		partyNameField = new JTextField();
		partyNameField.setBounds(449, 106, 136, 20);
		add(partyNameField);
		partyNameField.setColumns(10);

		JButton btnDeleteParty = new JButton("Delete Party");
		btnDeleteParty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String partyName = partyNameField.getText().trim();

				if (partyName.equals("")) {
					error.setText("Please enter Party Name.");
				} else {
					error.setText("");
					DB.deleteParty(partyName);  // updated method
					partyNameField.setText("");
				}
			}
		});
		btnDeleteParty.setBounds(449, 150, 136, 23);
		add(btnDeleteParty);

		error = new JLabel("");
		error.setForeground(Color.RED);
		error.setBounds(277, 200, 300, 14);
		add(error);
	}
}