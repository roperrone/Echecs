import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Case {
  public int x;
  public int y;
  
  public Piece piece;
  public boolean roque_possible = false;
  public boolean enEchec = false;
  
  Color couleur;
  Color blanc = new Color(242,197,142);
  Color noir = new Color(129,88,75);
  Color actifC = new Color(138,195,74);
  Color actifF = new Color(123,178,65);
  Color EchecC = new Color(253,60,60);
  
  Color actifRoque = new Color(0,255,94);

  public Case(int i, int j){
    x = i;
    y = j;
    couleur = ((x+y)%2 == 0) ? blanc : noir;
  }
  
  public Object clone() {    
	    Case c_case = new Case(x, y);
	    c_case.roque_possible = roque_possible;
        
        if( piece != null )
            c_case.piece = (Piece) piece.clone();
	    
	    return c_case;
  }


  public void setPiece(Piece p){
	  this.piece = p;
  }

  public void misEnEchec(boolean b){
	if( b ){
		this.couleur = EchecC;
		enEchec = true;
	} else {
		resetCouleur();
		enEchec = false;
	}
  }

  public void setActif(){
    if( !roque_possible )
		this.couleur = ((x+y)%2 == 0) ? actifC : actifF;
    else
        this.couleur = actifRoque;
  }

  public void resetCouleur(){
    this.couleur = ((x+y)%2 == 0) ? blanc : noir;
    roque_possible = false;
  }
  
  public void setY(int y){
    this.y = y;
  }

  public void afficher(Graphics g){
    g.setColor(couleur);
    g.fillRect(x*100,y*100,100,100);

    if( piece != null )
		piece.afficher(g,x,y);
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
