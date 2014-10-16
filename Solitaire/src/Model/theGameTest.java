/**
 * CSCI 205 - Software Design and Engineering
 * Names: Alex Wade, Anna Zeveney, JB Ring
 *
 * Team name: Chase's minions
 * Project: Solitaire
 */

package Model;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Model.Card.Num;
import Model.Card.Suit;

/**
 * Tests the game class
 * 
 */
public class theGameTest {

	private Game game1;

	@Before
	public void setUp() {

		game1 = new Game();
	}

	@After
	public void tearDown() {
		game1 = null;
	}

	@Test
	public void testFlipCard() {
		game1.dealDeck();
		game1.flipCard();
		if (game1.getTheWastePile().isEmpty()) {
			fail("no cards in wast pile flip fail");
		}
	}

	@Test
	public void testMoveFromWasteToFinal() {

		game1.dealDeck();
		WastePile theWastePile = new WastePile();
		theWastePile.push(new Card(Suit.DIAMOND, Num.ACE));
		game1.setTheWastePile(theWastePile);

		if (!game1.moveFromWasteToFinal(0)) {

			fail("failure to change from waste to final");

		}

		if (game1.getTheFinalPiles().get(0).isEmpty()) {
			fail("failure to change from waste to final");
		}
	}

	@Test
	public void testMoveFromWasteToPlaying() {

		game1.dealDeck();
		WastePile theWastePile = new WastePile();
		theWastePile.push(new Card(Suit.DIAMOND, Num.FOUR));
		game1.setTheWastePile(theWastePile);

		PlayPile thePlayPile = new PlayPile();
		thePlayPile.add(new Card(Suit.CLUB, Num.FIVE));
		game1.setThePlayPile(thePlayPile, 0);

		if (!game1.moveFromWasteToPlaying(0)) {
			fail("failure to change from waste to play");
		}
	}

	@Test
	public void testMoveFromPlayingToFinal() {

		game1.dealDeck();

		PlayPile thePlayPile = new PlayPile();

		thePlayPile.add(new Card(Suit.CLUB, Num.ACE));
		thePlayPile.add(new Card(Suit.CLUB, Num.TWO));
		game1.setThePlayPile(thePlayPile, 0);
		if (!game1.moveFromPlayingToFinal(0, 0)) {
			fail("failure to change from Playing to final");
		}

		if (!game1.moveFromPlayingToFinal(0, 0)) {
			fail("failure to change from Playing to final");
		}

	}

	@Test
	public void testMoveFromPlayingToPlaying() {

		game1.dealDeck();

		PlayPile thePlayPile0 = new PlayPile();
		PlayPile thePlayPile1 = new PlayPile();
		thePlayPile1.add(new Card(Suit.DIAMOND, Num.NINE));
		thePlayPile1.add(new Card(Suit.CLUB, Num.TEN));
		thePlayPile1.add(new Card(Suit.DIAMOND, Num.JACK));
		thePlayPile1.add(new Card(Suit.CLUB, Num.QUEEN));

		thePlayPile0.add(new Card(Suit.DIAMOND, Num.KING));

		game1.setThePlayPile(thePlayPile0, 0);
		game1.setThePlayPile(thePlayPile1, 1);

		if (game1.moveFromPlayingToPlaying(0, 1, 1)) {
			fail("failure to change correctly from Playing to Playing");
		}

		if (game1.moveFromPlayingToPlaying(1, 1, 0)) {
			fail("failure to change correctly from Playing to Playing");
		}

		if (!game1.moveFromPlayingToPlaying(1, 4, 0)) {
			fail("failure to change from Playing to Playing");
		}

		thePlayPile0 = new PlayPile();
		thePlayPile1 = new PlayPile();

		thePlayPile1.add(new Card(Suit.SPADE, Num.JACK));
		thePlayPile1.add(new Card(Suit.HEART, Num.QUEEN));

		thePlayPile0.add(new Card(Suit.DIAMOND, Num.KING));

		game1.setThePlayPile(thePlayPile0, 0);
		game1.setThePlayPile(thePlayPile1, 1);

		if (game1.moveFromPlayingToPlaying(1, 2, 0)) {
			fail("failure to change correctly from Playing to Playing");
		}
	}

	@Test
	public void testIsWin() {
		WastePile cards = new WastePile();
		for (Suit suit : Suit.values()) {
			for (Num num : Num.values()) {
				cards.push(new Card(suit, num));
			}
		}

		WastePile fin = new WastePile(); // need to flip for FINal dest
		while (!cards.isEmpty()) {
			fin.push(cards.pop());
		}

		game1.setTheWastePile(fin);

		for (int i = 0; i < 13; i++) {

			game1.moveFromWasteToFinal(0);

		}

		for (int i = 0; i < 13; i++) {
			game1.moveFromWasteToFinal(1);
		}

		for (int i = 0; i < 13; i++) {
			game1.moveFromWasteToFinal(2);
		}

		for (int i = 0; i < 13; i++) {
			game1.moveFromWasteToFinal(3);
		}

		if (!game1.isWin()) {
			fail("final decks not full");
		}

	}

	@Test
	public void testDealTheDeck() {

		game1.dealDeck();

		for (int i = 0; i < 7; i++) {
			if (game1.getThePlayPiles().get(i).isEmpty()) {

				fail("empty play pile");
			}
		}

		for (int i = 0; i < 4; i++) {
			if (!game1.getTheFinalPiles().get(i).isEmpty()) {

				fail("non-empty final pile");
			}
		}

		if (game1.getTheStockPile().isEmpty()) {

			fail("empty stock pile");
		}

		if (!game1.getTheWastePile().isEmpty()) {

			fail("empty stock pile");
		}
		// fail("Not yet implemented");

	}
}
