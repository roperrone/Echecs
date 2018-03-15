import java.awt.*;
import javax.swing.*;

public class FenetrePlateau extends JFrame {
  public JPanel content;
  public JPanel plateau;
  public JPanel options;
  private Jeu jeu;

  public FenetrePlateau(Jeu j){
    super("Echecs");
    this.setSize(1000,800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	this.jeu = j;
	
    content = new JPanel(new GridLayout(1,2));
    plateau = new DessinePlateau(this, jeu); 
    options = new JPanel();
    
    options.setBackground(Color.WHITE); 
    
    content.add(plateau, BorderLayout.WEST);
    content.add(options, BorderLayout.EAST);
    
   // content.addMouseListener(new MoveListener(this));
    
    setContentPane(content);
    setVisible(true);
  }
  
  public static void main(String args[]){
	    Joueur j1 = new Joueur("Romain"); 
	    Joueur j2 = new Joueur("Marie");
	    
	    Jeu j = new Jeu(j1, j2);
		j.start();
		
		FenetrePlateau f = new FenetrePlateau(j);
  }
}
