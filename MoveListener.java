import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class MoveListener implements MouseMotionListener {
    private FenetrePlateau fenetre;

    public MoveListener(FenetrePlateau f){
        fenetre = f;
    }

    public void mouseDragged(MouseEvent e) {
	   if( fenetre.clickListen.selectionne ) {
		   int X = e.getX();
		   int Y = e.getY();
		   
		   Piece p = fenetre.clickListen.pieceSelectionee;
		   p.setCustomPosition(X,Y);
		   
		   fenetre.repaint();
	   }
	}
    public void mouseMoved(MouseEvent e) {}

}
