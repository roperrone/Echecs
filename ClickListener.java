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
           caseArrivee = new Case((int)c.getX(), (int)c.getY());

           if ( depPossible.contains(caseArrivee) ){             
                System.out.println("ok!");   
                
                System.out.println( caseArrivee );
                System.out.println( caseDepart );
                
                caseArrivee.setPiece(caseDepart.piece);
                caseDepart.setPiece(null);
                
                fenetre.repaint();
                                
                selectionne = false;
           }
        } 

    }

    public void mousePressed(MouseEvent e) {
       int X = e.getX();
       int Y = e.getY();

       Point caseJeu = positionCase(X,Y);

       if( !selectionne ) {
		   Plateau p = fenetre.getJeu().plateau;

		   caseDepart = p.cases[(int)caseJeu.getX()][(int)(caseJeu.getY())];
           Case caseDeplacement = p.cases[(int)caseJeu.getX()][(int)(7-caseJeu.getY())];                      
                     
           if( caseDepart.piece != null ){
               Deplacement dep = new Deplacement(p,caseDeplacement);
               depPossible = dep.getDeplPoss();
                
               selectionne = true;
               System.out.println(depPossible);
               
               for (Case a : depPossible){
                   a.setActif();
               }
               
               fenetre.repaint();
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
