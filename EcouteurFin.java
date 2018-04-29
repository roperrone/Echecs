import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class EcouteurFin implements ActionListener {
  private FenetreFin fe;

  public EcouteurFin(FenetreFin fen){
    this.fe=fen;
  }

  public void actionPerformed(ActionEvent e){

    Object s = e.getSource();

    if(s instanceof JButton){
        if(s==fe.nouvellePartie){
             fe.dispose();
             FenetreBienvenue fenetre= new FenetreBienvenue();
         
        }else if(s==fe.rejouer){
             Jeu jeu=fe.jeu;
             Joueur[] tableauJoueur=jeu.tabJoueur;
             fe.dispose();
             FenetrePlateau fen= new FenetrePlateau(new Jeu(tableauJoueur[0],tableauJoueur[1]));
		 
         }else if(s==fe.fermer){
             fe.dispose();
         }
    }
  }
}

