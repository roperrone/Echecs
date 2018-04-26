import java.awt.*;
import javax.swing.*;

public class FenetrePlateau extends JFrame {
  public JPanel content;
  public JPanel plateau;
  public JPanel options;
  public JPanel boutons;
  public JButton abandonne;
  public JButton rejouer;
  public JTextArea text;
    
  public ClickListener clickListen;
  public MoveListener moveListen;
  
  private Jeu jeu;

  public FenetrePlateau(Jeu j){
    super("Echecs");
    jeu = j;

    setSize(1200,825);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    content = new JPanel(new BorderLayout());
    plateau = new DessinePlateau(this, jeu);
    options = new JPanel(new BorderLayout());
    boutons = new JPanel(new GridLayout(2,2,10,10));
	abandonne=new JButton("Abandonner");
	rejouer=new JButton("Rejouer");
	text=new JTextArea(jeu.tabJoueur[0]+ "\n" +jeu.tabJoueur[1]);
	
	boutons.add(rejouer);
	boutons.add(abandonne);
	
	abandonne.addActionListener(new EcouteurJeu(this));
	rejouer.addActionListener(new EcouteurJeu(this));
	
    options.setBackground(new Color(247,247,247));
    options.setPreferredSize(new Dimension(400, 820));
	options.add(boutons, BorderLayout.SOUTH);
	options.add(text, BorderLayout.CENTER);
	
    content.add(plateau, BorderLayout.CENTER);
    content.add(options, BorderLayout.EAST);

	clickListen = new ClickListener(this);
	moveListen = new MoveListener(this);
	
    plateau.addMouseListener(clickListen);
    plateau.addMouseMotionListener(moveListen);

    setContentPane(content);
    setVisible(true);
  }

  public Jeu getJeu(){ return jeu; }

}

