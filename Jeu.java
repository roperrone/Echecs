import java.util.*;

public class Jeu {
    public Joueur[] tabJoueur;
    public Plateau plateau;
    public int currentPlayer;
    public boolean ai_active;

    public Jeu( Joueur j1, Joueur j2 ) {
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
		     }

        // à l'ordinateur de jouer: dummy version
        if( plateau.couleurCourante == "noir" && ai_active ) {
            ArrayList<Case> piecesNoires = new ArrayList<Case>();

            for( Case[] cases : plateau.cases ){
                for( Case c2: cases ){
                    if( c2.piece != null && c2.piece.couleur == "noir" ){
                        piecesNoires.add(c2);
                    }
                }
            }

            boolean stop = true;

            do {
                // on tire une pièce au hasard
                int coup = (int)(Math.random()*piecesNoires.size());
                Case aJouer = piecesNoires.get(coup);


                Deplacement dep = new Deplacement(plateau, aJouer);
                ArrayList<Case> depPossible = dep.getDeplPoss();

                if ( !depPossible.isEmpty() ) {
                    Case caseArrivee = depPossible.get( (int)(Math.random()*depPossible.size()) );

                    Case casePionPassant = plateau.cases[caseArrivee.x][aJouer.y];
                    Piece p = aJouer.piece;

                    plateau.remplacerPiece(aJouer.x, aJouer.y, null);

                    if( caseArrivee.roque_possible ){
                        plateau.roquer(caseArrivee.x, caseArrivee.y, p);
                    } else if( casePionPassant.piece != null && casePionPassant.piece.pion_en_passant && p instanceof Pion ) { // si le pion peut être pris en passant
                        plateau.remplacerPiece(caseArrivee.x, caseArrivee.y, p);
                        plateau.supprPiece(caseArrivee.x, aJouer.y);
                    } else {
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
                } else {
                    stop = false;
                }
            } while( !stop );


        }
    }

    public void start() {
          Joueur JoueurBlanc = tabJoueur[0];
          Joueur JoueurNoir = tabJoueur[1];

          JoueurBlanc.definirCouleur("blanc");
          JoueurNoir.definirCouleur("noir");
    }

    public boolean partieTerminee() {
		boolean b = false;

        return b;
	}



}
