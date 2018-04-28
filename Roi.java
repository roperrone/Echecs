import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Roi extends Piece {
  private Image img;
  
  public Roi(String c){
    super(c,"Roi",900);
    
    img = null;
    try {
      img = ImageIO.read(new File(super.file+super.nom+(this.couleur == "noir" ? "N.png" : "B.png")));
    } catch(IOException e){}
    
  }

  public void afficher(Graphics g, int i, int j){
	 if( position_custom )
		g.drawImage(img, custom_x, custom_y, null);
	 else 
		g.drawImage(img, 15+i*100, 15+j*100, null);
  }
    public void afficher(Graphics g, int i, int j, int largeur, int hauteur ){ 
	 if( position_custom )
		g.drawImage(img, custom_x, custom_y,largeur, hauteur ,null);
	 else 
		g.drawImage(img, 15+i*100, 15+j*100,largeur, hauteur ,null);
  }
  
  public String toString(){
	 return "Roi "+this.couleur;
  }  
  
}
