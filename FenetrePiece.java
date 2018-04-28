import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.Image;
import java.awt.Color;

public class FenetrePiece extends JPanel {
	private FenetrePlateau fen;
	private Jeu jeu;
    public int i=0;
    public int j=0;
    public int tailleImage=50;

    public String color;
    
       
	public FenetrePiece( FenetrePlateau f, Jeu j, String couleur ){
		fen = f;
		jeu = j;
        color=couleur;
	}
	
	public void paintComponent(Graphics g){
        super.paintComponent(g);
        int [] nombre =jeu.plateau.getNbPieceMangees(color);
        
        
        Pion p=new Pion(color);
        p.afficher(g,0,1, tailleImage, tailleImage);
        
        Cavalier c= new Cavalier(color);
        c.afficher(g,0,2,tailleImage,tailleImage);
        
        Fou f= new Fou(color);
        f.afficher(g,0,3, tailleImage,tailleImage);

        Tour t= new Tour(color);
        t.afficher(g,0,4 ,tailleImage,tailleImage);

        Reine d=new Reine(color);
        d.afficher(g,0,5 ,tailleImage,tailleImage);

        Roi r= new Roi(color);
        r.afficher(g,0,6 ,tailleImage,tailleImage);
        
    }
}

	

