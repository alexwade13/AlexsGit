/**
 * CSCI 205 - Software Design and Engineering
 * Names: Alex Wade, Anna Zeveney, JB Ring
 *
 * Team name: Chase's minions
 * Project: Solitaire
 */

package gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {
	public JPanel contentPane;
	private final JButton newGameButton;
	private final JLabel numWins;
	private final JLabel winPercentage;

	public ControlPanel() {
		super();
		this.setLayout(new GridLayout(1, 0, 0, 0));
		newGameButton = new JButton("New Game");
		this.add(newGameButton);
		this.winPercentage = new JLabel("Win Percentage: 0.0%");

		this.numWins = new JLabel("Number of Wins: 0");
		numWins.setHorizontalAlignment(SwingConstants.CENTER);
		winPercentage.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(numWins);
		this.add(winPercentage);
	}

	/**
	 * @return
	 */
	public JButton getNewGameButton() {
		return newGameButton;
	}

	public JLabel getNumWins() {
		return numWins;
	}

	public JLabel getWinPercentage() {
		return winPercentage;
	}

}
