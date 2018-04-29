import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Ecouteur implements ActionListener {
  private FenetreBienvenue f;
  

  public Ecouteur(FenetreBienvenue fe){
    this.f = fe;
  }

  public void actionPerformed(ActionEvent e){

    Object s = e.getSource();

    if(s instanceof JButton){
      if(s == f.un){
		f.un.setVisible(false);
		f.deux.setVisible(false);
		f.joueur.setVisible(false);
        f.nbJoueurs = 1;
        f.setSize(400,210);
        f.content.add(f.p6, BorderLayout.CENTER);
        
      }else if(s == f.deux){
		f.un.setVisible(false);
		f.deux.setVisible(false);
		f.joueur.setVisible(false);
        f.nbJoueurs = 2;
        f.setSize(400,170);
        f.p3.add(f.p4, BorderLayout.NORTH);
        f.p3.add(f.p5, BorderLayout.SOUTH);
        f.p2.add(f.p3, BorderLayout.CENTER);
        f.content.add(f.p2, BorderLayout.CENTER);
        f.content.add(f.lancer, BorderLayout.SOUTH);

      }else if(s == f.lancer){
          if(f.nbJoueurs == 1){
            Jeu jeu = new Jeu(new Joueur(f.name1.getText()), null); //avec IA
			JoueurIA j = (JoueurIA) jeu.tabJoueur[1];
			j.setProfondeur(f.profondeur);
            FenetrePlateau fP = new FenetrePlateau(jeu);
            jeu.setFenetre(fP);
       }else if(f.nbJoueurs == 2 && !(f.name1.getText().equals(null)) && !(f.name2.getText().equals(null))){
			Joueur j1=new Joueur(f.name1.getText());
			Joueur j2=new Joueur(f.name2.getText());
            
            Jeu jeu = new Jeu(j1,j2);
            FenetrePlateau fP = new FenetrePlateau(jeu);
            jeu.setFenetre(fP);
          }
          f.dispose();
	  }
      } 
      }
    }

  

