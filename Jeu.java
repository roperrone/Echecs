import java.util.*;

public class Jeu {
    public Joueur[] tabJoueur;
    public Plateau plateau;
    public int currentPlayer;
    public boolean ai_active;
    public FenetrePlateau fen;

    public Jeu( Joueur j1, Joueur j2) {
      tabJoueur = new Joueur[2];
      plateau = new Plateau();

      if(j2 == null){
        j2 = new JoueurIA(plateau);
        ai_active = true;
      } else{
        ai_active = false;
      }

      tabJoueur[0] = j1;
      tabJoueur[1] = j2;

    }

    public void setFenetre( FenetrePlateau f ) {
      fen = f;
    }

    public void tourSuivant(){

        // réinitialise l'attribut boolean pion en passant après un tour
        if( !plateau.pionEnPassant.isEmpty() && plateau.couleurCourante == plateau.pionEnPassant.get(0).couleur ) {
            plateau.pionEnPassant.get(0).pion_en_passant = false;
            plateau.pionEnPassant.remove(0);
        }

        Case roi = plateau.trouverPiece("Roi", plateau.couleurCourante).get(0);
        Deplacement d = new Deplacement(plateau, roi);

        if( d.misEnEchec() ) {
               roi.misEnEchec(true);
               this.fen.repaint();
        }

        // à l'ordinateur de jouer
        if( plateau.couleurCourante == "noir" && ai_active ) {

            boolean stop = true;

            do {
                JoueurIA j = (JoueurIA) tabJoueur[1];
                Deplacement dep = j.execute(plateau, 3);

                if ( dep != null ) {
                    Case caseArrivee = dep.cF;
                    Case aJouer = dep.cI;

                    roi.misEnEchec(false);

                    Case casePionPassant = plateau.cases[caseArrivee.x][aJouer.y];
                    Piece p = aJouer.piece;

                    plateau.remplacerPiece(aJouer.x, aJouer.y, null);

                    if( caseArrivee.roque_possible ){
                        plateau.roquer(caseArrivee.x, caseArrivee.y, p);
                    } else if( casePionPassant.piece != null && casePionPassant.piece.pion_en_passant && p instanceof Pion ) { // si le pion peut être pris en passant
                        plateau.remplacerPiece(caseArrivee.x, caseArrivee.y, p);
                        plateau.supprPiece(caseArrivee.x, aJouer.y);
                    } else if(caseArrivee.piece!=null && caseArrivee.piece.couleur != p.couleur ){
                        plateau.supprPiece(caseArrivee.x, caseArrivee.y);
                        plateau.remplacerPiece(caseArrivee.x, caseArrivee.y, p);

                    }else {
                         plateau.remplacerPiece(caseArrivee.x, caseArrivee.y, p);

                        // rend le pion vulnérable à une attaque du pion en passant
                        if( p instanceof Pion && Math.abs(caseArrivee.y - aJouer.y) == 2 ){
                            p.pion_en_passant = true;
                            plateau.pionEnPassant.add(p);
                        }
                    }

                    p.deja_bougee = true;

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

        fen.repaint();
        fen.maj_fenetre();
    }

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
