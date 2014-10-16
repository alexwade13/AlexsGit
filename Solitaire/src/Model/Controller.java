/**
 * CSCI 205 - Software Design and Engineering
 * Names: Alex Wade, Anna Zeveney, JB Ring
 *
 * Team name: Chase's minions
 * Project: Solitaire
 */

package Model;

import gui.ControlPanel;
import gui.MyPosition;
import gui.PlayingAreaView;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * 
 * The controller that combines everything in the model and GUI to create the
 * game of Solitaire.
 * 
 */
public class Controller extends MouseAdapter implements ActionListener,
		MouseListener {

	/**
	 * the Enum for the differnt pile types, to be used for determining which
	 * pile was clicked or which pile is released upon
	 */
	public static enum PileType {
		STOCK, PLAYING, FINAL, WASTE, NEW;
	}

	private final Game theGame;
	private final PlayingAreaView theView;
	private final ControlPanel theControlPanel;

	public static final int CARD_WIDTH = 75;
	public static final int CARD_HEIGHT = 100;
	public static final int CARD_HGAP = 30;
	public static final int CARD_VGAP = 10;
	public static final int CARD_X_START = 5;
	public static final int CARD_VERTICAL_OFFSET = 30;

	public static final int BOTTOM_CARDS_Y_POSITION = CARD_HEIGHT + 2
			* CARD_VGAP;
	public static final int FINAL_PILES_X = 3 * CARD_WIDTH + 4 * CARD_HGAP;

	public static final MyPosition STOCK_PILE_POSITION = new MyPosition(
			CARD_HGAP, CARD_VGAP);
	public static final MyPosition WASTE_PILE_POSITION = new MyPosition(
			(CARD_HGAP * 2 + CARD_WIDTH), CARD_VGAP);
	private final ArrayList<Double> deltaXs = new ArrayList<>();
	private final ArrayList<Double> deltaYs = new ArrayList<>();
	private final ArrayList<JLabel> selectedCards = new ArrayList<>();

	private MyPosition lastClickLocation;
	private PileType lastClickPileType = PileType.NEW;
	private int numCardsSelected;
	public static final int PREF_H = (6 + 13) * CARD_VERTICAL_OFFSET + 2
			* CARD_HEIGHT;
	public static final int PREF_W = 7 * CARD_WIDTH + 8 * CARD_HGAP;

	/**
	 * 
	 * @param game
	 *            the model
	 * @param view
	 *            the view
	 */
	public Controller(Game game, PlayingAreaView view, ControlPanel controlPanel) {
		this.theGame = game;
		theGame.dealDeck();
		this.theView = view;
		drawGameInView();
		this.theControlPanel = controlPanel;

		this.theView.addMouseListener(this);
		this.theView.addMouseMotionListener(this);
		controlPanel.getNewGameButton().addActionListener(this);
	}

	/**
	 * redraws the game in the view
	 */
	private void drawGameInView() {
		drawWastePile();
		drawStockPile();

		for (int i = 0; i < theGame.NUM_FINAL_PILES; i++) {
			drawFinalPile(i);
		}

		for (int pileNum = 0; pileNum < theGame.NUM_PLAY_PILES; pileNum++) {
			drawPlayPile(pileNum);
		}
		theView.refreshView();

	}

	/**
	 * Draws a single PlayPile in given position
	 * 
	 * @param pileNum
	 *            given position
	 */
	private void drawPlayPile(int pileNum) {
		PlayPile pile = theGame.getThePlayPiles().get(pileNum);
		for (int i = 0; i < pile.size(); i++) {
			double x = pileNum * (CARD_HGAP + CARD_WIDTH) + CARD_HGAP;
			double y = BOTTOM_CARDS_Y_POSITION + (i * CARD_VERTICAL_OFFSET);
			theView.createCard(
					pile.get(pile.size() - i - 1).getCardNumInDeck(), x, y);
			theView.refreshView();
		}
	}

	/**
	 * Draws a single FinalPile in given position
	 * 
	 * @param pileNum
	 *            given position
	 */
	private void drawFinalPile(int pileNum) {
		FinalPile pile = theGame.getTheFinalPiles().get(pileNum);
		for (int i = 0; i < pile.size(); i++) {
			double x = pileNum * (CARD_HGAP + CARD_WIDTH) + FINAL_PILES_X;
			double y = CARD_VGAP;
			theView.createCard(pile.get(i).getCardNumInDeck(), x, y);
			theView.refreshView();
		}

	}

	@Override
	/**
	 * Overwrites the mousesPressed function to perform based on which pile was 
	 * clicked
	 * @param event 
	 */
	public void mousePressed(MouseEvent event) {
		selectedCards.clear();
		Component comp = theView.getBasePane().getComponentAt(event.getPoint());
		lastClickLocation = new MyPosition(event.getPoint().getX(), event
				.getPoint().getY());
		if (comp != null && comp instanceof JLabel) {
			if (isStockPileClick(lastClickLocation)) {
				lastClickPileType = PileType.STOCK;
				theGame.flipCard();

			} else if (isWastePileClick(lastClickLocation)) {
				wastePileClicked(comp);
			} else if (isFinalPileClick(lastClickLocation)) {
				finalPileClicked(comp);
			} else if (isBottomRowClick(lastClickLocation)) {
				playPileClicked(comp);
			}

		}

		drawGameInView();
	}

	/**
	 * @param comp
	 */
	private void playPileClicked(Component comp) {
		lastClickPileType = PileType.PLAYING;
		int playPileNumber = getPlayingPileNum(lastClickLocation);
		PlayPile playPile = theGame.getThePlayPiles().get(playPileNumber);
		if (!playPile.isEmpty()) {
			int cardIndexFromBottom = playPile.size()
					- getNumCardsFromTop(comp) - 1;
			Card selectedCard = playPile.get(cardIndexFromBottom);
			if (!selectedCard.isFaceUp() && (cardIndexFromBottom == 0)) {
				selectedCard.setFaceUp();
				theView.flipCard((JLabel) comp, selectedCard.getCardNumInDeck());
			} else if (selectedCard.isFaceUp()) {
				numCardsSelected = cardIndexFromBottom + 1;
				for (int i = 0; i < numCardsSelected; i++) {
					theGame.getCardsPickedUp().add(
							theGame.getThePlayPiles().get(playPileNumber)
									.remove(0));

				}
				pickUpCards(cardIndexFromBottom, comp);
			}
		}
		theView.getBasePane().removeAll();
	}

	/**
	 * @param comp
	 */
	private void finalPileClicked(Component comp) {
		lastClickPileType = PileType.FINAL;
		FinalPile finalPile = theGame.getTheFinalPiles().get(
				getFinalPileNum(lastClickLocation));
		if (!finalPile.isEmpty()) {
			pickUpCards(0, comp);
		}
	}

	/**
	 * @param comp
	 */
	private void wastePileClicked(Component comp) {
		numCardsSelected = 1;
		lastClickPileType = PileType.WASTE;
		if (!theGame.getTheWastePile().isEmpty()) {
			theGame.getCardsPickedUp().add(theGame.getTheWastePile().pop());
			pickUpCards(0, comp);
		}
	}

	/**
	 * 
	 */
	private void drawStockPile() {
		if (theGame.isStockPileEmpty()) {
			theView.drawEmptyPile(STOCK_PILE_POSITION);
		} else {
			StockPile pile = theGame.getTheStockPile();
			for (int k = 0; k < pile.size(); k++) {
				double x = STOCK_PILE_POSITION.getX();
				double y = STOCK_PILE_POSITION.getY();
				theView.createCard(pile.get(k).getCardNumInDeck(), x, y);
			}
		}
	}

	/**
	 * 
	 */
	private void drawWastePile() {
		if (theGame.isWastePileEmpty()) {
			theView.drawEmptyPile(WASTE_PILE_POSITION);
		} else {
			WastePile pile = theGame.getTheWastePile();
			for (int k = 0; k < pile.size(); k++) {
				double x = WASTE_PILE_POSITION.getX();
				double y = WASTE_PILE_POSITION.getY();
				theView.createCard(pile.get(k).getCardNumInDeck(), x, y);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		Component comp = theView.getBasePane().getComponentAt(event.getPoint());
		MyPosition currentClickPosition = new MyPosition(event.getPoint()
				.getX(), event.getPoint().getY());

		if (!selectedCards.isEmpty() && comp != null) {
			if (lastClickPileType == PileType.WASTE) {

				moveFromWastePile(currentClickPosition);
			} else if (lastClickPileType == PileType.PLAYING) {
				moveFromPlayingPile(currentClickPosition);
			}
			for (JLabel card : selectedCards) {
				theView.dropCard(card);
				theView.getBasePane().remove(card);
			}
			theView.getBasePane().removeAll();
			drawGameInView();
			selectedCards.clear();
			deltaXs.clear();
			deltaYs.clear();
			theGame.getCardsPickedUp().clear();
		}
		if (theGame.isWin()) {
			theGame.setNumberOfWins(theGame.getNumberOfWins() + 1);
			theControlPanel.getNumWins().setText(
					"Number of Wins:  " + theGame.getNumberOfWins());
			JOptionPane.showMessageDialog(null, "You Win ");
		}

	}

	/**
	 * @param currentClickPosition
	 */
	private void moveFromPlayingPile(MyPosition currentClickPosition) {
		int playPileNumber = getPlayingPileNum(lastClickLocation);
		for (int i = numCardsSelected; i > 0; i--) {
			try {
				theGame.getThePlayPiles().get(playPileNumber)
						.add(0, theGame.getCardsPickedUp().remove(i - 1));
			} catch (Exception e) {

			}
		}

		if (isBottomRowClick(currentClickPosition)) {
			int endPlayingPileNum = getPlayingPileNum(currentClickPosition);
			theGame.moveFromPlayingToPlaying(
					getPlayingPileNum(lastClickLocation), numCardsSelected,
					endPlayingPileNum);

		} else if (isFinalPileClick(currentClickPosition)
				&& numCardsSelected == 1) {
			theGame.moveFromPlayingToFinal(
					getPlayingPileNum(lastClickLocation),
					getFinalPileNum(currentClickPosition));
		}
	}

	/**
	 * @param currentClickPosition
	 */
	private void moveFromWastePile(MyPosition currentClickPosition) {
		theGame.getTheWastePile().add(theGame.getCardsPickedUp().remove(0));
		if (isBottomRowClick(currentClickPosition)) {
			int playingPileNum = getPlayingPileNum(currentClickPosition);
			if (theGame.moveFromWasteToPlaying(playingPileNum)) {
				double x = CARD_HGAP + playingPileNum
						* (CARD_WIDTH + CARD_HGAP);
				double y = BOTTOM_CARDS_Y_POSITION
						+ theGame.getThePlayPiles().get(playingPileNum).size()
						* CARD_VERTICAL_OFFSET;
				selectedCards.get(0).setLocation((int) x, (int) y);
			}

		} else if (isFinalPileClick(currentClickPosition)) {
			int finalPileNum = getFinalPileNum(currentClickPosition);
			if (theGame.moveFromWasteToFinal(finalPileNum)) {
				double x = FINAL_PILES_X + finalPileNum
						* (CARD_WIDTH + CARD_HGAP);
				double y = CARD_VGAP;
				selectedCards.get(0).setLocation((int) x, (int) y);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		if (!selectedCards.isEmpty()) {
			for (int i = 0; i < selectedCards.size(); i++) {
				int x = (int) (event.getX() - deltaXs.get(i));
				int y = (int) (event.getY() - deltaYs.get(i));
				selectedCards.get(i).setLocation(x, y);
				theView.refreshView();
			}
		}

	}

	/**
	 * called when the mouse leaves the bounds of the playing area, places the
	 * cards back in the pile that they came from
	 */
	@Override
	public void mouseExited(MouseEvent event) {
		if (lastClickPileType == PileType.WASTE) {
			try {
				theGame.getTheWastePile().add(
						theGame.getCardsPickedUp().remove(0));
			} catch (Exception e) {

			}
		} else if (lastClickPileType == PileType.PLAYING) {
			int playPileNumber = getPlayingPileNum(lastClickLocation);
			for (int i = selectedCards.size(); i > 0; i--) {
				try {
					theGame.getThePlayPiles().get(playPileNumber)
							.add(0, theGame.getCardsPickedUp().remove(i - 1));
				} catch (IndexOutOfBoundsException e) {
					return;
				}
			}

		}
		for (Component j : theView.getComponents()) {
			if (j instanceof JLabel) {
				if ((((JLabel) j).getBorder()) == null) {
					theView.remove(j);
				}

			}
		}
		theView.getBasePane().removeAll();
		drawGameInView();
	}

	private int getFinalPileNum(MyPosition clickPosition) {
		double x = clickPosition.getX();
		x -= (FINAL_PILES_X);
		return (int) (x / (CARD_HGAP + CARD_WIDTH));

	}

	private int getPlayingPileNum(MyPosition clickPosition) {
		double x = clickPosition.getX() - CARD_HGAP;
		return (int) (x / (CARD_HGAP + CARD_WIDTH));

	}

	private boolean isBottomRowClick(MyPosition clickPosition) {
		double y = clickPosition.getY();
		if (y > (BOTTOM_CARDS_Y_POSITION)) {
			return true;
		}
		return false;
	}

	private boolean isStockPileClick(MyPosition clickPosition) {
		double x = clickPosition.getX();
		double y = clickPosition.getY();
		if (x > (CARD_HGAP) && x < (CARD_HGAP + CARD_WIDTH)) {
			if (y < (BOTTOM_CARDS_Y_POSITION - CARD_VGAP) && y > CARD_VGAP) {
				return true;
			}
		}
		return false;
	}

	private boolean isFinalPileClick(MyPosition clickPosition) {
		double x = clickPosition.getX();
		double y = clickPosition.getY();
		if (x > (FINAL_PILES_X)) {
			if (y < (BOTTOM_CARDS_Y_POSITION - CARD_VGAP) && y > CARD_VGAP) {
				return true;
			}
		}

		return false;
	}

	private boolean isWastePileClick(MyPosition clickPosition) {
		double x = clickPosition.getX();
		double y = clickPosition.getY();
		if (x > (2 * CARD_HGAP + CARD_WIDTH)
				&& x < (2 * CARD_HGAP + 2 * CARD_WIDTH)) {
			if (y < (BOTTOM_CARDS_Y_POSITION - CARD_VGAP) && y > CARD_VGAP) {
				return true;
			}
		}
		return false;
	}

	public boolean isWithinPoints(int testPointX, int testPointY,
			int boundLowX, int boundHighX, int boundLowY, int boundHighY) {
		if (testPointX > boundLowX && testPointX < boundHighX
				&& testPointY > boundLowY && testPointY > boundHighY) {
			return true;
		}
		return false;
	}

	private int getNumCardsFromTop(Component comp) {
		double y = comp.getY();
		int cardFromTop = (int) ((y - BOTTOM_CARDS_Y_POSITION) / CARD_VERTICAL_OFFSET);
		return cardFromTop;
	}

	public void pickUpCards(int endIndex, Component comp) {

		for (int i = 0; i < numCardsSelected; i++) {
			JLabel currentComponent;
			currentComponent = (JLabel) comp;
			theView.pickUpCard(currentComponent);
			selectedCards.add(currentComponent);

			deltaXs.add(lastClickLocation.getX() - currentComponent.getX());
			deltaYs.add(lastClickLocation.getY() - currentComponent.getY());

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(theControlPanel.getNewGameButton())) {
			startNewGame();
		}

	}

	/**
	 * 
	 */
	private void startNewGame() {
		// TODO Auto-generated method stub
		theGame.setNumberOfLosses(theGame.getNumberOfLosses() + 1);
		theControlPanel.getWinPercentage().setText(
				"Win Percentage:  " + theGame.getWinPercentage() + "%");
		theView.getBasePane().removeAll();
		theGame.dealDeck();
		drawGameInView();

	}
}
