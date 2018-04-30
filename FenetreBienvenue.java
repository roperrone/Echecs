import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class FenetreBienvenue extends JFrame {
	
 // ---------- Attributs de la classe ----------
 
  public JLabel joueur;
  public JLabel jBlanc; 
  public JLabel jNoir; 
  public JLabel difficulte;
  
  public JPanel p1;
  public JPanel p2;
  public JPanel p3;
  public JPanel p4; 
  public JPanel p5;
  public JPanel p6;
  public JPanel p7;
  public JPanel content;
  
  public JTextField name1;
  public JTextField name2;
  
  public  JButton lancer;
  public  JButton un;
  public  JButton deux;
 
  int nbJoueurs;
  int profondeur=1; 
  
  public JSlider slide;
  
  static final int S_MIN = 1;
  static final int S_MAX = 3;
  static final int S_INIT =1;    
  
  /** Constructeur de la classe 
   * */
  public FenetreBienvenue(){
    // appel à super 
    super("Parametres de la partie");
    // définition de la taille de la fenetre, son emplacement et sa commande fermeture 
    this.setSize(400,100);
    this.setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// initialisation des attributs de la classe 

    joueur = new JLabel("Nombre de joueurs : ");
    jBlanc = new JLabel("Nom du joueur blanc:"); 
    jNoir = new JLabel ("Nom du joueur noir:");
    difficulte = new JLabel ("Niveau de difficulte:");
    
    lancer = new JButton("Commencer la partie");
    un = new JButton("1");
    deux = new JButton("2");
    
    name1 = new JTextField(null);
    name2 = new JTextField(null);
    
    slide = new JSlider (JSlider.HORIZONTAL, S_MIN, S_MAX, S_INIT);
    
    p1 = new JPanel(new FlowLayout());
    p2 = new JPanel(new BorderLayout());
    p3 = new JPanel(new GridLayout(2,2,20,20));
    p4 = new JPanel(new GridLayout(1,2,10,10)); 
    p5 = new JPanel(new GridLayout(1,2,10,10)); 
    p7 = new JPanel(new GridLayout(1,2,10,10)); 
    p6 = new JPanel (new GridLayout (3,1,0,10));
    content = new JPanel(new BorderLayout());
    
    // commandes relatives au slider 
	slide.setMinorTickSpacing(0);
    slide.setMajorTickSpacing(1);
    slide.setPaintTicks(true);
    slide.setPaintLabels(true);
  
   // ajour des widgets dans les pannels 
    p1.add(joueur);
    p1.add(un);
    p1.add(deux);
    p4.add(jBlanc); 
    p4.add(name1); 
    p5.add(jNoir); 
    p5.add(name2);
    p6.add(p4);
    p6.add(p7);
    p6.add(lancer);
    p7.add(difficulte); 
    p7.add(slide);
    content.add(p1, BorderLayout.NORTH);
    
    //défintion du changeListener relatif au silder 
    slide.addChangeListener(new ChangeListener(){public void stateChanged (ChangeEvent event ){
	  JSlider source = (JSlider)event.getSource(); 
	  if (source.equals(slide) && !source.getValueIsAdjusting())
	  profondeur = source.getValue(); 
	}
	}); 
	//ajout des ecouteurs 
    un.addActionListener(new Ecouteur(this));
    deux.addActionListener(new Ecouteur(this)); 
    lancer.addActionListener(new Ecouteur(this));
    
    setContentPane(content);
    setVisible(true);
  }
  
 
  
  
}
