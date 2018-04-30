import java.util.*;

public class JoueurIA extends Joueur {

  Plateau echiquier;  Plateau echiquierSimulate;
  int profondeur;

/** @param p plateau du jeu */
  public JoueurIA(Plateau p){
    super("IA");
    echiquier = p;
  }


/** @param p
  * Définie la profondeur p d'étude du jeu */
  public void setProfondeur (int p){
	  profondeur= p;
  }

/** @param p plateau du Jeu
  * @return best meilleur deplacement possible */
  public Deplacement execute(Plateau plateau) {

        final long startTime = System.currentTimeMillis();
        Deplacement best = null; //meilleur deplacement trouvé
        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue; //valeur du noeud etudié

        System.out.println("L'IA "+plateau.couleurCourante+" joue...");

        int nbDep = plateau.deplacementsPossibles().size(); //recupere tous les deplacements possibles de la couleurCourante

        for ( Deplacement d : plateau.deplacementsPossibles()) {
            Plateau plateauSim = plateau.simulateMove(d.cI, d.cF); //Simule chacun des deplecements
                //Pour chacun des deplecements l'IA simule ensuite le meilleur deplacemet que l'adversaire peu effectuer
                currentValue = plateau.couleurCourante == "blanc" ?
                                min(plateauSim, profondeur - 1) :
                                max(plateauSim, profondeur - 1);
            //Compare cette valeur avec les meilleurs valeurs observées pour les autres deplacements et si le Deplacement est meilleur on le stocke
                if (plateau.couleurCourante == "blanc" && currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                    best = d;
                } else if (plateau.couleurCourante == "noir" && currentValue <= lowestSeenValue) {
                    lowestSeenValue = currentValue;
                    best = d;
                }

        }
        final long executionTime = System.currentTimeMillis() - startTime; // temps de recherche du meilleur deplacement
        System.out.println(executionTime+" ms");

        return best;
    }

    /** @param plateau Pateau du jeu
      * @param profondeur profondeur (ou rang) d'étude du jeu
      * @return la valeur la plus petite observée en fonction de l'état du jeu */
    public int min( Plateau plateau, int profondeur) {
        if(profondeur == 0) {
            return plateau.estimer();
        }
        if(plateau.gameOver()) {
            return plateau.estimer();
        }
        int lowestSeenValue = Integer.MAX_VALUE;
        for (Deplacement d : plateau.deplacementsPossibles()) {
            Plateau plateauSim = plateau.simulateMove(d.cI, d.cF);
            //Recursivité, simule le meilleur deplacement de l'autre couleur
                int currentValue = max(plateauSim, profondeur - 1);
                if (currentValue <= lowestSeenValue) {
                    lowestSeenValue = currentValue;
                }

        }
        return lowestSeenValue;
    }

    /** @param plateau Pateau du jeu
      * @param profondeur profondeur (ou rang) d'étude du jeu
      * @return la valeur la plus grande observée en fonction de l'état du jeu */
    public int max( Plateau plateau, int profondeur) {
        if(profondeur == 0) {
            return plateau.estimer();
        }
        if(plateau.gameOver()) {
            return plateau.estimer();
        }
        int highestSeenValue = Integer.MIN_VALUE;
        for (Deplacement d : plateau.deplacementsPossibles()) {
            Plateau plateauSim = plateau.simulateMove(d.cI, d.cF);

                int currentValue = min(plateauSim, profondeur - 1);
                if (currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                }

        }
        return highestSeenValue;
    }

}
