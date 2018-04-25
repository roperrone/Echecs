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
    this.valeur = (couleur == "noir") ? -v : v;
  }

  public void setCustomPosition(int x, int y){
	 custom_x = x-30;
	 custom_y = y-30;

	 position_custom = true;
  }

  public void resetPosition(){
	position_custom = false;
  }

  public void afficher(Graphics g, int i, int j){}


}
