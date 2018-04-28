import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.Timer;

public class FenetrePlateau extends JFrame implements ActionListener {
  public JPanel content;
  public JPanel plateau;
  public JPanel options;
  public JPanel boutons;
  public JButton abandonne;
  public JButton rejouer;
  public JTextArea text;
  public JPanel panelPieceBlanc;
  public JPanel panelPieceNoir; 
  public JPanel panelPiece;
  public JPanel nbPieceBlanc;
  public JPanel nbPieceNoir;
  
  public JLabel nbPionB;
  public JLabel nbPionN;
  public JLabel nbCavB;
  public JLabel nbCavN;
  public JLabel nbFouB;
  public JLabel nbFouN;
  public JLabel nbTourB;
  public JLabel nbTourN;
  public JLabel nbReineB;
  public JLabel nbReineN;
  public JLabel nbRoiB;
  public JLabel nbRoiN;
  public JLabel titreB;
  public JLabel titreN;
  
  public ClickListener clickListen;
  public MoveListener moveListen;

  private Jeu jeu;

  public FenetrePlateau(Jeu j){
    super("Echecs");
    jeu = j;
  
    setSize(1200,825);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    content = new JPanel(new BorderLayout());
    plateau = new DessinePlateau(this, jeu);
    options = new JPanel(new BorderLayout());
    boutons = new JPanel(new GridLayout(2,2,10,10));
	abandonne=new JButton("Abandonner");
	rejouer=new JButton("Rejouer");
	text=new JTextArea("                      "+jeu.tabJoueur[0].getNom()+"       VS       "+jeu.tabJoueur[1].getNom());
   
    nbPieceBlanc=new JPanel(new GridLayout(7,0));
    nbPieceNoir=new JPanel(new GridLayout(7,0));
    panelPieceBlanc=new FenetrePiece(this, jeu, "blanc");
    panelPieceNoir= new FenetrePiece(this, jeu, "noir");
    panelPiece=new JPanel (new GridLayout(0,4));
    
    Font f = new Font("Serif", Font.PLAIN, 20); // augmente et change la police 
    text.setFont(f); 
    panelPieceBlanc.setBackground(Color.gray);
    panelPieceNoir.setBackground(Color.gray);
    
    //affichage du nombre de pièces mangées 

    nbPionB=new JLabel();
    nbPionN=new JLabel();
    nbCavB=new JLabel();
    nbCavN=new JLabel();
    nbFouB=new JLabel();
    nbFouN=new JLabel();
    nbTourB=new JLabel();
    nbTourN=new JLabel();
    nbReineB=new JLabel();
    nbReineN=new JLabel();
    nbRoiB=new JLabel();
    nbRoiN=new JLabel();
    titreB= new JLabel("Supprimees :");
    titreN= new JLabel("Supprimees :");
    // pour les pieces blanches 
    nbPieceBlanc.add(titreB);
    nbPieceBlanc.add(nbPionB);
    nbPieceBlanc.add(nbCavB);
    nbPieceBlanc.add(nbFouB);
    nbPieceBlanc.add(nbTourB);
    nbPieceBlanc.add(nbReineB);
    nbPieceBlanc.add(nbRoiB);
    
    //nbPionB.revalidate();
    
    //nbPieceBlanc.repaint();
    // pour les pieces noires
    nbPieceNoir.add(titreN);
    nbPieceNoir.add(nbPionN);
    nbPieceNoir.add(nbCavN);
    nbPieceNoir.add(nbFouN);
	nbPieceNoir.add(nbTourN);
    nbPieceNoir.add(nbReineN);
    nbPieceNoir.add(nbRoiN);
    
    
    
	boutons.add(rejouer);
	boutons.add(abandonne);
    panelPiece.add(panelPieceBlanc);
    panelPiece.add(nbPieceBlanc);
    panelPiece.add(panelPieceNoir);
    panelPiece.add(nbPieceNoir);
	
	abandonne.addActionListener(new EcouteurJeu(this));
	rejouer.addActionListener(new EcouteurJeu(this));
	
    options.setBackground(new Color(247,247,247));
    options.setPreferredSize(new Dimension(400, 820));
	options.add(boutons, BorderLayout.SOUTH);
	options.add(text, BorderLayout.NORTH);
    options.add(panelPiece, BorderLayout.CENTER);
    
    panelPiece.repaint();
    content.add(plateau, BorderLayout.CENTER);
    content.add(options, BorderLayout.EAST);

	clickListen = new ClickListener(this);
	moveListen = new MoveListener(this);
	
    plateau.addMouseListener(clickListen);
    plateau.addMouseMotionListener(moveListen);

    Timer t= new Timer(100,this);
    t.start();
    
    setContentPane(content);
    setVisible(true);
  }
    
  public void actionPerformed( ActionEvent e){
      this.actualiser();
  }
   
  
  public void actualiser(){
    nbPionB.setText("  : "+jeu.plateau.getNbPieceMangees("blanc")[0]);
    nbPionN.setText("  : "+jeu.plateau.getNbPieceMangees("noir")[0]);
    nbCavB.setText("  : "+jeu.plateau.getNbPieceMangees("blanc")[1]);
    nbCavN.setText("  : "+jeu.plateau.getNbPieceMangees("noir")[1]);
    nbFouB.setText("  : "+jeu.plateau.getNbPieceMangees("blanc")[2]);
    nbFouN.setText("  : "+jeu.plateau.getNbPieceMangees("noir")[2]);
    nbTourB.setText("  : "+jeu.plateau.getNbPieceMangees("blanc")[3]);
    nbTourN.setText("  : "+jeu.plateau.getNbPieceMangees("noir")[3]);
    nbReineB.setText("  : "+jeu.plateau.getNbPieceMangees("blanc")[4]);
    nbReineN.setText("  : "+jeu.plateau.getNbPieceMangees("noir")[4]);
    nbRoiB.setText("  : "+jeu.plateau.getNbPieceMangees("blanc")[5]);
    nbRoiN.setText("  : "+jeu.plateau.getNbPieceMangees("noir")[5]);
      
  }
  public Jeu getJeu(){ return jeu; }

}

