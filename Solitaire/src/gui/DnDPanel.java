package gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

public class DnDPanel extends JPanel implements Transferable,
		DragSourceListener, DragGestureListener, Serializable {
	private DragSource source;
	private DropTarget dropTarget;

	private TransferHandler t;
	private DTListener dtlistener;
	private Object acceptableActions;

	public DnDPanel() {
		super();
		t = new TransferHandler() {
			@Override
			public Transferable createTransferable(JComponent c) {
				return new DnDPanel();
			}
		};
		setTransferHandler(t);
		source = new DragSource();
		source.createDefaultDragGestureRecognizer(this,
				DnDConstants.ACTION_MOVE, this);
		this.dtlistener = new DTListener();
		this.dropTarget = new DropTarget(this, this.dtlistener);

	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { new DataFlavor(DnDPanel.class, "JPanel") };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return true;
	}

	@Override
	public Object getTransferData(DataFlavor flavor) {
		return this;
	}

	@Override
	public void dragEnter(DragSourceDragEvent dsde) {
	}

	@Override
	public void dragOver(DragSourceDragEvent dsde) {
	}

	public void dropActionchanged(DragSourceDragEvent dsde) {
	}

	@Override
	public void dragExit(DragSourceEvent dse) {
	}

	@Override
	public void dragDropEnd(DragSourceDropEvent dsde) {
		System.out.println("Drag complete\n");
		repaint();
	}

	@Override
	public void dragGestureRecognized(DragGestureEvent dge) {
		System.out.println("Drag started\n");
		source.startDrag(dge, DragSource.DefaultMoveDrop, new DnDPanel(), this);
	}

	@Override
	public void dropActionChanged(DragSourceDragEvent dsde) {
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
			DropTarget target = ((DropTarget) dtde.getSource());
			DnDPanel targetComp = (DnDPanel) target.getComponent();
			DataFlavor df = (targetComp.getTransferDataFlavors())[0];
			DnDPanel data;
			try {
				data = (DnDPanel) dtde.getTransferable().getTransferData(df);
				System.out.println("drop event");
				targetComp.add(data);
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}
