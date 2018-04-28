import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class EcouteurJeu implements ActionListener {
  private FenetrePlateau f;
  private FenetreFin fe;

  public EcouteurJeu(FenetrePlateau fe){
    this.f = fe;
  }
  public void actionPerformed(ActionEvent e){

    Object s = e.getSource();

    if(s instanceof JButton){
      if(s == f.abandonne){
		  f.dispose(); 
	  
	 }else if(s==f.rejouer){
		 Jeu jeu=f.getJeu();
		 Joueur[] tableauJoueur=jeu.tabJoueur;
		 f.dispose();
		 FenetrePlateau fen= new FenetrePlateau(new Jeu(tableauJoueur[0],tableauJoueur[1]));
		 
	 }
    }
  }
}



