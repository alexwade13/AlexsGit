package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class TopCardsPanel extends JPanel {
	public JPanel contentPane;

	public TopCardsPanel() {
		super();

		JLabel lblStock = new JLabel("Stock");
		this.add(lblStock);

		JLabel lblWaste = new JLabel("Waste");
		this.add(lblWaste);

		JSeparator separator = new JSeparator();
		this.add(separator);

		JLabel final0 = new JLabel("");
		this.add(final0);

		JLabel final1 = new JLabel("");
		this.add(final1);

		JLabel final2 = new JLabel("");
		this.add(final2);

		JLabel final3 = new JLabel("");
		this.add(final3);
	}
}
