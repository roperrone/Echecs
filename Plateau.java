import java.util.*;

public class Plateau {
    
    // Attributs
    private Case[][] cases;
    LinkedList <Piece> pieceSuppr= new LinkedList<Piece>();;
    String couleurCourante;
  //  Deplacement depCourant; //deplacement courant
    
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
       for(int j=0; j<8; j++) { 
		   Piece p1 = null;
		   Piece p2 = null;
		   
		   switch(j){
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
		   
		   cases[0][j].setPiece(p1);
		   cases[7][j].setPiece(p2);
	   }
	   
       // initialisation des pions
       for(int j=0; j<8; j++) {
		   Piece p1 = new Pion("noir");
		   Piece p2 = new Pion("blancs");
		   
		   cases[1][j].setPiece(p1);
		   cases[6][j].setPiece(p2);
	   }   
	   
    }
    
    public Case[][] getCases(){
		return cases;
	}
    
    
    public void supprPiece(int x, int y, String couleur){
      pieceSuppr.add(this.cases[x][y].piece);  
      cases[x][y].piece=null; 
    }
    
    // dx dy coordonnees case de d'arrivée
    
 /*   public void deplacerPiece(int dx, int dy, String couleur){  
       if(depCourant.estValide(dx,dy)){
            depCourant.resetCouleur();
            if(this.cases[dx][dy].piece!=null && this.cases[dx][dy].piece.couleur!= this.couleurCourante){
                supprPiece(dx, dy, (couleurCourante == "blanc" ? "blanc" : "noir"));
            }
            this.cases[dx][dy].piece=this.cases[this.depCourant.cI.x][this.depCourant.cI.y];
            this.cases[this.depCourant.cI.x][this.depCourant.cI.y]=null;
        }
        
    }
    

    
    public boolean echec(){
        
    }
    
    public void selectCase(int x, int y){
        if(cases[x][y].piece!=null){
            if(cases[x][y].piece.couleur==this.couleurCourante){
                depCourant=new Deplacement(this,cases[x][y]);
                depCourant.changerCouleur();
           }
       } 
    }
    
    public void remplacerPiece(int x, int y,Piece p){
        this.cases[x][y].piece=p;
    }
    
    public void échangerPiece(int x, int y, int x1, int y1){
        Piece p=this.cases[x][y].piece;
        this.cases[x][y].piece=this.cases[x1][y1].piece;
        this.cases[x1][y1].piece=p;
    }
    * 
    * */
}


