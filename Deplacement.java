import java.util.*;

public class Deplacement {
	Plateau echiquier;

	public LinkedList<Case> depPoss= new LinkedList<Case>();
	public LinkedList<Case> toutDeplAdv = new LinkedList<Case>();
    public LinkedList<Case> toutDeplEchec = new LinkedList<Case>();
 	public LinkedList<Case> toutDepl = new LinkedList<Case>(); //tous les deplacement du joueur courant
	public LinkedList<Case> depParer = new LinkedList<Case>();
	Case cI; // @ param case initiale: contient tous les attributs de la case de déplacement


/** @param p: Plateau de jeu
 *  @param c: Case à jouer
 *  Constructeur de la classe Déplacement */
public Deplacement(Plateau p, Case c){
    echiquier = p;

    // si la case contient une pièce, on remplit la liste de déplacement
    if ( c.piece != null ){
        cI=c;

        initialiserListes();
        remplirListDepl(cI, depPoss);
    }
}

/** @param p: Plateau de jeu
 *  @param c1: Case de départ
 *  ???? à compléter */
public Deplacement(Plateau p, Case c1, Case c2){
	echiquier = p;
	p.echangerPiece(c1,c2);

    // si la case contient une pièce, on remplit la liste de déplacement
    if ( c2.piece != null ){
        cI=c2;
        tousDeplacementsAdv();
        remplirListDepl(cI, depPoss);
    }
}

/** Réinitialise les listes de deplacements possibles */
public void initialiserListes(){
	depPoss.clear();
	toutDeplAdv.clear();
    toutDeplEchec.clear();
	toutDepl.clear();
	depParer.clear();
}

/** à documenter */
public void adaptGrille(){
	cI.y=cI.y;
}


/** Vérifie si les coordonées [i,j] passées en paramètre
 *  appartiennent à l'échiquier */
public boolean estDansLeTableau(int i, int j){
	return (i>=0 && j>=0 && i<8 && j<8 );
}


/** Méthode remplissant une liste de déplacement, passée en paramètre.
 *  Les déplacements sont ceux des PIONS
 *  @param list: Liste de déplacements à remplir */

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

/** Méthode remplissant une liste de déplacement, passée en paramètre.
 *  Les déplacements sont ceux des PIONS, lorsqu'ils menacent de prendre une pièce
 *  @param list: Liste de déplacements à remplir */

public void prisePion(LinkedList<Case> list){
		int x= cI.x;
		int y= cI.y;

        String couleurAdverse = (cI.piece.couleur == "blanc" ) ? "noir" : "blanc";

        if(cI.piece.couleur == "blanc" ){
            if( estDansLeTableau(x-1,y-1) && echiquier.cases[x-1][y-1].piece!=null && echiquier.cases[x-1][y-1].piece.couleur==couleurAdverse )
                list.add(echiquier.cases[x-1][y-1]);
            if (estDansLeTableau(x+1,y-1) && echiquier.cases[x+1][y-1].piece!=null && echiquier.cases[x+1][y-1].piece.couleur==couleurAdverse )
                list.add(echiquier.cases[x+1][y-1]);

            // vérifie si le pion peut être pris en passant
            if( estDansLeTableau(x-1,y) && echiquier.cases[x-1][y].piece!=null && echiquier.cases[x-1][y].piece instanceof Pion
                && echiquier.cases[x-1][y].piece.pion_en_passant && echiquier.cases[x-1][y].piece.couleur==couleurAdverse ) {
                list.add(echiquier.cases[x-1][y-1]);
            }


             if( estDansLeTableau(x+1,y) && echiquier.cases[x+1][y].piece!=null && echiquier.cases[x+1][y].piece instanceof Pion
                && echiquier.cases[x+1][y].piece.pion_en_passant && echiquier.cases[x+1][y].piece.couleur==couleurAdverse ) {
                list.add(echiquier.cases[x+1][y-1]);
            }
        } else {
            if( estDansLeTableau(x-1,y+1) && echiquier.cases[x-1][y+1].piece!=null && echiquier.cases[x-1][y+1].piece.couleur==couleurAdverse )
                list.add(echiquier.cases[x-1][y+1]);
            if (estDansLeTableau(x+1,y+1) && echiquier.cases[x+1][y+1].piece!=null && echiquier.cases[x+1][y+1].piece.couleur==couleurAdverse )
                list.add(echiquier.cases[x+1][y+1]);

            // vérifie si le pion peut être pris en passant
            if( estDansLeTableau(x-1,y) && echiquier.cases[x-1][y].piece!=null && echiquier.cases[x-1][y].piece instanceof Pion
                && echiquier.cases[x-1][y].piece.pion_en_passant && echiquier.cases[x-1][y].piece.couleur==couleurAdverse ) {
                list.add(echiquier.cases[x-1][y+1]);
            }


             if( estDansLeTableau(x+1,y) && echiquier.cases[x+1][y].piece!=null && echiquier.cases[x+1][y].piece instanceof Pion
                && echiquier.cases[x+1][y].piece.pion_en_passant && echiquier.cases[x+1][y].piece.couleur==couleurAdverse ) {
                list.add(echiquier.cases[x+1][y+1]);
            }
        }



}

/** Méthode remplissant une liste de déplacement, passée en paramètre.
 *  Les déplacements sont ceux des CAVALIERS
 *  @param list: Liste de déplacements à remplir */

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

/** Méthode remplissant une liste de déplacement, passée en paramètre.
 *  Les déplacements sont ceux des FOUS
 *  @param list: Liste de déplacements à remplir */

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


/** Méthode remplissant une liste de déplacement jusqu'à rencontrer une pièce.
 *  @param list: Liste de déplacements à remplir
 *  @param dep: Déplacements pouvant être ajoutés
 * */

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

