package gui;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class MyMouseAdapter extends MouseAdapter{
		   private JLabel selectedCard;
		   private JLayeredPane cardGameTable;
		   private JPanel basePane;
		   private int deltaX = 0;
		   private int deltaY = 0;

		   public MyMouseAdapter(JLayeredPane game, JPanel basePane) {
		      this.cardGameTable = game;
		      this.basePane = basePane;
		   }

		   @Override
		   public void mousePressed(MouseEvent mEvt) {
		      Component comp = basePane.getComponentAt(mEvt.getPoint());
		      if (comp != null && comp instanceof JLabel) {
		         selectedCard = (JLabel) comp;
		         basePane.remove(selectedCard);
		         basePane.revalidate();
		         basePane.repaint();

		         cardGameTable.add(selectedCard, JLayeredPane.DRAG_LAYER);
		         cardGameTable.revalidate();
		         cardGameTable.repaint();
		         deltaX = mEvt.getX() - selectedCard.getX();
		         deltaY = mEvt.getY() - selectedCard.getY();
		      }
		   }

		   @Override
		   public void mouseReleased(MouseEvent mEvt) {
		      if (selectedCard != null) {
		         cardGameTable.remove(selectedCard);
		         cardGameTable.revalidate();
		         cardGameTable.repaint();

		         basePane.add(selectedCard, 0);
		         basePane.revalidate();
		         basePane.repaint();
		         selectedCard = null;
			 int x = mEvt.getX() - deltaX;
		         int y = mEvt.getY() - deltaY;
			 System.out.println(""+x+" "+y);
		      }
		   }

		   @Override
		   public void mouseDragged(MouseEvent mEvt) {
		      if (selectedCard != null) {
		         int x = mEvt.getX() - deltaX;
		         int y = mEvt.getY() - deltaY;
		         selectedCard.setLocation(x, y);
		         cardGameTable.revalidate();
		         cardGameTable.repaint();
		      }
		   }
}
