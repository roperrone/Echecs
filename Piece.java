import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Piece {
  public String couleur;
  public String nom;
  public int valeur;
  protected String file = "images/";

  public Piece(String c, String n, int v){
    this.couleur = c;
    this.nom = n;
  }

  public void afficher(Graphics g, int i, int j){}


}
