/**
 * CSCI 205 - Software Design and Engineering
 * Names: Alex Wade, Anna Zeveney, JB Ring
 *
 * Team name: Chase's minions
 * Project: Solitaire
 */

package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Model.Card.Num;
import Model.Card.Suit;

/**
 * the game class, that acts as the model to the game of Solitaire
 * 
 */
public class theGame {

	private final StockPile theStockPile;
	private WastePile theWastePile;
	private final ArrayList<FinalPile> theFinalPiles;
	private final ArrayList<PlayPile> thePlayPiles;
	public final int NUM_PLAY_PILES = 7;
	public final int NUM_FINAL_PILES = 4;
	public int numberOfWins = 0;
	public int numberOfLosses = 0;
	public double winPercentage = 0.1;

	/**
	 * 
	 */
	public theGame() {

		this.theStockPile = new StockPile();
		this.theWastePile = new WastePile();
		this.theFinalPiles = new ArrayList<FinalPile>();
		for (int i = 0; i < this.NUM_FINAL_PILES; i++) {
			this.theFinalPiles.add(new FinalPile());
		}

		this.thePlayPiles = new ArrayList<PlayPile>();

		for (int i = 0; i < this.NUM_PLAY_PILES; i++) {
			this.thePlayPiles.add(new PlayPile());
		}
	}

	/**
	 * FlipCard puts one card from the top of the Stock pile into the top of the
	 * WastPile
	 * 
	 */
	public void flipCard() {
		if (!this.theStockPile.isEmpty()) {
			Card card = this.theStockPile.pop();
			card.setFaceUp();
			this.theWastePile.push(card);

		} else {
			while (!this.theWastePile.isEmpty()) {
				Card card = this.theWastePile.pop();
				card.setFaceDown();
				this.theStockPile.push(card);
			}
		}
	}

	/**
	 * @param endCol
	 * @return boolean: true if success, false if failure Moves one card from
	 *         waste to a specifiued Final pile
	 */
	public boolean moveFromWasteToFinal(int endCol) {
		Card card = this.theWastePile.pop();
		if (putCardInFinal(endCol, card)) {
			return true;
		}
		this.theWastePile.push(card);
		return false;

	}

	/**
	 * @param endCol
	 * @return boolean: true if success, false if failure
	 */
	public boolean moveFromWasteToPlaying(int endCol) {
		Card card = this.theWastePile.pop();
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(card);

		if (putCardsInPlaying(endCol, cards)) {

			return true;

		}
		this.theWastePile.push(card);
		return false;
	}

	/**
	 * @param startCol
	 * @param endCol
	 * @return boolean: true if success, false if failure
	 */
	public boolean moveFromPlayingToFinal(int startCol, int endCol) {

		ArrayList<Card> cards = getCardsFromPlaying(startCol, 1);

		if (putCardInFinal(endCol, cards.get(0))) {

			return true;
		}
		this.thePlayPiles.get(startCol).add(0, cards.get(0));

		return false;
	}

	/**
	 * @param startCol
	 * @param numCards
	 * @param endCol
	 * @return boolean: true if success, false if failure
	 */
	public boolean moveFromPlayingToPlaying(int startCol, int numCards,
			int endCol) {
		ArrayList<Card> cards = getCardsFromPlaying(startCol, numCards);
		if (putCardsInPlaying(endCol, cards)) {
			return true;
		}

		for (int i = cards.size() - 1; i > -1; i--) {
			this.thePlayPiles.get(startCol).add(0, cards.get(i));
		}

		return false;
	}

	/**
	 * @param startCol
	 * @param numCards
	 * @return an ArrayList of the cards that the user will select
	 */
	private ArrayList<Card> getCardsFromPlaying(int startCol, int numCards) {
		List<Card> subList = this.thePlayPiles.get(startCol).subList(0,
				numCards);
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.addAll(subList);
		// Sublist in arraylist class returns list so change it bak to play pile
		subList = this.thePlayPiles.get(startCol).subList(numCards,
				this.thePlayPiles.get(startCol).size());

		PlayPile set = new PlayPile();
		set.addAll(subList);
		this.thePlayPiles.set(startCol, set);

		return cards;
	}

	/**
	 * @param endCol
	 * @param card
	 * @return boolean: true if success, false if failure
	 */
	private boolean putCardInFinal(int endCol, Card card) {

		if ((this.theFinalPiles.get(endCol).isEmpty() && card.getTheNum() == Card.Num.ACE)
				|| (!this.theFinalPiles.get(endCol).isEmpty()
						&& card.getTheSuit() == this.theFinalPiles.get(endCol)
								.peek().getTheSuit() && card.getTheNum()
						.getValue() == this.theFinalPiles.get(endCol).peek()
						.getTheNum().getValue() + 1)) {
			this.theFinalPiles.get(endCol).push(card);
			return true;
		}
		return false;
	}

