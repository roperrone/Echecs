import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class MoveListener implements MouseMotionListener {
    private FenetrePlateau fenetre;

    public MoveListener(FenetrePlateau f){
        fenetre = f;
    }

    public void mouseDragged(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}

    /** Retourne les coordonées de la case à partir de la position relative retournée
     * par l'évènement MouseClicked */
    public Point positionCase(int X, int Y){
		return new Point( (int)Math.floor(X/100), 7-(int)Math.floor(Y/100) );
	}

}
