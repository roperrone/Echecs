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

/**@param c : couleur de la pièce
 * @param n : nom de la pièce
 * @param v : valeur de la pièce
 * Constructeur de la classe Piece */
  public Piece(String c, String n, int v){
    this.couleur = c;
    this.nom = n;
    this.valeur = (couleur == "noir") ? -v : v;
  }

/**@param x :
 * @param y :
 * Méthode */
  public void setCustomPosition(int x, int y){
	 custom_x = x-30;
	 custom_y = y-30;

	 position_custom = true;
  }
/** Méthode  */
  public void resetPosition(){
	position_custom = false;
  }
/**Méthode qui permet de cloner une pièce */
  public Object clone() {
        Piece piece;

      	if (this instanceof Pion) {
           piece = new Pion(couleur);
        } else if (this instanceof Cavalier) {
            piece = new Cavalier(couleur);
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
/**@param g : Graphics
 * @param i : position d'affichage
 * @param j : position d'affichage
 * Méthode qui affiche l'image de la pièce*/
  public void afficher(Graphics g, int i, int j){}

/**@param g : Graphics
 * @param i : position d'affichage
 * @param j : position d'affichage
 * @param largeur : largeur de l'image
 * @param hauteur : hauteur de l'image 
 * Méthode qui affiche l'image de la pièce*/
  public void afficher(Graphics g, int i, int j, int largeur, int hauteur){}

}
