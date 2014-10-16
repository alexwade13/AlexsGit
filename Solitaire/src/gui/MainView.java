package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class MainView extends JLayeredPane {
	public static final int CARD_WIDTH = 100;
	public static final int CARD_HEIGHT = 300;
	public static final int CARD_HGAP = 10;
	public static final int CARD_VERTICAL_OFFSET = 30;
	private static final int PREF_W = 600;
	private static final int PREF_H = 400;
	private static final int CARD_VGAP = 10;
	private JPanel contentPane;
	private MyPosition initialClick;
	private final MyMouseAdapter mouseAdapter;
	private final Deck deck;
	private final JPanel basePane;
	private Icon emptyImg;

	/**
	 * Create the frame.
	 */
	public MainView() {
		basePane = new JPanel(null);
		basePane.setSize(getPreferredSize());
		String path = "http://www.jfitz.com/cards/classic-playing-cards.png";
		this.deck = new Deck(path);
		for (JLabel c : deck) {
			basePane.add(c);
		}
		basePane.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(basePane, JLayeredPane.DEFAULT_LAYER);
		// contentPane.add(layeredPane, BorderLayout.NORTH);
		// contentPane.add(basePane);

		this.mouseAdapter = new MyMouseAdapter(this, basePane);
		// addMouseListener(mouseAdapter);
		// addMouseMotionListener(mouseAdapter);

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}

	public void drawWasteCard(String string) {
		JLabel card = (JLabel) getComponentAt(CARD_HGAP, CARD_VGAP);
		card.setLocation(CARD_HGAP * 2 + CARD_WIDTH, CARD_VGAP);
		flipCard(card, string);

	}

	public void drawStockCard(Object pictureAddress) {
		// TODO Auto-generated method stub

	}

	public void drawEmptyPile(MyPosition pilePosition) {
		JLabel emptyPile = new JLabel(emptyImg);
		emptyPile.setLocation((int) pilePosition.getX(),
				(int) pilePosition.getY());

	}

	public void pickUpCard(Component component) {
		basePane.remove(component);
		basePane.revalidate();
		basePane.repaint();
		add(component, JLayeredPane.DRAG_LAYER);
		revalidate();
		repaint();
	}

	public void flipCard(JLabel card, String address) {
		// create image from address
		// card.setIcon(image);

	}

	public JPanel getBasePane() {
		return basePane;
	}

	public void drawFromGame() {
		// TODO Auto-generated method stub
	}

	public void drawCardBack(MyPosition cardPosition) {
		// TODO Auto-generated method stub

	}

}
