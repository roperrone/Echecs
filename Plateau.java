import java.util.*;

public class Plateau {

    // Attributs
  public Case[][] cases;
  public ArrayList<Piece> pieceSuppr= new ArrayList<Piece>();
  public ArrayList<Piece> pionEnPassant= new ArrayList<Piece>();
  public String couleurCourante;
  public Deplacement depCourant; //deplacement courant
  public Deplacement dernierDeplacement;

  /** Créer un nouveau Plateau */
  public Plateau(){
    cases = new Case[8][8];
    couleurCourante= "blanc";
    initialisation();
  }

  /** @param c tableau de cases
    * @param couleur couleur courante active
    * Creer un plateau a partir des attributs d'un plateau existant */
  public Plateau(Case[][] c, String couleur){
    this.cases = c;
    couleurCourante = couleur;
  }

  //Initialise les cases et place les pièces
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

  /** Change la couleur courante */
  public void switchCouleurCourante(){
	 couleurCourante = ( couleurCourante == "blanc" ) ? "noir" : "blanc";

  }


  /** @return l'état du jeu
    * true si actif - false si partie terminée */
  public boolean gameOver(){
    boolean b;
    Case roi = this.trouverPiece("Roi", this.couleurCourante).get(0);
    Deplacement d = new Deplacement(this, roi);

    if(d.enEchecEtMate()){
      b = true;
    }else{
      b = false;
    }

    return b;
  }

  /** @return le tableau de cases du plateau */
  public Case[][] getCases(){
		return cases;
  }

  /**  Colore les cases ou la piece selectionnée peut se déplacer */
  public void selectCase(int x, int y){
    if(cases[x][y].piece!=null){
      if(cases[x][y].piece.couleur == this.couleurCourante){
        depCourant = new Deplacement(this,cases[x][y]);
        remplirCases();
      }
    }
  }

  /** @param x abscisse de la case sur le plateau
    * @param y ordonnée de la case
    * supprime la pièce de la case selectionée */
  public void supprPiece(int x, int y){
    Piece p = this.cases[x][y].piece;

    if(!(p instanceof Roi) ){
        pieceSuppr.add(this.cases[x][y].piece);
        cases[x][y].piece=null;
    }
  }

  /** @param x abscisse de la case sur le plateau
    * @param y ordonnée de la case
    * @param p Piece
    * Remplace la piece de la case par la piece p */
  public void remplacerPiece(int x, int y, Piece p){
    this.cases[x][y].piece=p;
    effectuerPromotion(x,y,p);
  }

  /** @param x abscisse de la case sur le plateau
    * @param y ordonnée de la case
    * @param p Piece
    * Lorsqu'un pion traverse entièrement le plateau il est remplacé par une reine */
  public void effectuerPromotion(int x, int y, Piece p){
    if( p != null ){
        String c = p.couleur;

        if (p instanceof Pion && ((c == "blanc" && y==0) || (c == "noir" && y==7)) )
            remplacerPiece(x,y, new Reine(c));
    }
  }


  /** @param x abscisse de la case sur le plateau
    * @param y ordonnée de la case
    * @param p Piece
    * Effectue un roque */
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

  /** @param c1 case d'origine
    * @param c2 case a echanger
    * @return Plateau modifié
    * Modifie le plateau en echangeant c1 et c2 */
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

    // Verifie si l'échange est un roque ou un en passant
    if( arrivee.roque_possible ){
        pl.roquer(arrivee.x, arrivee.y, p);
    } else if( casePionPassant.piece != null && casePionPassant.piece.pion_en_passant && p instanceof Pion ) { // si le pion peut être pris en passant
        pl.remplacerPiece(arrivee.x, arrivee.y, p);
        pl.supprPiece(arrivee.x, depart.y);
    } else if(arrivee.piece!=null && arrivee.piece.couleur != p.couleur ){
        pl.supprPiece(arrivee.x, arrivee.y);
        pl.remplacerPiece(arrivee.x, arrivee.y, p);
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

  /** Colore les cases de la liste depCourant.getDeplPoss() */
  public void remplirCases(){
    for(Case c : depCourant.getDeplPoss()){
      c.setActif();
    }
  }

  /** @param n nom de la piece
    * @param color couleur de la pièce
    * @return list liste des positions des pièces correspondants aux critères
    * Recherche les pièces sur le plateau et les renvoie sous une liste de cases*/
  public ArrayList<Case> trouverPiece(String n, String color){
    ArrayList<Case> list = new ArrayList<Case>();

    for(int i = 0; i<cases.length; i++){
      for (int j=0;j<cases[0].length ;j++ ) {
        if( cases[i][j].piece != null && cases[i][j].piece.nom.equals(n) && cases[i][j].piece.couleur.equals(color)){
          list.add(cases[i][j]);
        }
      }
    }
    return list;
  }

  /** @return somme une sestimation du plateau
    * Estime le plateau selon la somme des pièces et renvoie la valeur */
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


  /** @return list liste des Deplacements possible de la couleurCourante
    * Renvoi tous les deplacement possibles concernants toutes les pieces de la couleur courante  */
  public ArrayList<Deplacement> deplacementsPossibles(){
    ArrayList<Deplacement> list = new ArrayList<Deplacement>();

    for(int i=0; i<this.cases.length; i++){
      for(int j=0; j<this.cases[0].length; j++){
        if(this.cases[i][j].piece != null && this.cases[i][j].piece.couleur == couleurCourante){
          Deplacement d = new Deplacement(this, this.cases[i][j]);
          if(!d.getDeplPoss().isEmpty()){
            for(Case a : d.getDeplPoss()){
              Case cI = this.cases[i][j];
              Case cF = a;
              Deplacement depl = new Deplacement(this, cI, cF);
              list.add(depl);
            }
          }
        }
      }
    }
    return list;
  }

  /** @param couleur couleur des pièces
    * @return nb nombre de pièces mangées
    * Renvoie le nombre de pièces mangées de la couleur selectionnée*/
  public int [] getNbPieceMangees(String couleur){
    int nbPion=0;
    int nbFou=0;
    int nbCavalier=0;
    int nbTour=0;
    int nbReine=0;
    int nbRoi=0;
    int [] nb= new int[6];

    for(Piece p: pieceSuppr){
      if(p.couleur==couleur){
        if( p instanceof Pion){
          nbPion++;
          nb[0]=nbPion;
        }else if( p instanceof Cavalier){
          nbCavalier++;
          nb[1]=nbCavalier;
        }else if( p instanceof Fou){
          nbFou++;
          nb[2]=nbFou;
        }else if( p instanceof Tour){
          nbTour++;
          nb[3]=nbTour;
        }else if( p instanceof Reine){
          nbReine++;
          nb[4]=nbReine;
        }else if( p instanceof Roi){
          nbRoi++;
          nb[5]=nbRoi;
        }
      }
    }
    return nb;
  }


}
