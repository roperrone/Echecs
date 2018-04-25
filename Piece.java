import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Piece {
  public String couleur;
  public String nom;
  public int valeur;
  
  public int custom_x;
  public int custom_y;
  
  public boolean position_custom = false;
  public boolean deja_bougee = false;
  public boolean pion_en_passant = false;
  
  protected String file = "images/";

  public Piece(String c, String n, int v){
    this.couleur = c;
    this.nom = n;
  }
  
  public void setCustomPosition(int x, int y){
	 custom_x = x-30;
	 custom_y = y-30;
	 
	 position_custom = true;
  }
  
  public void resetPosition(){
	position_custom = false;
  }
  
  public Object clone() {   
        Piece piece;
      
      	if (this instanceof Pion) {
           piece = new Pion(couleur);
        } else if (this instanceof Cavalier) {
            piece = new Pion(couleur);
        } else if (this instanceof Fou) {
            piece = new Fou(couleur);
        } else if (this instanceof Tour) {
           piece = new Tour(couleur);
        } else if (this instanceof Reine){
            piece = new Reine(couleur);
        } else {
            piece = new Roi(couleur);
        }        
	    piece.position_custom = position_custom;
        piece.deja_bougee = deja_bougee;
        piece.pion_en_passant = pion_en_passant;
	    
	    return piece;
  }

  public void afficher(Graphics g, int i, int j){}


}
