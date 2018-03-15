import java.awt.*;
import javax.swing.*;

public class DessinePlateau extends JPanel {
	private FenetrePlateau fen;
	private Jeu jeu;
	
	public DessinePlateau( FenetrePlateau f, Jeu j ){
		fen = f;
		jeu = j;
	}
	
	public void paintComponent(Graphics g){
		Plateau p = jeu.plateau;
		Case[][] c = p.getCases();
		
		for(int i=0; i<c[0].length; i++){
			for(int j=0; j<c.length; j++){
				c[i][j].afficher(g);
			}
		}

		
	}
	
}
