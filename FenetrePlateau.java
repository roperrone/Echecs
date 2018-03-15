import java.awt.*;
import javax.swing.*;

public class FenetrePlateau extends JFrame {
  public JPanel content;
  public JPanel plateau;
  public JPanel options;

  public FenetrePlateau(){
    super("Echecs");
    this.setSize(1000,800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    content = new JPanel(new GridLayout(1,2));
    plateau = new DessinePlateau(this); 
    options = new JPanel();
    
    options.setBackground(Color.WHITE); 
    
    content.add(plateau, BorderLayout.WEST);
    content.add(options, BorderLayout.EAST);
    
   // content.addMouseListener(new MoveListener(this));
    
    setContentPane(content);
    setVisible(true);
  }
  
  public static void main(String args[]){
		FenetrePlateau f = new FenetrePlateau();
  }
}
