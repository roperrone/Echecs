import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.Timer;

public class FenetrePlateau extends JFrame {
  public JPanel content;
  public JPanel plateau;
  public JPanel options;
  public JPanel boutons;
  public JButton abandonne;
  public JButton rejouer;
  public JLabel text;
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
  public JLabel titreB;
  public JLabel titreN;
  public Timer t;
  
  public ClickListener clickListen;
  public MoveListener moveListen;

  private Jeu jeu;

  public FenetrePlateau(Jeu j){
    super("Echecs");
    jeu = j;
  
    setSize(1200,825);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    setUIFont (new javax.swing.plaf.FontUIResource("Roboto",Font.PLAIN,15));
        
    content = new JPanel(new BorderLayout());
    plateau = new DessinePlateau(this, jeu);
    options = new JPanel(new BorderLayout());
    boutons = new JPanel(new GridLayout(2,2,10,10));
	abandonne=new JButton("Abandonner");
	rejouer=new JButton("Rejouer");
	text=new JLabel("<html> Partie d'&eacute;chec <br>"+jeu.tabJoueur[0].getNom()+" contre "+jeu.tabJoueur[1].getNom()+"</html>");
   
    nbPieceBlanc=new JPanel(new GridLayout(7,0));
    nbPieceNoir=new JPanel(new GridLayout(7,0));
    panelPieceBlanc=new FenetrePiece(this, jeu, "blanc");
    panelPieceNoir= new FenetrePiece(this, jeu, "noir");
    panelPiece=new JPanel (new GridLayout(0,4));
    
    Font f = new Font("Roboto", Font.PLAIN, 20); // augmente et change la police 
    text.setFont(f); 
    text.setHorizontalAlignment(JLabel.CENTER);
    text.setVerticalAlignment(JLabel.CENTER);

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
    
    titreB= new JLabel("");
    titreN= new JLabel("");
    
    nbPionB.setHorizontalAlignment(JLabel.CENTER);
    nbPionB.setVerticalAlignment(JLabel.CENTER);
    nbPionN.setHorizontalAlignment(JLabel.CENTER);
    nbPionN.setVerticalAlignment(JLabel.CENTER);
    nbCavN.setHorizontalAlignment(JLabel.CENTER);
    nbCavN.setVerticalAlignment(JLabel.CENTER);
    nbCavB.setHorizontalAlignment(JLabel.CENTER);
    nbCavB.setVerticalAlignment(JLabel.CENTER);
    nbFouB.setHorizontalAlignment(JLabel.CENTER);
    nbFouB.setVerticalAlignment(JLabel.CENTER);   
    nbFouN.setHorizontalAlignment(JLabel.CENTER);  
    nbFouN.setVerticalAlignment(JLabel.CENTER);  
    nbTourB.setHorizontalAlignment(JLabel.CENTER);  
    nbTourB.setVerticalAlignment(JLabel.CENTER);     
    nbTourN.setHorizontalAlignment(JLabel.CENTER);  
    nbTourN.setVerticalAlignment(JLabel.CENTER);        
    nbReineB.setHorizontalAlignment(JLabel.CENTER);  
    nbReineB.setVerticalAlignment(JLabel.CENTER);  
    nbReineN.setHorizontalAlignment(JLabel.CENTER);  
    nbReineN.setVerticalAlignment(JLabel.CENTER);  
       
    // pour les pieces blanches 
    nbPieceBlanc.add(titreB);
    nbPieceBlanc.add(nbPionB);
    nbPieceBlanc.add(nbCavB);
    nbPieceBlanc.add(nbFouB);
    nbPieceBlanc.add(nbTourB);
    nbPieceBlanc.add(nbReineB);

    // pour les pieces noires
    nbPieceNoir.add(titreN);
    nbPieceNoir.add(nbPionN);
    nbPieceNoir.add(nbCavN);
    nbPieceNoir.add(nbFouN);
	nbPieceNoir.add(nbTourN);
    nbPieceNoir.add(nbReineN);
    
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
    
    content.add(plateau, BorderLayout.CENTER);
    content.add(options, BorderLayout.EAST);

	clickListen = new ClickListener(this);
	moveListen = new MoveListener(this);
	
    plateau.addMouseListener(clickListen);
    plateau.addMouseMotionListener(moveListen);

   
    //t= new Timer();
  //  t.start();
    
    setContentPane(content);
    setVisible(true);
  }
    
  public void maj_fenetre(){
      this.actualiser();
        if(jeu.plateau.gameOver()){
            FenetreFin f= new FenetreFin(jeu);
            //t.stop();
            this.dispose();
            
        }
  }

  
  public void actualiser(){
    nbPionB.setText(""+jeu.plateau.getNbPieceMangees("blanc")[0]);
    nbPionN.setText(""+jeu.plateau.getNbPieceMangees("noir")[0]);
    nbCavB.setText(""+jeu.plateau.getNbPieceMangees("blanc")[1]);
    nbCavN.setText(""+jeu.plateau.getNbPieceMangees("noir")[1]);
    nbFouB.setText(""+jeu.plateau.getNbPieceMangees("blanc")[2]);
    nbFouN.setText(""+jeu.plateau.getNbPieceMangees("noir")[2]);
    nbTourB.setText(""+jeu.plateau.getNbPieceMangees("blanc")[3]);
    nbTourN.setText(""+jeu.plateau.getNbPieceMangees("noir")[3]);
    nbReineB.setText(""+jeu.plateau.getNbPieceMangees("blanc")[4]);
    nbReineN.setText(""+jeu.plateau.getNbPieceMangees("noir")[4]);

  }
  
  public Jeu getJeu(){ return jeu; }
  
  // Source: https://stackoverflow.com/questions/7434845/setting-the-default-font-of-swing-program
  public static void setUIFont (javax.swing.plaf.FontUIResource f){
    java.util.Enumeration keys = UIManager.getDefaults().keys();
    while (keys.hasMoreElements()) {
      Object key = keys.nextElement();
      Object value = UIManager.get (key);
      if (value instanceof javax.swing.plaf.FontUIResource)
        UIManager.put (key, f);
      }
    } 

}

