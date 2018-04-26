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
           Case casePionPassant = fenetre.getJeu().plateau.cases[(int)c.getX()][caseDepart.y];

			// si le déplacement est possible, on déplace la pièce et on passe au joueur suivant
           if ( depPossible.contains(caseArrivee) ){                             
                Piece p = caseDepart.piece;
                
                Case roi = fenetre.getJeu().plateau.trouverPiece("Roi", p.couleur).getFirst();
				roi.misEnEchec(false);
				
                fenetre.getJeu().plateau.remplacerPiece(caseDepart.x, caseDepart.y, null);
                
                if( caseArrivee.roque_possible ){
                    fenetre.getJeu().plateau.roquer((int)c.getX(), (int)c.getY(), p);
                } else if( casePionPassant.piece != null && casePionPassant.piece.pion_en_passant && p instanceof Pion ) { // si le pion peut être pris en passant
                    fenetre.getJeu().plateau.remplacerPiece((int)c.getX(), (int)c.getY(), p);
                    fenetre.getJeu().plateau.supprPiece((int)c.getX(), caseDepart.y);
                } else {
                    fenetre.getJeu().plateau.remplacerPiece((int)c.getX(), (int)c.getY(), p);
                    
                    // rend le pion vulnérable à une attaque du pion en passant
                    if( p instanceof Pion && Math.abs((int)c.getY() - caseDepart.y) == 2 ){
                        p.pion_en_passant = true;
                        fenetre.getJeu().plateau.pionEnPassant.add(p);
                    }
                }
               				
                p.deja_bougee = true;

				fenetre.getJeu().plateau.switchCouleurCourante();
                fenetre.getJeu().tourSuivant();
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
