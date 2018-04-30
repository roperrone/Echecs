import java.util.*;

/** 
 * Gestion du jeu
 * */
public class Jeu {
    public Joueur[] tabJoueur;
    public Plateau plateau;
    public int currentPlayer;
    public boolean ai_active;
    public FenetrePlateau fen;

    /**
     * Crée une instance de jeu à partir de 2 joueurs
     * */
    public Jeu( Joueur j1, Joueur j2) {
      tabJoueur = new Joueur[2];
      plateau = new Plateau();

      // si pas de 2ème joueur: joue contre l'ordinateur
      if(j2 == null){
        j2 = new JoueurIA(plateau);
        ai_active = true;
      } else{
        ai_active = false;
      }

      tabJoueur[0] = j1;
      tabJoueur[1] = j2;
    }

    /** Récupère l'attribut FenetrePlateau */
    public void setFenetre( FenetrePlateau f ) {
      fen = f;
    }

    /** Méthode appelée à chaque fin de tour 
     *  Détecte la mise en échec du joueur adverse 
     *  et gère les déplacements de l'IA */
    public void tourSuivant(){

        // Réinitialise l'attribut boolean pion en passant après un tour
        // Le pion n'est alors plus vulnérable et ne peut plus être mangé en diagonale
        if( !plateau.pionEnPassant.isEmpty() && plateau.couleurCourante == plateau.pionEnPassant.get(0).couleur ) {
            plateau.pionEnPassant.get(0).pion_en_passant = false;
            plateau.pionEnPassant.remove(0);
        }

        // Récupère la case correspondant au roi 
        Case roi = plateau.trouverPiece("Roi", plateau.couleurCourante).get(0);
        Deplacement d = new Deplacement(plateau, roi);

        // Vérifie si le roi a été mis en échec
        if( d.misEnEchec() ) {
               roi.misEnEchec(true);
               this.fen.repaint(); // la case se colore en rouge
        }

        // à l'ordinateur de jouer
        if( plateau.couleurCourante == "noir" && ai_active ) {
            boolean stop = true;

            do {
                JoueurIA j = (JoueurIA) tabJoueur[1];
                Deplacement dep = j.execute(plateau); // Appele la méthode execute de la classe JoueurIA

                // Si un déplacement est proposé par l'IA: on le joue
                if ( dep != null ) {
                    
                    Case caseArrivee = dep.cF;
                    Case aJouer = dep.cI;
                    Case casePionPassant = plateau.cases[caseArrivee.x][aJouer.y];
                    
                    Piece p = aJouer.piece;
                    
                    roi.misEnEchec(false); // les déplacements en échecs étant interdits dans déplacement, le déplacement retourné sort le roi d'échec
                    plateau.remplacerPiece(aJouer.x, aJouer.y, null); // on fait disparaître la pièce jouée de sa case actuelle

                    // Si le roque a été activé pour cette case: on roque
                    if( caseArrivee.roque_possible ){
                        plateau.roquer(caseArrivee.x, caseArrivee.y, p);
                    // Si le pion peut être pris en passant, on le prend en diagonale
                    } else if( casePionPassant.piece != null && casePionPassant.piece.pion_en_passant && p instanceof Pion ) {
                        plateau.remplacerPiece(caseArrivee.x, caseArrivee.y, p);
                        plateau.supprPiece(caseArrivee.x, aJouer.y);
                    // si la case d'arrivée contient une pièce: on la mange
                    } else if(caseArrivee.piece!=null && caseArrivee.piece.couleur != p.couleur ){
                        plateau.supprPiece(caseArrivee.x, caseArrivee.y);
                        plateau.remplacerPiece(caseArrivee.x, caseArrivee.y, p);
                    // sinon on place simplement la pièce dans la case d'arrivée
                    }else {
                         plateau.remplacerPiece(caseArrivee.x, caseArrivee.y, p);

                        // Si le pion  s'est déplacé de 2 cases: on le rend vulnérable à une attaque du pion en passant
                        if( p instanceof Pion && Math.abs(caseArrivee.y - aJouer.y) == 2 ){
                            p.pion_en_passant = true;
                            plateau.pionEnPassant.add(p);
                        }
                    }

                    p.deja_bougee = true; // la pièce vient d'être bougée

                     // on change la couleur courante et le tour est terminé
                    plateau.switchCouleurCourante();
                    tourSuivant(); 

                    stop = true;
                } else if( fen.getJeu().partieTerminee() ){
                    stop = true;
                } else {
                    stop = false;
                }
            } while( !stop );
        }

        // On actualise la fenêtre
        fen.repaint();
        fen.maj_fenetre();
    }

    /** Retourne un booléan indiquant si la partie est terminée  */
    public boolean partieTerminee() {
		    boolean b = false;

        if(plateau.gameOver()){
          b = true;
        }else{
          b = false;
        }

        return b;
	}



}
