import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Case {
  public int x;
  public int y;
  public Piece piece;
  Color couleur;
  Color blanc = new Color(242,197,142);
  Color noir = new Color(129,88,75);
  Color actif = new Color(149,188,166);

  public Case(int i, int j){
    x = i;
    y = j;
    couleur = ((x+y)%2 == 0) ? blanc : noir;
  }

  public void setPiece(Piece p){
	  piece = p;
  }

  public void setActif(){
    this.couleur = actif;
  }

  public void afficher(Graphics g){
    g.setColor(couleur);
    g.fillRect(x*100,y*100,100,100);

    if( this.piece != null )
		this.piece.afficher(g,x,y);
  }
}
