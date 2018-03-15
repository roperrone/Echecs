import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Case {
  int x;
  int y;
  public Piece piece;
  Color couleur;

  public Case(int i, int j){
    couleur = ((x+y)%2 == 0 ? new Color(129,88,75) : new Color(242,197,142));
    x = i;
    y = j;
  }
  
  public void setPiece(Piece p){
	  piece = p;
  }

  public void afficher(Graphics g){
    g.setColor(couleur);
    g.fillRect(x*100,y*100,100,100);
    
    if( this.piece != null )
		this.piece.afficher(g,x,y);
  }
}
