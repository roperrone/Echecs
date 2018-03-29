import java.util.*;
public class Deplacement {
	Plateau echiquier;
	public LinkedList<Case> depPoss= new LinkedList<Case>();
	public LinkedList<Case> toutDeplAdv = new LinkedList<Case>();
 	public LinkedList<Case> toutDepl = new LinkedList<Case>(); //tous les deplacement du joueur courant
	boolean echecEtMat=false;
 	boolean pat=false;
	boolean echec=false;
	Case cI; // @ param case initiale: contient tous les attributs de la case de déplacement

public Deplacement(Plateau p, Case c){
  echiquier = p;
  cI=c;
  tousDeplacementsAdv();
	remplirListDepl(cI, depPoss);
  }

public void depPion(LinkedList<Case> list){

	// le pion est sur la première ligne: il peut avancer de un ou de deux
	if (cI.y==1 && echiquier.cases[cI.x][cI.y+1].piece==null && echiquier.cases[cI.x][cI.y+2].piece==null){
		list.add(echiquier.cases[cI.x][cI.y+1]);
		list.add(echiquier.cases[cI.x][cI.y+2]);
	}
	else if (cI.y==6 && echiquier.cases[cI.x][cI.y-1].piece==null && echiquier.cases[cI.x][cI.y-2].piece==null){
		list.add(echiquier.cases[cI.x][cI.y-1]);
		list.add(echiquier.cases[cI.x][cI.y-2]);
	} // sinon le pion ne peut avancer que d'une seule case à la fois
	else if (cI.piece.couleur=="blanc" && echiquier.cases[cI.x][cI.y+1].piece==null)
		list.add(echiquier.cases[cI.x][cI.y+1]);
	else if (cI.piece.couleur=="noir" && echiquier.cases[cI.x][cI.y-1]==null)
		list.add(echiquier.cases[cI.x][cI.y-1]);
	}

public void depCavalier( LinkedList <Case> list ){

	// on envisage tous les déplacements possibles
	LinkedList <Case> dep = new LinkedList<Case>();
	dep.add(new Case(cI.x+1,cI.y+2));
	dep.add(new Case(cI.x+1,cI.y-2));
	dep.add(new Case(cI.x-1,cI.y+2));
	dep.add(new Case(cI.x-1,cI.y-2));
	dep.add(new Case(cI.x+2,cI.y+1));
	dep.add(new Case(cI.x+2,cI.y-1));
	dep.add(new Case(cI.x-2,cI.y+1));
	dep.add(new Case(cI.x-2,cI.y-1));

	// on élimine les déplacements qui sortent du tableau (de l'échiquier)
	int x=0;
	int y=0;

	for(Case c: dep) {
		x=c.x; 	y=c.y;
		// si la pièce est dans le tableau et que la couleur de la pièce sur la case où l'on se déplace
		// est différente de la couleur du joueur, alors on ajoute ce déplacement aux déplacements possibles
		if (x>=0 && y>=0 && x<8 && y<8 && echiquier.cases[x][y].piece.couleur!=cI.piece.couleur)
			list.add(c);
	}
}

public boolean estDansLeTableau(int i, int j){
	return (i>=0 && j>=0 && i<8 && j<8 );
}

public void depFou( LinkedList <Case> list ){
  //Initialise 4 tableaux représentant les sens de déplacement possible en  diagonale: HD (haut-droit), ...
	LinkedList<Case>depHD= new LinkedList<Case>();
	LinkedList<Case>depBD= new LinkedList<Case>();
	LinkedList<Case>depHG= new LinkedList<Case>();
	LinkedList<Case>depBG= new LinkedList<Case>();

	int caseX = cI.x;
	int caseY = cI.y;
  // Le nombre maximal de coup possible dans chaque direction: 7-a
	int a = Math.min(caseX,caseY);

	for (int i=1; i<7-a ; i++){
		if (estDansLeTableau (caseY+i,caseX+i))
			depHD.add(new Case(caseX+i,caseY+i));
		if (estDansLeTableau(caseY-i,caseX+i))
			depBD.add(new Case(caseX+i,caseY-i));
		if (estDansLeTableau(caseX-i,caseY+i))
			depHG.add(new Case(caseX-i,caseY+i));
		if (estDansLeTableau(caseY-i,caseY-i))
			depBG.add(new Case(caseX-i,caseY-i));
	}

	verif(depHD, list);
	verif(depBD, list);
	verif(depHG, list);
	verif(depBG, list);
}

public void verif(LinkedList <Case> depHD, LinkedList <Case> list){
  for( Case c: depHD ) {
		 // si la case correspondante ne contient pas de pièce
		 if( echiquier.cases[c.x][c.y] == null  ) {
			 list.add(c);
		 } else {
			 // la couleur de la pièce sur la case est différente de la couleur du joueur
			 if( !echiquier.cases[c.x][c.y].couleur.equals(cI.piece.couleur) ){
				 list.add(c);
			 }

			 break;
		 }
	}
}

public void depTour( LinkedList <Case> list ){

//Initialise 4 tableaux représentant les sens de déplacement possible en  diagonale: H (haut), ...
	LinkedList<Case>depH= new LinkedList<Case>();
	LinkedList<Case>depB= new LinkedList<Case>();
	LinkedList<Case>depD= new LinkedList<Case>();
	LinkedList<Case>depG= new LinkedList<Case>();

	int caseX = cI.x;
	int caseY = cI.y;

	// Le nombre maximal de coup possible dans chaque direction: 7-a
	int a = Math.min(caseX,caseY);

	for (int i=1; i<7-a ; i++){
		if (estDansLeTableau (caseY+i,caseX+i))
			depH.add(new Case(caseX,caseY+i));
		if (estDansLeTableau(caseY-i,caseX+i))
			depD.add(new Case(caseX+i,caseY));
		if (estDansLeTableau(caseX-i,caseY+i))
			depG.add(new Case(caseX-i,caseY));
		if (estDansLeTableau(caseY-i,caseY-i))
			depB.add(new Case(caseX,caseY-i));
	}

	verif(depH, list);
	verif(depB, list);
	verif(depD, list);
	verif(depG, list);
}

public void depRoi( LinkedList <Case> list ){
	int x=0;
	int y=0;

	LinkedList<Case> dep = new LinkedList<Case>();

 // on ajoute les 8 déplacements possible
	dep.add(new Case(cI.x+1,cI.y));
	dep.add(new Case(cI.x-1,cI.y));
	dep.add(new Case(cI.x+1,cI.y+1));
	dep.add(new Case(cI.x-1,cI.y+1));
	dep.add(new Case(cI.x,cI.y+1));
	dep.add(new Case(cI.x+1,cI.y-1));
	dep.add(new Case(cI.x-1,cI.y-1));
	dep.add(new Case(cI.x,cI.y-1));


	for(Case c: dep ) {
		x = c.x;
		y = c.y;

		// si le déplacement est dans le tableau et qu'il n'y a pas de pièce (ou que la pièce n'est pas de la même couleur)
		if (estDansLeTableau(x,y) && (echiquier.cases[x][y].piece==null || echiquier.cases[x][y].piece.couleur!=cI.piece.couleur))
			list.add(c);
	}

}

//Rempli tous les deplacements possibles de l'adversaire
public void tousDeplacementsAdv(){
	for(Case[] cases : echiquier.cases){
		for(Case c : cases){
			if((c.piece != null) && (c.piece.couleur != cI.piece.couleur)){
				remplirListDepl(c, toutDeplAdv);
			}
		}
	}
}

//Remplir tous les deplacements possibles du joueur
public void tousDeplacements(){
	for(Case[] cases : echiquier.cases){
		for(Case c : cases){
			if((c.piece != null) && (c.piece.couleur == cI.piece.couleur)){
				remplirListDepl(c, toutDepl);
			}
		}
	}
}

public void parerEchec(){
	tousDeplacements();
	for (Case c :toutDepl){

	}
}

public void petitRoque() {
	int x = cI.x;
	int y = cI.y;
	Piece tour= echiquier.cases[x+3][y];
	// ajouter la condition Aucune pièce ennemie ne doit contrôler les deux cases que le Roi parcourt pour roquer.
	if (cI.piece instanceof Roi && x==4 && (y==0 || y==7) && tour instanceof Tour && echiquier.cases[x+1][y]==null && echiquier.cases[x+2][y]==null && enEchec()==false ){
		echiquier.cases[x+2][y]= cI.piece;
		echiquier.cases[x][y] = null;
		echiquier.cases[x+1][y]= tour;
		echiquier.cases[x+3][y] = null;
	}
	else System.out.println ("Vous ne pouvez pas effectuer un petit roque"); // a revoir (i.e fenetre d'erreur)
}

public void grandRoque() {
	int x = cI.x;
	int y = cI.y;
	Piece tour= echiquier.cases[0][y];
	// ajouter la condition Aucune pièce ennemie ne doit contrôler les deux cases que le Roi parcourt pour roquer.
	if (cI.piece instanceof Roi && x==4 && (y==0 || y==7) && tour instanceof Tour && echiquier.cases[x-1][y]==null && echiquier.cases[x-2][y]==null && echiquier.cases[x-3][y]==null && enEchec()==false ){
		echiquier.cases[x-2][y]= cI.piece;
		echiquier.cases[x][y] = null;
		echiquier.cases[x-1][y]= tour;
		echiquier.cases[0][y] = null;
	}
	else System.out.println ("Vous ne pourvez pas effectuer un grand roque"); // a revoir (i.e fenetre d'erreur)
}

public void promotion (){
	String c = cI.piece.couleur;
	if (cI.piece instanceof Pion && (cI.y==7 || cI.y==0))
	cI.piece=new Reine(c); // gerer le choix de promotion
}
/*
public void mangePion() {
	for (Case c: depPion())

}
*/
/*
public boolean misEnEchec(){
 	if( toutDeplAdv.contains(echiquier.trouverPiece("Roi")) )
		return true;
 	else
		return false;
} */


public void remplirListDepl(Case c, LinkedList<Case> list){
	if (c.piece instanceof Pion)
		depPion(list);
	if (c.piece instanceof Cavalier)
		depCavalier(list);
	if (c.piece instanceof Fou)
		depFou(list);
	if (c.piece instanceof Tour)
		depTour(list);
	if (c.piece instanceof Reine){
		// combinaison d'une tour et d'un fou
		depTour(list);
		depFou(list);
	}
	if (c.piece instanceof Roi){
		depRoi(list);
	}
}

public boolean deplacementValide(Case cF){
	return depPoss.contains(cF);
}

public LinkedList<Case> getDeplPoss(){
 	return depPoss;
}


}
