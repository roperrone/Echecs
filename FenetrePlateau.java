import java.awt.*;
import javax.swing.*;

public class FenetrePlateau extends JFrame {
  public JPanel content;
  public JPanel plateau;
  public JPanel options;
  private Jeu jeu;

  public FenetrePlateau(Jeu j){
    super("Echecs");
    jeu = j;

    setSize(1200,825);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    content = new JPanel(new BorderLayout());
    plateau = new DessinePlateau(this, jeu);
    options = new JPanel();

    options.setBackground(new Color(247,247,247));
    options.setPreferredSize(new Dimension(400, 820));


    content.add(plateau, BorderLayout.CENTER);
    content.add(options, BorderLayout.EAST);

    plateau.addMouseListener(new MoveListener(this));

    setContentPane(content);
    setVisible(true);
  }

  public Jeu getJeu(){ return jeu; }

  public static void main(String args[]){
	    Joueur j1 = new Joueur("Romain");
	    Joueur j2 = new Joueur("Marie");

	    Jeu j = new Jeu(j1, j2);
		j.start();

		FenetrePlateau f = new FenetrePlateau(j);
  }
}
