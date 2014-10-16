package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class MainViewBackUp extends JFrame {

	private final JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainViewBackUp frame = new MainViewBackUp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainViewBackUp() {
		DTListener dtl = new DTListener();
		setTitle("Klondike Solitaire");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		Panel panel = new Panel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 7, 3, 0));

		JPanel stockPile = new JPanel();
		stockPile.setBorder(new TitledBorder(null, "Stock",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(stockPile);
		stockPile.setLayout(new CardLayout(0, 0));

		JPanel wastePile = new JPanel();
		wastePile.setBorder(new TitledBorder(null, "Waste",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(wastePile);
		wastePile.setLayout(new CardLayout(0, 0));

		JSeparator separator = new JSeparator();
		separator.setBackground(UIManager.getColor("Button.background"));
		separator.setForeground(UIManager.getColor("Button.background"));
		separator.setOrientation(SwingConstants.VERTICAL);
		panel.add(separator);

		JPanel finalPile1 = new JPanel();
		finalPile1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(finalPile1);
		finalPile1.setLayout(new CardLayout(0, 0));

		JPanel finalPile2 = new JPanel();
		finalPile2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(finalPile2);
		finalPile2.setLayout(new CardLayout(0, 0));

		JPanel finalPile3 = new JPanel();
		finalPile3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(finalPile3);
		finalPile3.setLayout(new CardLayout(0, 0));

		JPanel finalPile4 = new JPanel();
		finalPile4.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(finalPile4);
		finalPile4.setLayout(new CardLayout(0, 0));

		JPanel stackPanel = new JPanel();
		contentPane.add(stackPanel, BorderLayout.CENTER);
		stackPanel.setLayout(new GridLayout(0, 7, 3, 0));

		JLayeredPane stack1 = new JLayeredPane();
		stack1.setBorder(new LineBorder(new Color(0, 0, 0)));

		stackPanel.add(stack1);

		JLayeredPane stack2 = new JLayeredPane();
		stack2.setBorder(new LineBorder(new Color(0, 0, 0)));
		stackPanel.add(stack2);

		JLayeredPane stack3 = new JLayeredPane();
		stack3.setBorder(new LineBorder(new Color(0, 0, 0)));
		stackPanel.add(stack3);

		JLayeredPane stack4 = new JLayeredPane();
		stack4.setBorder(new LineBorder(new Color(0, 0, 0)));
		stackPanel.add(stack4);

		JLayeredPane stack5 = new JLayeredPane();
		stack5.setBorder(new LineBorder(new Color(0, 0, 0)));
		stackPanel.add(stack5);

		DnDPanel panel3 = new DnDPanel();

		DnDPanel dndpanel = new DnDPanel();
		dndpanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		dndpanel.add(new JTextField("HI"));
		panel3.add(dndpanel);
		stackPanel.add(panel3);

		DnDPanel panel2 = new DnDPanel();
		DnDPanel dndpanel2 = new DnDPanel();
		dndpanel2.setBorder(new LineBorder(new Color(0, 0, 0)));
		dndpanel2.add(new JTextField("HI2"));
		panel2.add(dndpanel2);
		stackPanel.add(panel2);

		JPanel optionsPanel = new JPanel();
		contentPane.add(optionsPanel, BorderLayout.SOUTH);

		JButton btnNewGame = new JButton("New Game");
		optionsPanel.add(btnNewGame);

	}

	class DTListener implements DropTargetListener {

		@Override
		public void dragEnter(DropTargetDragEvent dtde) {

		}

		@Override
		public void dragOver(DropTargetDragEvent dtde) {
		}

		@Override
		public void dropActionChanged(DropTargetDragEvent dtde) {
		}

		@Override
		public void dragExit(DropTargetEvent dte) {
		}

		@Override
		public void drop(DropTargetDropEvent dtde) {
			System.out.println("drop event");
			try {
				((JComponent) dtde.getSource()).add((JComponent) dtde
						.getTransferable().getTransferData(null));
			} catch (UnsupportedFlavorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
