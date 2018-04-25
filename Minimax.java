import java.util.*;

public class Minimax {


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
}