			 // si l'on rencontre une pièce, on arrête l'ajout.
             // si la pièce est ADVERSE, on l'ajoute à la liste des déplacements possibles
			 if(echiquier.cases[x][y].piece!=null && !echiquier.cases[x][y].piece.couleur.equals(coul)){
				 list.add(echiquier.cases[x][y]);
			 }
			 break;
		 }
	}
}

/** Méthode remplissant une liste de déplacement, passée en paramètre.
 *  Les déplacements sont ceux des TOURS
 *  @param list: Liste de déplacements à remplir */

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

/** Méthode remplissant une liste de déplacement, passée en paramètre.
 *  Les déplacements sont ceux du ROI
 *  @param list: Liste de déplacements à remplir */

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

        // on récupère la position du roi adverse
        String couleurAdverse = (echiquier.couleurCourante == "blanc") ? "noir" : "blanc";
        Case roiAdverse = echiquier.trouverPiece("Roi", couleurAdverse).getFirst();

        LinkedList <Case> depRoiAdverse = new LinkedList<Case>();

        // si le Roi se trouve à proximité de l'autre Roi, on remplit la liste de déplacement du roi adverse
        // afin d'interdir aux 2 rois de se coller
        if( Math.abs(x-roiAdverse.x) <= 2 || Math.abs(y-roiAdverse.y) <= 2 ){
            int r_x = roiAdverse.x;
            int r_y = roiAdverse.y;

            depRoiAdverse.add(new Case(r_x+1,r_y));
            depRoiAdverse.add(new Case(r_x-1,r_y));
            depRoiAdverse.add(new Case(r_x,r_y-1));
            depRoiAdverse.add(new Case(r_x,r_y+1));
            depRoiAdverse.add(new Case(r_x+1,r_y+1));
            depRoiAdverse.add(new Case(r_x-1,r_y+1));
            depRoiAdverse.add(new Case(r_x+1,r_y-1));
            depRoiAdverse.add(new Case(r_x-1,r_y-1));
        }


        for(Case c: dep ) {
            x = c.x;
            y = c.y;

            // si le déplacement est dans le tableau et  qu'il n'y a pas de pièce (ou que la pièce n'est pas de la même couleur), et que le déplacement est autorisé
            if ( estDansLeTableau(x,y) && !depRoiAdverse.contains(c)
                    && (echiquier.cases[x][y].piece==null || !echiquier.cases[x][y].piece.couleur.equals(coul))
                )
                list.add(echiquier.cases[x][y]);
        }

}

