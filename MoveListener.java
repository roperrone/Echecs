import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/** 
 * Ecouteur gérant le déplacement de la souris à l'écran 
 * */
public class MoveListener implements MouseMotionListener {
    private FenetrePlateau fenetre;

    /** Constructeur
     * @param f: FenetrePlateau
     * */
    public MoveListener(FenetrePlateau f){
        fenetre = f;
    }

    public void mouseDragged(MouseEvent e) {
	   if( fenetre.clickListen.selectionne ) { // si une pièce a été sélectionnée
		   // on récupère les coordonées de la souris dans le panel
           int X = e.getX();
		   int Y = e.getY();
		   
		   Piece p = fenetre.clickListen.pieceSelectionee; // on récupère la pièce sélectionnée
		   p.setCustomPosition(X,Y); // on place supperpose la pièce au pointeur
		   
		   fenetre.repaint();
	   }
	}
    public void mouseMoved(MouseEvent e) {}

}
