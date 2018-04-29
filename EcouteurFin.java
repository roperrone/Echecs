import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class EcouteurFin implements ActionListener {
  private FenetreFin fe;

/** @param f: Fenetre de fin
 *  Constructeur de la classe EcouteurFin */
  public EcouteurFin(FenetreFin fen){
    this.fe=fen;
  }
  
/** @param e : Action
 * méthode appelée lorsqu'une action se produit */
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
            
            if(tableauJoueur[1] instanceof JoueurIA){
                tableauJoueur[1] = null;
            } 
            
            Jeu j=new Jeu(tableauJoueur[0],tableauJoueur[1]);
            FenetrePlateau fen= new FenetrePlateau(j);
            j.setFenetre(fen);
            
         }else if(s==fe.fermer){
             fe.dispose();
         }
    }
  }
}