	/**
	 * @param endCol
	 * @param cards
	 * @return boolean: true if success, false if failure
	 */
	private boolean putCardsInPlaying(int endCol, ArrayList<Card> cards) {

		Card topCard = cards.get(cards.size() - 1);
		if (this.thePlayPiles.get(endCol).isEmpty()) {
			if (topCard.getTheNum() == Card.Num.KING) {
				for (int i = cards.size() - 1; i > -1; i--) {
					this.thePlayPiles.get(endCol).add(0, cards.get(i));

				}
				return true;

			}
			return false;
		}
		Card endCard = this.thePlayPiles.get(endCol).get(0);

		if (topCard.getTheColor() != endCard.getTheColor()
				&& topCard.getTheNum().getValue() == endCard.getTheNum()
						.getValue() - 1) {
			for (int i = cards.size() - 1; i > -1; i--) {
				this.thePlayPiles.get(endCol).add(0, cards.get(i));
			}

			return true;
		}

		return false;
	}

	/**
	 * deals the cards randomly to there spots on the boards, in all play piles,
	 * and the rest in the stock pile
	 */
	public void dealDeck() {
		clearAllPiles();
		ArrayList<Card> cards = generateCards();
		Collections.shuffle(cards);
		dealCards(cards);

	}

	/**
	 * clears the piles to be empty.
	 */
	public void clearAllPiles() {
		this.theStockPile.clear();
		this.theWastePile.clear();

		for (FinalPile pile : this.theFinalPiles) {
			if (!pile.isEmpty()) {
				pile.clear();
			}
		}

		for (PlayPile pile : this.thePlayPiles) {
			pile.clear();
		}
	}

	/**
	 * @param cards
	 *            starts by doing cards to the play piles, then to stock piles
	 */
	private void dealCards(ArrayList<Card> cards) {
		ArrayList<Card> cardsLeft = dealCardsToPlayPiles(cards);
		dealCardsToStockPile(cardsLeft);
	}

	/**
	 * @param cards
	 *            deals the rest of the cards to the stock pile
	 */
	private void dealCardsToStockPile(ArrayList<Card> cards) {
		while (!cards.isEmpty()) {
			this.theStockPile.push(cards.remove(0));
		}

	}

	/**
	 * @param cards
	 * @return the array list of the unused cards to be used in the stock pile
	 */
	private ArrayList<Card> dealCardsToPlayPiles(ArrayList<Card> cards) {
		for (int pile = 0; pile < NUM_PLAY_PILES; pile++) {
			this.thePlayPiles.get(pile).add(cards.remove(0));
			for (int numCard = 0; numCard < pile; numCard++) {
				this.thePlayPiles.get(pile).add(cards.remove(0));
			}
			this.thePlayPiles.get(pile).get(0).setFaceUp();
		}
		return cards;
	}

	/**
	 * @return a ordered deck of cards
	 */
	private ArrayList<Card> generateCards() {
		ArrayList<Card> cards = new ArrayList<Card>();
		for (Suit suit : Suit.values()) {
			for (Num num : Num.values()) {
				cards.add(new Card(suit, num));
			}
		}
		return cards;

	}

	/**
	 * @return the theStockPile
	 */
	public StockPile getTheStockPile() {
		return theStockPile;
	}

	/**
	 * @param theWastePile
	 *            the theWastPile to set
	 */
	public void setTheWastePile(WastePile theWastePile) {
		this.theWastePile = theWastePile;
	}

	/**
	 * @param thePlayPile
	 *            the thePlayPile to set
	 */
	public void setThePlayPile(PlayPile thePlayPile, int endCol) {
		this.thePlayPiles.set(endCol, thePlayPile);
	}

	/**
	 * @return the theWastePile
	 */
	public WastePile getTheWastePile() {
		return theWastePile;
	}

	/**
	 * @return the theFinalPiles
	 */
	public ArrayList<FinalPile> getTheFinalPiles() {
		return theFinalPiles;
	}

	/**
	 * @return the thePlayPiles
	 */
	public ArrayList<PlayPile> getThePlayPiles() {
		return thePlayPiles;
	}

	/**
	 * @return boolean: true if it is a win, false if not
	 */
	public boolean isWin() {
		int sum = this.theFinalPiles.get(0).size()
				+ this.theFinalPiles.get(1).size()
				+ this.theFinalPiles.get(2).size()
				+ this.theFinalPiles.get(3).size();
		if (sum == 52) {
			numberOfWins += 1;
			winPercentage = ((double) numberOfWins)
					/ (numberOfWins + numberOfLosses);
			return true;
		}
		return false;
	}

	public int getNumberOfWins() {
		return numberOfWins;
	}

	public void setNumberOfWins(int numberOfWins) {
		this.numberOfWins = numberOfWins;
	}

	public int getNumberOfLosses() {
		return numberOfLosses;
	}

	public void setNumberOfLosses(int numberOfLosses) {
		this.numberOfLosses = numberOfLosses;
		winPercentage = ((double) numberOfWins)
				/ (numberOfWins + numberOfLosses);
	}

	/**
	 * @return true if empty false if not
	 */
	public boolean isStockPileEmpty() {

		if (this.theStockPile.isEmpty()) {
			return true;
		}

		return false;
	}

	public boolean isWastePileEmpty() {
		return theWastePile.isEmpty();
	}

	public double getWinPercentage() {
		return winPercentage;
	}

}
