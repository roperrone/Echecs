import java.util.*;

public class Minimax {

  Plateau echiquier;

  public Deplacement Minimax(int profondeur, Joueur j, Jeu jeu){


  if(profondeur == 0 || echiquier.depCourant.misEnEchec()){
    jeu.plateau.estimer(); //return le meilleur deplacemeent
  }else{



  if(j.couleur.couleurCourante == "blanc") {

  	Deplacement best = new Deplacement(-9999); //deplacement
  	ArrayList<Deplacement> t = jeu.plateau.deplacementsPossibles();

  	if (t.size() != null) {

  	  for(Deplacement d : t){
        d.simuler(echiquier); //simule le deplacement sur le plateau
  	    Deplacement a = minimax(profondeur -1, jeu.tabJoueur[1], jeu)
  	    if (a.score() > best.score()) {
  	      best = a;
  	    }
  	  }
  	}
    return best;
  }



  /**
   * The search algorithm for min (Black player)
   * @param depth - The current depth
   * @return the best evaluation
   */

   if(j.couleur.couleurCourante == "noir") {

     Deplacement best = new Deplacement(9999); //deplacement
     ArrayList<Deplacement> t = jeu.plateau.deplacementsPossibles();

     if (t.size() != null) {

       for(Deplacement d : t){
         d.simuler(echiquier); //simule le deplacement sur le plateau
         Deplacement a = minimax(profondeur -1, jeu.tabJoueur[1], jeu)
         if (a.score() < best.score()) {
           best = a;
         }
       }
     }
     return best;
   }

  }
}
}
