import java.awt.*;
import javax.swing.*;

public class DessinePlateau extends JPanel {
	private FenetrePlateau fen;
	private Jeu jeu;
	
	/** constructeur de la classe 
	 * */
	public DessinePlateau( FenetrePlateau f, Jeu j ){
		fen = f;
		jeu = j;
	}
	/** méthode de dessin 
	 * @param g le graphique 
	 * */
	public void paintComponent(Graphics g){
		Plateau p = jeu.plateau;
		Case[][] c = p.getCases();
		
		for(int i=0; i<c.length; i++){
			for(int j=0; j<c.length; j++){
				c[i][j].afficher(g);
			}
		}	
		
		// si la pièce est sélectionnée: on l'affiche au premier plan
		Piece piece = fen.clickListen.pieceSelectionee;
		if(piece != null && piece.position_custom ){
			piece.afficher(g, 0, 0);
		}
	}
	
}
