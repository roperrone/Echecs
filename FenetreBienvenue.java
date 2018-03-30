import java.awt.*;
import javax.swing.*;

public class FenetreBienvenue extends JFrame {
  public JLabel joueur;
  public JLabel jBlanc; 
  public JLabel jNoir;
  public JTextField name1;
  public JTextField name2;
  public  JButton lancer;
  public  JButton un;
  public  JButton deux;
  public JPanel p1;
  public JPanel p2;
  public JPanel p3;
  public JPanel p4; 
  public JPanel p5;
  public JPanel content;
  int nbJoueurs;

  public FenetreBienvenue(){
    super("Parametres de la partie");
    this.setSize(400,100);
    this.setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    joueur = new JLabel("Nombre de joueurs : ");
    lancer = new JButton("Commencer la partie");
    un = new JButton("1");
    deux = new JButton("2");
    jBlanc = new JLabel("Nom du joueur blanc:"); 
    jNoir = new JLabel ("Nom du joueur noir:");
    name1 = new JTextField(null);
    name2 = new JTextField(null);
    p1 = new JPanel(new FlowLayout());
    p2 = new JPanel(new BorderLayout());
    p3 = new JPanel(new GridLayout(2,2,20,20));
    p4 = new JPanel(new GridLayout(1,2,10,10)); 
    p5 = new JPanel(new GridLayout(1,2,10,10)); 
    content = new JPanel(new BorderLayout());

    p1.add(joueur);
    p1.add(un);
    p1.add(deux);
    p4.add(jBlanc); 
    p4.add(name1); 
    p5.add(jNoir); 
    p5.add(name2);
    content.add(p1, BorderLayout.NORTH);
    un.addActionListener(new Ecouteur(this));
    deux.addActionListener(new Ecouteur(this)); 
    lancer.addActionListener(new Ecouteur(this));
    setContentPane(content);
    setVisible(true);
  }
}
