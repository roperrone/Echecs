import java.util.*;
public class Deplacement {
	Plateau echiquier;
	public LinkedList<Case> depPoss= new LinkedList<Case>();
	public LinkedList<Case> toutDeplAdv = new LinkedList<Case>();
 	public LinkedList<Case> toutDepl = new LinkedList<Case>(); //tous les deplacement du joueur courant
	public LinkedList<Case> depParer = new LinkedList<Case>();
	boolean echecEtMat=false;
 	boolean pat=false;
	boolean echec=false;
	Case cI; // @ param case initiale: contient tous les attributs de la case de déplacement

public Deplacement(Plateau p, Case c){
    echiquier = p;
    
    if ( c.piece != null ){
        cI=c;
        
        initialiserListes();
        //tousDeplacementsAdv();
        remplirListDepl(cI, depPoss);
    }
}

public Deplacement(Plateau p, Case c1, Case c2){
	echiquier = p;
	p.echangerPiece(c1,c2);
    
    if ( c2.piece != null ){
        cI=c2;
        tousDeplacementsAdv();
        remplirListDepl(cI, depPoss);
    }
}

//Vide les listes des deplacements possibles
public void initialiserListes(){
	depPoss.clear();
	toutDeplAdv.clear();
	toutDepl.clear();
	depParer.clear();
}


//
// ---------- GESTION DES DEPLACEMENTS GENERAUX DES PIECES ---------
//
public void depPion(LinkedList<Case> list){

    try {
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
    } catch( Exception e ){}
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
            if ( estDansLeTableau(x,y) && c.piece != null )
                if( echiquier.cases[x][y].piece.couleur!=cI.piece.couleur )
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

//Rempli le tableau de deplacements possibles
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



//
// ---------- REGLES PARTICULIERES DES ECHECS ----------
//



public void petitRoque() {
	int x = cI.x;
	int y = cI.y;
	Piece tour= echiquier.cases[x+3][y].piece;
	// ajouter la condition Aucune pièce ennemie ne doit contrôler les deux cases que le Roi parcourt pour roquer.
	if (cI.piece instanceof Roi && x==4 && (y==0 || y==7) && tour instanceof Tour && echiquier.cases[x+1][y]==null && echiquier.cases[x+2][y]==null && enEchec()==false ){
		echiquier.cases[x+2][y].piece= cI.piece;
		echiquier.cases[x][y].piece = null;
		echiquier.cases[x+1][y].piece = tour;
		echiquier.cases[x+3][y].piece = null;
	}
	else System.out.println ("Vous ne pouvez pas effectuer un petit roque"); // a revoir (i.e fenetre d'erreur)
}

public void grandRoque() {
	int x = cI.x;
	int y = cI.y;
	Piece tour= echiquier.cases[0][y].piece;
	// ajouter la condition Aucune pièce ennemie ne doit contrôler les deux cases que le Roi parcourt pour roquer.
	if (cI.piece instanceof Roi && x==4 && (y==0 || y==7) && tour instanceof Tour && echiquier.cases[x-1][y]==null && echiquier.cases[x-2][y]==null && echiquier.cases[x-3][y]==null && enEchec()==false ){
		echiquier.cases[x-2][y].piece = cI.piece;
		echiquier.cases[x][y].piece = null;
		echiquier.cases[x-1][y].piece = tour;
		echiquier.cases[0][y].piece = null;
	}
	else System.out.println ("Vous ne pourvez pas effectuer un grand roque"); // a revoir (i.e fenetre d'erreur)
}

public void promotion (){
	String c = cI.piece.couleur;
	if (cI.piece instanceof Pion && (cI.y==7 || cI.y==0))
	cI.piece=new Reine(c); // gerer le choix de promotion
}


//
// ---------- ETAT DU JEU ---------
//



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

public boolean parerEchec(){
	for (int i=0;i<echiquier.cases.length;i++) {
		for (int j=0;j<echiquier.cases[0].length;j++) {
			if(echiquier.cases[i][j].piece.couleur.equals(echiquier.couleurCourante)){ // Si la piece appartient au joueur courant
				Deplacement a = new Deplacement(echiquier, echiquier.cases[i][j]);
				simuler(a.getDeplPoss()); //Regarde si les deplacement de cette piece permettent de parer l'echec
			}
		}
	}
	return depParer.isEmpty();
}

//permet de simuler le deplacement d'une piece et d'evaluer l'état du jeu suite au deplacement
public void simuler(LinkedList<Case> list){
	for(Case c : list){
		Deplacement d = new Deplacement(echiquier, cI, c);
		if(!d.enEchec()){ //verifie si le deplacement permet de parer l'echec
			depParer.add(c);
		}
	}
}

//Roi pouvant etre mangé par l'adversaire
public boolean misEnEchec(){
 	if( toutDeplAdv.contains(echiquier.trouverPiece("Roi")) )
		return true;
 	else
		return false;
}

public boolean enEchec(){
	if(misEnEchec() && parerEchec()){
		return true;
	}else{
		return false;
	}
}


//
// ------ VALIDATION DES COUPS -------
//

public boolean deplacementValide(Case cF){
	return depPoss.contains(cF);
}

//return deplacments possibles
public LinkedList<Case> getDeplPoss(){
 	return depPoss;
}


}