//Rempli le tableau de deplacements possibles
public void remplirListDepl(Case c, LinkedList<Case> list){
	if (c.piece instanceof Pion) {
		depPion(list);
		prisePion(list);
    } else if (c.piece instanceof Cavalier) {
		depCavalier(list);
    } else if (c.piece instanceof Fou) {
		depFou(list);
    } else if (c.piece instanceof Tour) {
		depTour(list);
    }
	if (c.piece instanceof Reine){
		// combinaison d'une tour et d'un fou
		depTour(list);
		depFou(list);
	}
	if (c.piece instanceof Roi){
		depRoi(list);

        if( verifier_petitRoque() ){
            list.add(echiquier.cases[cI.x+2][cI.y]);
            echiquier.cases[cI.x+2][cI.y].roque_possible = true;
        }

        if ( verifier_grandRoque() ){
            list.add(echiquier.cases[cI.x-2][cI.y]);
            echiquier.cases[cI.x-2][cI.y].roque_possible = true;
        }
	}
}

public void remplirListDeplEchec(Case c, LinkedList<Case> list){
	if (c.piece instanceof Pion) {
		prisePion(list); // le pion peut mettre le roi en échec uniquement sur sa diagonale
    } else if (c.piece instanceof Cavalier) {
		depCavalier(list);
    } else if (c.piece instanceof Fou) {
		depFou(list);
    } else if (c.piece instanceof Tour) {
		depTour(list);
    }
	if (c.piece instanceof Reine){
		// combinaison d'une tour et d'un fou
		depTour(list);
		depFou(list);
	}
	if (c.piece instanceof Roi){
		depRoi(list);
	}
}

// ---------- REGLES PARTICULIERES DES ECHECS ----------

public boolean verifier_petitRoque() {
	int x = cI.x;
	int y = cI.y;

    // on récupère les attributs du roi
    Piece roi = echiquier.trouverPiece("Roi", cI.piece.couleur).getFirst().piece;

    // si le roi a bougé, on ne peut pas roquer
    if( roi.deja_bougee )
        return false;

    // de même pour la tour
    if ( echiquier.cases[x+3][y].piece == null )
        return false;
    else if ( echiquier.cases[x+3][y].piece.deja_bougee )
        return false;

    return ( echiquier.cases[x+1][y].piece == null &&
                echiquier.cases[x+2][y].piece == null );
}

public boolean verifier_grandRoque() {
	int x = cI.x;
	int y = cI.y;

    // on récupère les attributs du roi
    Piece roi = echiquier.trouverPiece("Roi", cI.piece.couleur).getFirst().piece;

    // si le roi a bougé, on ne peut pas roquer
    if( roi.deja_bougee )
        return false;

    // de même pour la tour
    if ( echiquier.cases[x-4][y].piece == null )
        return false;
    else if ( echiquier.cases[x-4][y].piece.deja_bougee )
        return false;

    return ( echiquier.cases[x-1][y].piece == null &&
                echiquier.cases[x-2][y].piece == null &&
                    echiquier.cases[x-3][y].piece == null );
}

public void promotion(){
	String c = cI.piece.couleur;

	if (cI.piece instanceof Pion && c == "blanc" && cI.y==0)
        cI.piece=new Reine(c);
    else if( cI.piece instanceof Pion && c == "noir" && cI.y==7 )
        cI.piece=new Reine(c);
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

//Rempli tous les mises en échec possible
public void toutDeplacementsEchec(){
    for(Case[] cases : echiquier.cases){
		for(Case c : cases){
			if((c.piece != null) && (c.piece.couleur != cI.piece.couleur)){
				remplirListDeplEchec(c, toutDeplEchec);
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
				simuler(a; //Regarde si les deplacement de cette piece permettent de parer l'echec
			}
		}
	}
	return depParer.isEmpty();
}

//permet de simuler le deplacement d'une piece et d'evaluer l'état du jeu suite au deplacement
public void simuler(Deplacement d){
	for(Case c : d.getDeplPoss()){
		Deplacement a = new Deplacement(echiquier, d.cI, c);
		if(!a.enEchec()){ //verifie si le deplacement permet de parer l'echec
			depParer.add(c);
		}
	}
}

//Roi pouvant etre mis en échec par l'adversaire
public boolean misEnEchec(){
 	if( toutDeplAdv.contains(echiquier.trouverPiece("Roi",echiquier.couleurCourante).getFirst()) )
		return true;
 	else
		return false;
}


//Echec et mate
public boolean enEchec(){
	if(misEnEchec() && !parerEchec()){
		return true;
	}else{
		return false;
	}
}


//
// ------ VALIDATION DES COUPS -------
//


//return deplacments possibles
public LinkedList<Case> getDeplPoss(){
 	return depPoss;
}


}
