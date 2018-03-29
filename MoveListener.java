import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MoveListener implements MouseListener {
    private FenetrePlateau fenetre;
    private boolean selectionne;

    public MoveListener(FenetrePlateau f){
        fenetre = f;
        selectionne = false;
    }

	public void mousePressed(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}

    public void mouseClicked(MouseEvent e) {
       int X = e.getX();
       int Y = e.getY();

       Point caseJeu = positionCase(X,Y);

       if( !selectionne ) {
		   Plateau p = fenetre.getJeu().plateau;
		   Case c = p.cases[(int)caseJeu.getX()][(int)caseJeu.getY()];

           Deplacement dep = new Deplacement(p,c);
           LinkedList<Case> depPossible = dep.getDeplPoss();

           System.out.println(caseJeu.toString());
           selectionne = true;
       }
       else {
            try {
              // deplacerPiece(caseJeu.getX(), caseJeu.getY());
            } catch( Exception MouseEvent ){
                //
            }
       }
    }

    /** Retourne les coordonées de la case à partir de la position relative retournée
     * par l'évènement MouseClicked */
    public Point positionCase(int X, int Y){
		return new Point( (int)Math.floor(X/100), 7-(int)Math.floor(Y/100) );
	}

}
