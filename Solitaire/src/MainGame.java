import gui.ControlPanel;
import gui.PlayingAreaView;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;

import Model.Controller;
import Model.Game;

public class MainGame {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					JFrame frame = new JFrame("Evan Solitaire");
					ControlPanel controlPanel = new ControlPanel();
					frame.add(controlPanel, BorderLayout.SOUTH);
					PlayingAreaView gameView = new PlayingAreaView();
					frame.add(gameView, BorderLayout.NORTH);
					Game game = new Game();
					@SuppressWarnings("unused")
					Controller controller = new Controller(game, gameView,
							controlPanel);

					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.pack();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
