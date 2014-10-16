/**
 * CSCI 205 - Software Design and Engineering
 * Names: Alex Wade, Anna Zeveney, JB Ring
 *
 * Team name: Chase's minions
 * Project: Solitaire
 */

package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import Model.Controller;

@SuppressWarnings("serial")
public class PlayingAreaView extends JLayeredPane {

	public final int NUM_PLAY_PILES = 7;
	public final int NUM_FINAL_PILES = 4;
	private static final int CARD_VGAP = 10;
	private final CardIcons deck;
	private final JPanel basePane;

	/**
	 * Create the frame.
	 */
	public PlayingAreaView() {
		this.setSize(1000, 1000);
		basePane = new JPanel(null);
		basePane.setSize(getPreferredSize());
		String path = "http://www3.delta.edu/bernadetteharkness/Ch4AtomicTheoryPart2/classic-playing-cards.png";
		this.deck = new CardIcons(path);

		basePane.setBorder(new LineBorder(new Color(0, 0, 0)));
		basePane.add(new JLabel("nothing is working"));
		add(basePane, JLayeredPane.DEFAULT_LAYER);

		drawEmptyPile(Controller.WASTE_PILE_POSITION);
		drawEmptyPile(Controller.STOCK_PILE_POSITION);
		for (int i = 0; i < NUM_FINAL_PILES; i++) {
			int x = Controller.FINAL_PILES_X + i
					* (Controller.CARD_HGAP + Controller.CARD_WIDTH);
			int y = Controller.CARD_VGAP;
			drawEmptyPile(new MyPosition(x, y));
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Controller.PREF_W, Controller.PREF_H);// PREF_W,PREF_H);
	}

	public void drawWasteCard(int cardNum) {
		if (getComponentAt(Controller.CARD_HGAP, CARD_VGAP) instanceof JLabel) {
			JLabel card = (JLabel) getComponentAt(Controller.CARD_HGAP,
					CARD_VGAP);
			card.setLocation(Controller.CARD_HGAP * 2 + Controller.CARD_WIDTH,
					CARD_VGAP);
			flipCard(card, cardNum);
		}

	}

	public void drawEmptyPile(MyPosition pilePosition) {

		JLabel empty = new JLabel();
		empty.setSize(Controller.CARD_WIDTH, Controller.CARD_HEIGHT);
		empty.setBorder(new LineBorder(Color.BLACK, 1));
		empty.setLocation((int) pilePosition.getX(), (int) pilePosition.getY());
		this.add(empty, JLayeredPane.MODAL_LAYER);

	}

	public void pickUpCard(Component component) {
		basePane.remove(component);

		refreshView();
		add(component, JLayeredPane.DRAG_LAYER);
		refreshView();
	}

	public void flipCard(JLabel card, int cardNum) {
		Icon icon = deck.get(cardNum);
		card.setIcon(icon);

	}

	public JPanel getBasePane() {
		return basePane;
	}

	public void drawFromGame() {

	}

	public void drawCardBack(MyPosition cardPosition) {
		Icon icon = deck.get(0);
		JLabel card = new JLabel(icon);
		card.setSize(new Dimension(Controller.CARD_WIDTH,
				Controller.CARD_HEIGHT));
		card.setLocation((int) cardPosition.getX(), (int) cardPosition.getY());
		basePane.add(card, 0);
		basePane.revalidate();
		basePane.repaint();

	}

	public void createCard(int cardNum, double x, double y) {
		Icon icon = deck.get(cardNum);
		JLabel card = new JLabel(icon);
		card.setSize(new Dimension(Controller.CARD_WIDTH,
				Controller.CARD_HEIGHT));
		card.setLocation((int) x, (int) y);
		basePane.add(card, 0);
		basePane.revalidate();
		basePane.repaint();

	}

	public void refreshView() {
		revalidate();
		repaint();
		basePane.revalidate();
		basePane.repaint();

	}

	public void dropCard(JLabel card) {
		this.remove(card);

	}

}
