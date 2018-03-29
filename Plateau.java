import java.util.*;

public class Plateau {

    // Attributs
    public Case[][] cases;
    LinkedList <Piece> pieceSuppr= new LinkedList<Piece>();;
    String couleurCourante;
    Deplacement depCourant; //deplacement courant

    //Constructeur
    public Plateau(){
       cases = new Case[8][8];
       couleurCourante= "blanc";

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
		   Piece p2 = new Pion("blancs");

		   cases[i][1].setPiece(p1);
		   cases[i][6].setPiece(p2);
	   }

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
    if(depCourant.deplacementValide(cases[dx][dy])){
      resetCouleur();
      if(this.cases[dx][dy].piece != null && cases[dx][dy].piece.couleur != couleurCourante){
        supprPiece(dx, dy);
      }
      cases[dx][dy].piece = cases[depCourant.cI.x][depCourant.cI.y].piece;
      cases[depCourant.cI.x][depCourant.cI.y] = null;
    }
  }

//Quand on arrive au bour du plateau avec un pion on peut l'échanger avec une piece de notre choix
  public void remplacerPiece(int x, int y,Piece p){
    this.cases[x][y].piece=p;
  }


  public void remplirCases(){
    for(Case c : depCourant.getDeplPoss()){
      c.setActif();
    }
  }

  public Case trouverPiece(Piece p){
    for(int i = 0; i<cases.length; i){
      for (int j=0;j<cases[0].length ;j++ ) {
        if(cases[i][j].piece == p && cases[i][j].piece.couleur == this.couleurCourante){
          return cases[i][j];
          break;
        }
      }
    }
  }

  public void resetCouleur(){
    for(Case c : depCourant.getDeplPoss()){
      c.resetCouleur();
    }
  }
}
