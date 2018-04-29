import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/** Ecouteur gérant le clic de la souris sur une pièce */
public class ClickListener implements MouseListener {
    private FenetrePlateau fenetre;
    private ArrayList<Case> depPossible;

    public boolean selectionne;
    private Case caseDepart;
    private Case caseArrivee;

    public Piece pieceSelectionee = null;

    /** Constructeur
     * @param f: FenetrePlateau */
    public ClickListener(FenetrePlateau f){
        fenetre = f;
        selectionne = false;
    }

	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}

    // Se déclenche lorsque le bouton de la souris est relaché
	public void mouseReleased(MouseEvent e){
        
        // si une pièce a été sélectionné
       if( selectionne ) {
           int X = e.getX();
           int Y = e.getY();

           Point c = positionCase(X,Y); // on induit les coordonées de la case où la pièce est lâchée à partir des coordonées du clic
           
           caseArrivee = fenetre.getJeu().plateau.cases[(int)c.getX()][(int)c.getY()]; // on récupère la case
           Case casePionPassant = fenetre.getJeu().plateau.cases[(int)c.getX()][caseDepart.y]; // permet de vérifier si un pion peut être pris en passant (par la suite)

            // optimise l'affichage en permettant le rafraichissement le plus tôt possible
            boolean _continue = false;

			// si le déplacement effectué est possible: on déplace la pièce et on passe au joueur suivant
           if ( depPossible.contains(caseArrivee) ){
                
                Piece p = caseDepart.piece;
                
                Case roi = fenetre.getJeu().plateau.trouverPiece("Roi", p.couleur).get(0);
                roi.misEnEchec(false); // les déplacements en échecs étant interdits dans déplacement, le déplacement retourné sort le roi d'échec

                // on fait disparaître la pièce jouée de sa case actuelle
                fenetre.getJeu().plateau.remplacerPiece(caseDepart.x, caseDepart.y, null);

                // Si le roque a été activé pour cette case: on roque
                if( caseArrivee.roque_possible ){
                    fenetre.getJeu().plateau.roquer((int)c.getX(), (int)c.getY(), p);
                } // Si le pion peut être pris en passant, on le prend en diagonale
                else if( casePionPassant.piece != null && casePionPassant.piece.pion_en_passant && p instanceof Pion ) { // si le pion peut être pris en passant
                    fenetre.getJeu().plateau.remplacerPiece((int)c.getX(), (int)c.getY(), p);
                    fenetre.getJeu().plateau.supprPiece((int)c.getX(), caseDepart.y);
                // si la case d'arrivée contient une pièce: on la mange
                }else if(caseArrivee.piece!=null && caseArrivee.piece.couleur != p.couleur ){
                    fenetre.getJeu().plateau.supprPiece((int)c.getX(), (int)c.getY());
                    fenetre.getJeu().plateau.remplacerPiece((int)c.getX(), (int)c.getY(), p);
                // sinon on place simplement la pièce dans la case d'arrivée
                } else {
                    fenetre.getJeu().plateau.remplacerPiece((int)c.getX(), (int)c.getY(), p);

                    // Si le pion  s'est déplacé de 2 cases: on le rend vulnérable à une attaque du pion en passant
                    if( p instanceof Pion && Math.abs((int)c.getY() - caseDepart.y) == 2 ){
                        p.pion_en_passant = true;
                        fenetre.getJeu().plateau.pionEnPassant.add(p);
                    }
                }

                p.deja_bougee = true; // la pièce vient d'être bougée
                fenetre.getJeu().plateau.switchCouleurCourante(); // on change la couleur courante 
                
                _continue = true;
           }

            // On réinitialise la couleur des cases colorées en vert, qui indiquaient une possibilités de déplacement 
			for (Case a : depPossible){
				a.resetCouleur();
			}

            selectionne = false;

            // on replace la pièce au centre de sa case
            pieceSelectionee.resetPosition();
			pieceSelectionee = null;

			selectionne = false;
			fenetre.repaint(); // puis on actualise
            
            if ( _continue ) {
                    new java.util.Timer().schedule( // empêche que l'affichage soit différée par les calculs de l'AI
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                fenetre.getJeu().tourSuivant(); // le tour est terminé, on passe au joueur suivant
                            }
                        }, 
                        50 
                );
            } 
        }
    }

     // Se déclenche lorsque le bouton de la souris est appuyé
    public void mousePressed(MouseEvent e) {
       int X = e.getX();
       int Y = e.getY();

       Point caseJeu = positionCase(X,Y); // on induit les coordonées de la case contenant la pièce à partir des coordonées du clic

       // si aucune pièce n'a été sélectionnée et que c'est au joueur actuel de jouer
       if( !selectionne && (!fenetre.getJeu().ai_active || (fenetre.getJeu().ai_active && fenetre.getJeu().plateau.couleurCourante == "blanc"))) {
		   Plateau p = fenetre.getJeu().plateau;

		   caseDepart = p.cases[(int)caseJeu.getX()][(int)(caseJeu.getY())]; // case contenant la pièce à déplacer
           Case caseDeplacement = p.cases[(int)caseJeu.getX()][(int)(caseJeu.getY())];

           // si la case cliquée contient une pièce et que la pièce nous appartient: on affiche les possibilités de déplacement
           if( caseDepart.piece != null && caseDepart.piece.couleur == fenetre.getJeu().plateau.couleurCourante ){
               Deplacement dep = new Deplacement(p,caseDeplacement);
               pieceSelectionee = caseDepart.piece;
               depPossible = dep.getDeplPoss(); // on récupère la liste des déplacement possible

               selectionne = true;

                // si au moins un déplacement est possible
               if( depPossible.size() >= 1 ){
                   for (Case a : depPossible){                       
                        if(!(caseDepart.piece instanceof Roi)) // le roque n'est possible que si la pièce cliqué est un roi
                            a.roque_possible = false;
                            
                        a.setActif(); // on affiche d'une certaine couleur ce déplacement
                   }
                   fenetre.repaint(); // puis on actualise
                }
          }
       }

    }

    /** Retourne les coordonées de la case à partir de la position relative retournée
     * par l'évènement MouseClicked */
    public Point positionCase(int X, int Y){
		return new Point( (int)Math.floor(X/100), (int)Math.floor(Y/100) );
	}

}
