import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Ecouteur implements ActionListener {
  private FenetreBienvenue f;
  int nbjoueurs;

  public Ecouteur(FenetreBienvenue fe){
    this.f = fe;
  }

  public void actionPerformed(ActionEvent e){

    Object s = e.getSource();

    if(s instanceof JButton){
      if(s == f.un){
        nbjoueurs = 1;
        f.setSize(400,150);
        f.p2.add(f.name1);
        f.content.add(f.p2, BorderLayout.CENTER);
        f.content.add(f.lancer, BorderLayout.SOUTH);
      }else if(s == f.deux){
        nbjoueurs = 2;
        f.setSize(400,170);
        f.p3.add(f.name1, BorderLayout.NORTH);
        f.p3.add(f.name2, BorderLayout.SOUTH);
        f.p2.add(f.p3, BorderLayout.CENTER);
        f.content.add(f.p2, BorderLayout.CENTER);
        f.content.add(f.lancer, BorderLayout.SOUTH);
      } else if(s == f.lancer){
          if(nbjoueurs == 1){
            Jeu j = new Jeu(new Joueur(f.name1.getText()), null); //avec IA
            j.start();
            FenetrePlateau f = new FenetrePlateau(j);
          }else if(nbjoueurs == 2){
            Jeu j = new Jeu(new Joueur(f.name1.getText()), new Joueur(f.name2.getText()) );
            j.start();
        		FenetrePlateau fe = new FenetrePlateau(j);
          }
        }

      }
    }
}
