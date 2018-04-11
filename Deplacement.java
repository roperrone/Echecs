import java.util.*;

public class Deplacement {
	Plateau echiquier;
	public LinkedList<Case> depPoss= new LinkedList<Case>();
	public LinkedList<Case> toutDeplAdv = new LinkedList<Case>();
 	public LinkedList<Case> toutDepl = new LinkedList<Case>(); //tous les deplacement du joueur courant
	public LinkedList<Case> depParer = new LinkedList<Case>();
	Case cI; // @ param case initiale: contient tous les attributs de la case de déplacement

public Deplacement(Plateau p, Case c){
    echiquier = p;

    if ( c.piece != null ){
        cI=c;

        initialiserListes();
        //tousDeplacementsAdv();
        remplirListDepl(cI, depPoss);
    }
    //adaptGrille();
}

public Deplacement(Plateau p, Case c1, Case c2){
	echiquier = p;
	p.echangerPiece(c1,c2);

    if ( c2.piece != null ){
        cI=c2;
        tousDeplacementsAdv();
        remplirListDepl(cI, depPoss);
    }
    //adaptGrille();
}

//Vide les listes des deplacements possibles
public void initialiserListes(){
	depPoss.clear();
	toutDeplAdv.clear();
	toutDepl.clear();
	depParer.clear();
}
public void adaptGrille(){
	cI.y=cI.y;
}
//
// ---------- GESTION DES DEPLACEMENTS GENERAUX DES PIECES ---------
//
public void depPion(LinkedList<Case> list){
	int x= cI.x;
	int y= cI.y;
    try {
        // le pion est sur la première ligne: il peut avancer de un ou de deux
        if (y==1 && echiquier.cases[x][2].piece==null && echiquier.cases[x][3].piece==null){
            list.add(echiquier.cases[x][2]);
            list.add(echiquier.cases[x][3]);
        }
        else if (y==6 && echiquier.cases[x][5].piece==null && echiquier.cases[x][5].piece==null){
            list.add(echiquier.cases[x][4]);
            list.add(echiquier.cases[x][5]);
        } // sinon le pion ne peut avancer que d'une seule case à la fois
        else if (cI.piece.couleur=="noir" && echiquier.cases[x][y+1].piece==null && estDansLeTableau(x,y+1))
            list.add(echiquier.cases[x][y+1]);
        else if (cI.piece.couleur=="blanc" && echiquier.cases[x][y-1].piece==null && estDansLeTableau(x,y-1))
            list.add(echiquier.cases[x][y-1]);
	 } catch( Exception e ){}
}
public void prisePion(LinkedList<Case> list){
		int x= cI.x;
		int y= cI.y;
		  // gestion des prises des pions
		if (cI.piece.couleur=="noir"){
			if(estDansLeTableau(x+1,y+1) && echiquier.cases[x+1][y+1].piece!=null && echiquier.cases[x+1][y+1].piece.couleur=="blanc" )
				list.add(echiquier.cases[x+1][y+1]);
			if (estDansLeTableau(x-1,y+1) && echiquier.cases[x-1][y+1].piece!=null && echiquier.cases[x-1][y+1].piece.couleur=="blanc")
				list.add(echiquier.cases[x-1][y+1]);
		}
		if (cI.piece.couleur=="blanc"){
			if( estDansLeTableau(x-1,y-1) && echiquier.cases[x-1][y-1].piece!=null && echiquier.cases[x-1][y-1].piece.couleur=="noir" )	
				list.add(echiquier.cases[x-1][y-1]);
			if (estDansLeTableau(x+1,y-1) && echiquier.cases[x+1][y-1].piece!=null && echiquier.cases[x+1][y-1].piece.couleur=="noir" )	
				list.add(echiquier.cases[x+1][y-1]);
		}	
}  

public void depCavalier(LinkedList <Case> list ){

        // on envisage tous les déplacements possibles
        LinkedList <Case> dep = new LinkedList<Case>();
        int x=cI.x;
        int y=cI.y;
        dep.add(new Case(x+1,y+2));
        dep.add(new Case(x+1,y-2));
        dep.add(new Case(x-1,y+2));
        dep.add(new Case(x-1,y-2));
        dep.add(new Case(x+2,y+1));
        dep.add(new Case(x+2,y-1));
        dep.add(new Case(x-2,y+1));
        dep.add(new Case(x-2,y-1));

        // on élimine les déplacements qui sortent du tableau (de l'échiquier)
        String coul =cI.piece.couleur;
        for(Case c: dep) {
            x=c.x; 	y=c.y;
            // si la pièce est dans le tableau et que la couleur de la pièce sur la case où l'on se déplace
            // est différente de la couleur du joueur ou ne contient pas de pièce, alors on ajoute ce déplacement aux déplacements possibles
            if (estDansLeTableau(x,y) && (echiquier.cases[x][y].piece == null || !echiquier.cases[x][y].piece.couleur.equals(coul)))
                    list.add(echiquier.cases[x][y]);
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

        int x = cI.x;
        int y = cI.y;
      // Le nombre maximal de coup possible dans chaque direction: 7-a


        for (int i=1; i<8 ; i++){
            if (estDansLeTableau (y+i,x+i))
                depHD.add(new Case(x+i,y+i));
            if (estDansLeTableau(y-i,x+i))
                depBD.add(new Case(x+i,y-i));
            if (estDansLeTableau(x-i,y+i))
                depHG.add(new Case(x-i,y+i));
            if (estDansLeTableau(x-i,y-i))
                depBG.add(new Case(x-i,y-i));
        }

        verif(depHD, list);
        verif(depBD, list);
        verif(depHG, list);
        verif(depBG, list);
}


public void verif(LinkedList <Case> dep, LinkedList <Case> list){
  String coul = cI.piece.couleur;
  int x=0;
  int y=0;
  for(Case c: dep){
		 x=c.x;
		 y=c.y;
		 // si la case correspondante ne contient pas de pièce
		 if(echiquier.cases[x][y].piece==null) {
			 list.add(echiquier.cases[x][y]);
		 } else {
			 // la couleur de la pièce sur la case est différente de la couleur du joueur
			 if(echiquier.cases[x][y].piece!=null && !echiquier.cases[x][y].piece.couleur.equals(coul)){
				 list.add(echiquier.cases[x][y]);
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

            int x = cI.x;
            int y = cI.y;

            // Le nombre maximal de coup possible dans chaque direction: 7-a

            for (int i=1; i<8 ; i++){
                if (estDansLeTableau (y+i,x))
                    depB.add(new Case(x,y+i));
                if (estDansLeTableau(y,x+i))
                    depD.add(new Case(x+i,y));
                if (estDansLeTableau(x-i,y))
                    depG.add(new Case(x-i,y));
                if (estDansLeTableau(y-i,x))
                    depH.add(new Case(x,y-i));
            }

            verif(depH, list);
            verif(depB, list);
            verif(depD, list);
            verif(depG, list);

}

public void depRoi( LinkedList <Case> list ){

        int x=cI.x;
        int y=cI.y;
		String coul= cI.piece.couleur;
        LinkedList<Case> dep = new LinkedList<Case>();

     // on ajoute les 8 déplacements possibles
        dep.add(new Case(x+1,y));
        dep.add(new Case(x-1,y));
        dep.add(new Case(x,y-1));
        dep.add(new Case(x,y+1));
        dep.add(new Case(x+1,y+1));
        dep.add(new Case(x-1,y+1));
        dep.add(new Case(x+1,y-1));
        dep.add(new Case(x-1,y-1));

        for(Case c: dep ) {
            x = c.x;
            y = c.y;

            // si le déplacement est dans le tableau et qu'il n'y a pas de pièce (ou que la pièce n'est pas de la même couleur)
            if (estDansLeTableau(x,y) && (echiquier.cases[x][y].piece==null || !echiquier.cases[x][y].piece.couleur.equals(coul)))
                list.add(echiquier.cases[x][y]);
        }

}

//Rempli le tableau de deplacements possibles
public void remplirListDepl(Case c, LinkedList<Case> list){
	if (c.piece instanceof Pion)
		depPion(list);
		prisePion(list);
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



public boolean petitRoque() {
	int x = cI.x;
	int y = cI.y;
	Piece tour= echiquier.cases[x+3][y].piece;
	// ajouter la condition Aucune pièce ennemie ne doit contrôler les deux cases que le Roi parcourt pour roquer.
	if (cI.piece instanceof Roi && x==4 && (y==0 || y==7) && tour instanceof Tour && echiquier.cases[x+1][y]==null && echiquier.cases[x+2][y]==null && enEchec()==false ){
		echiquier.cases[x+2][y].piece= cI.piece;
		echiquier.cases[x][y].piece = null;
		echiquier.cases[x+1][y].piece = tour;
		echiquier.cases[x+3][y].piece = null;

		return true;
	}
	else{
		System.out.println ("Vous ne pouvez pas effectuer un petit roque"); // a revoir (i.e fenetre d'erreur)
		return false;
	}
}

public boolean grandRoque() {
	int x = cI.x;
	int y = cI.y;
	Piece tour= echiquier.cases[0][y].piece;
	// ajouter la condition Aucune pièce ennemie ne doit contrôler les deux cases que le Roi parcourt pour roquer.
	if (cI.piece instanceof Roi && x==4 && (y==0 || y==7) && tour instanceof Tour && echiquier.cases[x-1][y]==null && echiquier.cases[x-2][y]==null && echiquier.cases[x-3][y]==null && enEchec()==false ){
		echiquier.cases[x-2][y].piece = cI.piece;
		echiquier.cases[x][y].piece = null;
		echiquier.cases[x-1][y].piece = tour;
		echiquier.cases[0][y].piece = null;

		return true;
	}
	else{
		System.out.println ("Vous ne pourvez pas effectuer un grand roque"); // a revoir (i.e fenetre d'erreur)
		return false;
	}
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
 	if( toutDeplAdv.contains(echiquier.trouverPiece("Roi").getFirst()) )
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
	if(echiquier.trouverPiece("roi").contains(cI) && echiquier.trouverPiece("tour").contains(cF)){
		return (grandRoque() || petitRoque());
	}
	return depPoss.contains(cF);
}

//return deplacments possibles
public LinkedList<Case> getDeplPoss(){
 	return depPoss;
}


}
