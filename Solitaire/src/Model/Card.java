/**
 * CSCI 205 - Software Design and Engineering
 * Names: Alex Wade, Anna Zeveney, JB Ring
 *
 * Team name: Chase's minions
 * Project: Solitaire
 */

package Model;

/**
 * Card class stores the two different values of the individual cards
 * 
 */
public class Card {
	public static enum Suit {
		CLUB(0), SPADE(1), HEART(2), DIAMOND(3), ;
		private final int value;

		Suit(int value) {
			this.value = value;
		}
	}

	public static enum Num {
		ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(
				9), TEN(10), JACK(11), QUEEN(12), KING(13);
		private final int value;

		Num(int value) {
			this.value = value;
		}

		/**
		 * @return the value
		 */
		public int getValue() {
			return value;
		}
	}

	public static enum CardColor {
		RED, BLACK;
	}

	private static final int NUM_RANKS = 13;

	private static final int CARD_BACK_NUMBER = 52;

	private final CardColor theColor;
	private final Suit theSuit;
	private final Num theNum;
	private boolean isFaceUp;
	private final int cardNumInDeck;

	public Card(Suit theSuit, Num theNum) {
		this.theSuit = theSuit;
		this.theNum = theNum;
		this.isFaceUp = false;
		this.cardNumInDeck = (theSuit.value) * NUM_RANKS + theNum.value - 1;

		if (this.theSuit == Suit.HEART || this.theSuit == Suit.DIAMOND) {
			this.theColor = CardColor.RED;
		} else {
			this.theColor = CardColor.BLACK;
		}
	}

	/**
	 * @return the isFaceUp
	 */

	@Override
	public String toString() {
		return ("" + this.theSuit + " " + this.theNum);
	}

	public boolean isFaceUp() {
		return isFaceUp;
	}

	/**
	 * 
	 */
	public void setFaceUp() {
		this.isFaceUp = true;
	}

	/**
	 * 
	 */
	public void setFaceDown() {
		this.isFaceUp = false;
	}

	/**
	 * @return the theColor
	 */
	public CardColor getTheColor() {
		return theColor;
	}

	/**
	 * @return the theNum
	 */
	public Num getTheNum() {
		return theNum;
	}

	/**
	 * @return the theSuit
	 */
	public Suit getTheSuit() {
		return theSuit;
	}

	public int getCardNumInDeck() {
		if (isFaceUp()) {
			return cardNumInDeck;
		}
		return CARD_BACK_NUMBER;
	}

}
