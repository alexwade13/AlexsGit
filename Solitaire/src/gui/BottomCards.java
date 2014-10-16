package gui;

import java.awt.Color;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class BottomCards extends JPanel {
	public JPanel contentPane;

	public BottomCards() {
		super();
		JLayeredPane column0 = new JLayeredPane();
		column0.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.add(column0);

		JLayeredPane column1 = new JLayeredPane();
		this.add(column1);

		JLayeredPane column2 = new JLayeredPane();
		this.add(column2);

		JLayeredPane column3 = new JLayeredPane();
		this.add(column3);

		JLayeredPane column4 = new JLayeredPane();
		this.add(column4);

		JLayeredPane column5 = new JLayeredPane();
		this.add(column5);

		JLayeredPane column6 = new JLayeredPane();
		this.add(column6);
	}
}