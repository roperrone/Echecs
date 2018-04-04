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
  Color actifC = new Color(138,195,74);
  Color actifF = new Color(123,178,65);

  public Case(int i, int j){
    x = i;
    y = j;
    couleur = ((x+y)%2 == 0) ? blanc : noir;
  }

  public void setPiece(Piece p){
	  piece = p;
  }

  public void setActif(){
    this.couleur = ((x+y)%2 == 0) ? actifC : actifF;
  }

  public void resetCouleur(){
    this.couleur = ((x+y)%2 == 0) ? blanc : noir;
  }
  
  public void setY(int y){
    this.y = y;
  }

  public void afficher(Graphics g){
    g.setColor(couleur);
    g.fillRect(x*100,y*100,100,100);

    if( this.piece != null )
		this.piece.afficher(g,x,y);
  }
  
  public String toString() {
      if( piece != null )
        return "[x:"+x+", y:"+y+"] "+piece.toString();
    
      return "[x:"+x+", y:"+y+"]";
  }
  
  /** 
   * La méthode equals vérifie SEULEMENT si les coordonées de la case sont les même
   * */
  public boolean equals(Object c) {      
      if( !(c instanceof Case) ) return false;
      
      Case caseJeu = (Case) c;      
      return (caseJeu.x == x && caseJeu.y == y);     
  }
  
}
