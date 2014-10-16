/**
 * CSCI 205 - Software Design and Engineering
 * Names: Alex Wade, Anna Zeveney, JB Ring
 *
 * Team name: Chase's minions
 * Project: Solitaire
 */

package gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class Deck extends ArrayList<Icon> {
	private static final int SUIT_COUNT = 4;
	private static final int RANK_COUNT = 13;

	// private ArrayList<ImageIcon> icons;

	public Deck(String path) {
		super();
		try {
			createIconList(path);
			// createDeck();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
	}

	public void createIconList(String path) throws MalformedURLException,
			IOException {
		BufferedImage deckImage = ImageIO.read(new URL(path));
		int width = deckImage.getWidth();
		int height = deckImage.getHeight();
		for (int suit = 0; suit < SUIT_COUNT; suit++) {
			for (int rank = 0; rank < RANK_COUNT; rank++) {
				int x = (rank * width) / RANK_COUNT;
				int y = (suit * height) / SUIT_COUNT;
				int w = width / RANK_COUNT;
				int h = height / SUIT_COUNT;
				BufferedImage cardImg = deckImage.getSubimage(x, y, w, h);
				this.add(new ImageIcon(cardImg));
			}
		}
		String pathToBack = "https://31.media.tumblr.com/a231b9ee86ce2a8b2c42f84c5a264d7c/tumblr_nbwjbqxmVd1tau2hao1_250.jpg";
		URL url = new URL(pathToBack);
		BufferedImage backImage = ImageIO.read(url);
		// BufferedImage sdf = new BufferedImage(Controller.CARD_WIDTH,
		// Controller.CARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		this.add(new ImageIcon(backImage));

	}

	// public void createDeck() {
	// for (ImageIcon i : icons){
	// JLabel e = new JLabel(i);
	// e.setSize(e.getPreferredSize());
	// this.add(e);
	// }
	// }
}
