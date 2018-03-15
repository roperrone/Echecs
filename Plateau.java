import java.util.*;

public class Plateau {
    
    // Attributs
    private Case[][] cases;
    LinkedList <Piece> pieceSuppr= new LinkedList<Piece>();;
    String couleurCourante;
    String[] ordrePiece = {Tour,Cavalier,Fou,Reine,Roi,Fou,Cavalier,Tour};
    Deplacement depCourant; //deplacement courant
    
    //Constructeur
    public Plateau(){
       cases = new Case[8][8]; 
       couleurCourante= "Blanc";
       
    }
    
    
    public void supprPiece(int x, int y, String couleur){
      pieceSuppr.add(this.cases[x][y].piece);  
      cases[x][y].piece=null; 
    }
    
    // dx dy coordonnees case de d'arrivée
    
    public void deplacerPiece(int dx, int dy, String couleur){  
       if(depCourant.estValide(dx,dy)){
            depCourant.resetCouleur();
            if(this.cases[dx][dy].piece!=null && this.cases[dx][dy].piece.couleur!= this.couleurCourante){
                supprPiece(dx, dy, (couleurCourante == "Blanc" ? "Blanc" : "Noir"));
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
}


