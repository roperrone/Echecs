import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.Timer;

public class FenetreFin extends JFrame {
    
  public JPanel content;
  public JPanel boutons;
  public JLabel fin;
  public JButton rejouer;
  public JButton nouvellePartie;
  public JButton fermer;

  public Jeu jeu;

/** @param j: jeu en cours
 *  Constructeur de la classe FenetreFin */
  public FenetreFin(Jeu j){
    super("Fin de la partie");
    jeu = j;
  
    setSize(500,400);
    setResizable(false);
    this.setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

   
    content = new JPanel(new BorderLayout());
    
    String couleur = ( j.plateau.couleurCourante == "blanc" ) ? "noir" : "blanc";
        
    fin= new JLabel("Les "+couleur+"s l'emportent !");
    rejouer= new JButton("Rejouer");
    nouvellePartie= new JButton ("Nouvelle Partie");
    fermer= new JButton("Fermer");
    boutons= new JPanel(new BorderLayout());
    
    Font f = new Font("Arial", Font.PLAIN, 30); // augmente et change la police 
    fin.setFont(f); 
    fin.setHorizontalAlignment(JLabel.CENTER);
    fin.setVerticalAlignment(JLabel.CENTER);

    if (couleur=="blanc"){
        fin.setForeground(Color.black);
        fin.setBackground(Color.white);
    }else{
        fin.setForeground(Color.white);
        fin.setBackground(Color.black);
    }
    fin.setOpaque(true);
    
    rejouer.addActionListener(new EcouteurFin(this));
    nouvellePartie.addActionListener(new EcouteurFin(this));
    fermer.addActionListener(new EcouteurFin(this));
   
    boutons.add(rejouer,BorderLayout.NORTH);
    boutons.add(nouvellePartie,BorderLayout.CENTER);
    boutons.add(fermer, BorderLayout.SOUTH);

    content.add(fin, BorderLayout.CENTER);
    content.add(boutons, BorderLayout.SOUTH);

    setContentPane(content);
    setVisible(true);
    
  }
}

