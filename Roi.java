import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Roi extends Piece {

  public Roi(String c){
    super(c,"Roi",1);
  }

  public void afficher(Graphics g, int i, int j){
    Image img = null;

    try {
      img = ImageIO.read(new File(super.file+super.nom+(this.couleur == "noir" ? "N.png" : "B.png")));
    } catch(IOException e){}

      g.drawImage(img, 15+i*100, 15+j*100, null);

  }

}
