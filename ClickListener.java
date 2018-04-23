import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class ClickListener implements MouseListener {
    private FenetrePlateau fenetre;
    private LinkedList<Case> depPossible;
    
    public boolean selectionne;
    private Case caseDepart;
    private Case caseArrivee;
    
    public Piece pieceSelectionee = null;

    public ClickListener(FenetrePlateau f){
        fenetre = f;
        selectionne = false;
    }

	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
    
	public void mouseReleased(MouseEvent e){
        
       if( selectionne ) {
           int X = e.getX();
           int Y = e.getY();
           
           Point c = positionCase(X,Y);
           caseArrivee = fenetre.getJeu().plateau.cases[(int)c.getX()][(int)c.getY()];

			// si le déplacement est possible, on déplace la pièce et on passe au joueur suivant
           if ( depPossible.contains(caseArrivee) ){                             
                Piece p = caseDepart.piece;
                
                fenetre.getJeu().plateau.supprPiece(caseDepart.x, caseDepart.y);
                fenetre.getJeu().plateau.remplacerPiece((int)c.getX(), (int)c.getY(), p);
                
                p.deja_bougee = true;
				fenetre.getJeu().plateau.switchCouleurCourante();
           }

			for (Case a : depPossible){
				a.resetCouleur();
			}
              
            selectionne = false;
            
            pieceSelectionee.resetPosition();
			pieceSelectionee = null;
			
			selectionne = false; 
			fenetre.repaint();    
        } 
    }

    public void mousePressed(MouseEvent e) {		
       int X = e.getX();
       int Y = e.getY();

       Point caseJeu = positionCase(X,Y);

       if( !selectionne ) {
		   Plateau p = fenetre.getJeu().plateau;

		   caseDepart = p.cases[(int)caseJeu.getX()][(int)(caseJeu.getY())];
           Case caseDeplacement = p.cases[(int)caseJeu.getX()][(int)(caseJeu.getY())];                      
                     
           if( caseDepart.piece != null && caseDepart.piece.couleur == fenetre.getJeu().plateau.couleurCourante ){
               Deplacement dep = new Deplacement(p,caseDeplacement);
               pieceSelectionee = caseDepart.piece;
               depPossible = dep.getDeplPoss();
               
               selectionne = true;
                
               if( depPossible.size() >= 1 ){                   
                   for (Case a : depPossible){
                       a.setActif();
                   }
                   
                   fenetre.repaint();
                }
          }
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
		return new Point( (int)Math.floor(X/100), (int)Math.floor(Y/100) );
	}

}
