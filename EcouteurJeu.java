import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class EcouteurJeu implements ActionListener {
  private FenetrePlateau f;


/** @param fen: Fenetre Plateau
 *  Constructeur de la classe EcouteurJeu */
  public EcouteurJeu(FenetrePlateau fe){
    this.f = fe;
  }
  
/** @param e : Action
 * méthode appelée lorsqu'une action se produit */
  public void actionPerformed(ActionEvent e){

    Object s = e.getSource();

    if(s instanceof JButton){
      if(s == f.abandonne){
		  f.dispose(); 
	  
	 }else if(s==f.rejouer){
		 Jeu jeu=f.getJeu();
		 Joueur[] tableauJoueur=jeu.tabJoueur;
		 f.dispose();
         
         if(tableauJoueur[1] instanceof JoueurIA){
            tableauJoueur[1] = null;
          } 
         Jeu j=new Jeu(tableauJoueur[0],tableauJoueur[1]);
		 FenetrePlateau fen= new FenetrePlateau(j);
		 j.setFenetre(fen);
	 }
    }
  }
}



