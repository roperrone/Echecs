import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Cavalier extends Piece {

  public Cavalier(String c){
    super(c,"Cavalier",1);
  }

  public void afficher(Graphics g, int i, int j){
    Image img = null;

    try {
      img = ImageIO.read(new File(super.file+super.nom+(this.couleur == "noir" ? "N.png" : "B.png")));
    } catch(IOException e){}

      g.drawImage(img, 15+i*100, 15+j*100, null);

  }

}
