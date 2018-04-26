import java.util.*;

public class Plateau {

    // Attributs
    public Case[][] cases;
    public LinkedList <Piece> pieceSuppr= new LinkedList<Piece>();
    public LinkedList <Piece> pionEnPassant= new LinkedList<Piece>();
    public String couleurCourante;

    public Deplacement depCourant; //deplacement courant
    public Deplacement dernierDeplacement;

    //Constructeur
  public Plateau(){
    cases = new Case[8][8];
    couleurCourante= "blanc";
    initialisation();
  }

  public Plateau(Case[][] cases, String couleur){
    this.cases = cases;
    couleurCourante = couleur;
  }

  public void initialisation(){
    for(int i=0; i<8; i++) {
     for(int j=0; j<8; j++) {
       cases[i][j] = new Case(i,j);
     }
    }

      // initialisation des pièces principales
      for(int i=0; i<8; i++) {
      Piece p1 = null;
      Piece p2 = null;

      switch(i){
        case 0:
        case 7:
           p1 = new Tour("noir");
           p2 = new Tour("blanc");
         break;
        case 1:
        case 6:
         p1 = new Cavalier("noir");
         p2 = new Cavalier("blanc");
         break;
        case 2:
        case 5:
         p1 = new Fou("noir");
         p2 = new Fou("blanc");
           break;
        case 3:
         p1 = new Reine("noir");
         p2 = new Reine("blanc");
         break;
        case 4:
         p1 = new Roi("noir");
         p2 = new Roi("blanc");
         break;
      }

      cases[i][0].setPiece(p1);
      cases[i][7].setPiece(p2);
    }



      // initialisation des pions
      for(int i=0; i<8; i++) {
		  Piece p1 = new Pion("noir");
		  Piece p2 = new Pion("blanc");

		  cases[i][1].setPiece(p1);
		  cases[i][6].setPiece(p2);
    }
  }

  public void switchCouleurCourante(){
	 couleurCourante = ( couleurCourante == "blanc" ) ? "noir" : "blanc";

  }

  public Case[][] getCases(){
		return cases;
  }

  public void selectCase(int x, int y){
    if(cases[x][y].piece!=null){
      if(cases[x][y].piece.couleur==this.couleurCourante){
        depCourant=new Deplacement(this,cases[x][y]);
        remplirCases();
      }
    }
  }

  public void supprPiece(int x, int y){
    pieceSuppr.add(this.cases[x][y].piece);
    cases[x][y].piece=null;
  }

  // dx dy coordonnees case de d'arrivée
  public void deplacerPiece(int dx, int dy, String couleur){
    dernierDeplacement = depCourant; //si le déplacmeent est effectué le dernier deplacement deviens le depCourant
      resetCouleur();
      if(this.cases[dx][dy].piece != null && cases[dx][dy].piece.couleur != couleurCourante){
        supprPiece(dx, dy);
      }
      cases[dx][dy].piece = cases[depCourant.cI.x][depCourant.cI.y].piece;
      cases[depCourant.cI.x][depCourant.cI.y] = null;


  }

  //Quand on arrive au bout du plateau avec un pion on peut l'échanger avec une piece de notre choix
  public void remplacerPiece(int x, int y, Piece p){
    this.cases[x][y].piece=p;
    effectuerPromotion(x,y,p);
  }

  public void effectuerPromotion(int x, int y, Piece p){
    if( p != null ){
        String c = p.couleur;

        if (p instanceof Pion && ((c == "blanc" && y==0) || (c == "noir" && y==7)) )
            remplacerPiece(x,y, new Reine(c));
    }
  }

  public void roquer(int x, int y, Piece p) {
      if( x == 2 ) { // on effectue le petit roque
          remplacerPiece(x,y,p);
          remplacerPiece(x+1,y, cases[x-2][y].piece );

          remplacerPiece(x-2,y, null );
      } else if ( x == 6 ) { // on effectue le grand roque
          remplacerPiece(x,y,p);
          remplacerPiece(x-1,y, cases[x+1][y].piece );
          remplacerPiece(x+1,y, null );
      }
  }

  public Plateau simulateMove( Case c1, Case c2 ){
     // on passe en mode simulation
     Case[][] case_t = new Case[8][8];

     for(int i=0; i<cases.length; i++ ){
         for(int j=0; j<cases.length; j++ ){
             case_t[i][j] = (Case) cases[i][j].clone();
         }
     }

     Plateau pl = new Plateau(case_t, couleurCourante);
     Case depart =  pl.cases[c1.x][c1.y];
     Case arrivee =  pl.cases[c2.x][c2.y];
     Case casePionPassant = pl.cases[arrivee.x][depart.y];

     Piece p = depart.piece;

     pl.remplacerPiece(c1.x, c1.y, null);
     pl.remplacerPiece(c2.x, c2.y, p);

    if( arrivee.roque_possible ){
        pl.roquer(arrivee.x, arrivee.y, p);
    } else if( casePionPassant.piece != null && casePionPassant.piece.pion_en_passant && p instanceof Pion ) { // si le pion peut être pris en passant
        pl.remplacerPiece(arrivee.x, arrivee.y, p);
        pl.supprPiece(arrivee.x, depart.y);
    } else {
         pl.remplacerPiece(arrivee.x, arrivee.y, p);

        // rend le pion vulnérable à une attaque du pion en passant
        if( p instanceof Pion && Math.abs(arrivee.y - depart.y) == 2 ){
            p.pion_en_passant = true;
            pl.pionEnPassant.add(p);
        }
    }

    p.deja_bougee = true;
    return pl;
  }

  public void remplirCases(){
    for(Case c : depCourant.getDeplPoss()){
      c.setActif();
    }
  }


  public LinkedList<Case> trouverPiece(String n, String color){
    LinkedList<Case> list = new LinkedList<Case>();

    for(int i = 0; i<cases.length; i++){
      for (int j=0;j<cases[0].length ;j++ ) {
        if( cases[i][j].piece != null && cases[i][j].piece.nom.equals(n) && cases[i][j].piece.couleur.equals(color)){
          list.add(cases[i][j]);
        }
      }
    }
    return list;
  }

  public LinkedList<Case> trouverPiece(String n){
    return trouverPiece(n, this.couleurCourante);
  }


  public void resetCouleur(){
    for(Case c : depCourant.getDeplPoss()){
      c.resetCouleur();
    }
  }

  public void echangerPiece(Case c1, Case c2){
    cases[c2.x][c2.y].setPiece(c1.piece);
    cases[c1.x][c2.y].setPiece(null);
  }

  public void simuler(Deplacement d){
    cases[d.cF.x][d.cF.y]=d.cI;
    cases[d.cI.x][d.cI.y]=null;
  }

  public int estimer(){
    int somme=0;
    for(int i=0; i<cases.length; i++){
      for(int j=0; j<cases[0].length; j++){
        if(cases[i][j].piece != null){
          somme += cases[i][j].piece.valeur;
        }
      }
    }
    return somme;
  }

  public ArrayList<Deplacement> deplacementsPossibles(){
    ArrayList<Deplacement> list = new ArrayList<Deplacement>();

    for(int i=0; i<this.cases.length; i++){
      for(int j=0; j<this.cases[0].length; j++){
        Deplacement d = new Deplacement(this, this.cases[i][j]);
        for(Case a : d.getDeplPoss()){
          list.add(new Deplacement(this, d.cI, a));
        }
      }
    }
    return list;
  }


}
