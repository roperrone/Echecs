import java.awt.*;
import java.awt.event.*;

public class MoveListener implements MouseListener {
    private Fenetre f;
    private boolean selectione;

    public MoveListener(Fenetre f){
        fenetre = f;
        selectione = false;
    }

    public void mouseClicked(MouseEvent e) {
       int X = e.getXOnScreen();
       int Y = e.getYOnScreen();
       
       Point caseJeu = positionCase(X,Y);
       
       if( !selectione ) {
           p.selectCase(caseJeu.getX(), caseJeu.getY());
           selectione = true;
       }
       else {
            try {
                p.deplacerPiece(caseJeu.getX(), caseJeu.getY());
            } catch( Exception e ){
                //
            }
       }
    }
    
    public Point positionCase(int X, int Y){
        // ...
            
        Point caseJeu = new Point(new_x, new_y);
        return caseJeu;
    }

}
