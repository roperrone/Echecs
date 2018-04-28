import java.util.*;

public class JoueurIA extends Joueur {

  Plateau echiquier;
  Plateau echiquierSimulate;

  public JoueurIA(Plateau p){
    super("IA");
    echiquier = p;;
  }

  public Deplacement execute(Plateau plateau, int profondeur) {

        final long startTime = System.currentTimeMillis();
        Deplacement best = null;
        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;

        System.out.println(plateau.couleurCourante + " THINKING with depth = " +profondeur);

        int nbDep = plateau.deplacementsPossibles().size();

        for ( Deplacement d : plateau.deplacementsPossibles()) {
            Plateau plateauSim = plateau.simulateMove(d.cI, d.cF);

                currentValue = plateau.couleurCourante == "blanc" ?
                                min(plateauSim, profondeur - 1) :
                                max(plateauSim, profondeur - 1);

                if (plateau.couleurCourante == "blanc" && currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                    best = d;
                } else if (plateau.couleurCourante == "noir" && currentValue <= lowestSeenValue) {
                    lowestSeenValue = currentValue;
                    best = d;
                }

        }
        final long executionTime = System.currentTimeMillis() - startTime;
        System.out.println(executionTime+" ms");

        return best;
    }


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

                int currentValue = max(plateauSim, profondeur - 1);
                if (currentValue <= lowestSeenValue) {
                    lowestSeenValue = currentValue;
                }

        }
        return lowestSeenValue;
    }

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

/*
  public Deplacement Minimax(int profondeur, String couleur, Plateau echiquier){

    ArrayList<Deplacement> t = echiquier.deplacementsPossibles();

    if(profondeur == 0 || echiquier.depCourant.misEnEchec() || t.isEmpty()){
      return echiquier.dernierDeplacement; //return le meilleur deplacement de la fin de la branche
    }else{

    Deplacement best = new Deplacement(echiquier, null, null);

    if(couleur == "blanc") {
      best.score = -9999;

    	  for(Deplacement d : t){
          if(d.cI.piece.couleur == "blanc"){
            echiquier.simuler(d); //simule le deplacement sur le plateau
      	    Deplacement a = Minimax(profondeur -1, "noir", echiquier);
      	    if (a.score > best.score) {
      	      best = a;
      	    }
          }
    	  }
    }

     if(couleur == "noir") {
       best.score = 9999;

         for(Deplacement d : t){
           if(d.cI.piece.couleur == "noir"){
             echiquier.simuler(d); //simule le deplacement sur le plateau
             Deplacement a = Minimax(profondeur -1, "blanc", echiquier);
             if (a.score < best.score) {
               best = a;
             }
           }
         }
     }
     return best;
    }
  }

  public Deplacement move(){
    return this.Minimax(3, super.getCouleur(), echiquierSimulate);
  }*/

}
