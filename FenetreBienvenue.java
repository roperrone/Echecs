import java.awt.*;
import javax.swing.*;

public class FenetreBienvenue extends JFrame {
  private JLabel joueur;
  public JTextField name1;
  public JTextField name2;
  public  JButton lancer;
  public  JButton un;
  public  JButton deux;
  public JPanel p1;
  public JPanel p2;
  public JPanel p3;
  public JPanel content;

  public FenetreBienvenue(){
    super("Parametres de la partie");
    this.setSize(400,100);
    this.setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    joueur = new JLabel("Nombre de joueurs : ");
    lancer = new JButton("Commencer la partie");
    un = new JButton("1");
    deux = new JButton("2");
    name1 = new JTextField("Nom du joueur 1");
    name2 = new JTextField("Nom du joueur 2");
    p1 = new JPanel(new FlowLayout());
    p2 = new JPanel(new BorderLayout());
    p3 = new JPanel(new BorderLayout());
    content = new JPanel(new BorderLayout());

    p1.add(joueur);
    p1.add(un);
    p1.add(deux);
    content.add(p1, BorderLayout.NORTH);
    un.addActionListener(new Ecouteur(this));
    deux.addActionListener(new Ecouteur(this));
    setContentPane(content);
    setVisible(true);
  }
}
