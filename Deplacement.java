import java.util.*;

public class Deplacement {
	Plateau echiquier;

	public ArrayList <Case> depPossTemp = new ArrayList <Case>();
	public ArrayList<Case> depPoss= new ArrayList<Case>();
	public ArrayList<Case> toutDeplAdv = new ArrayList<Case>();
	public ArrayList<Case> toutDeplEchec = new ArrayList<Case>();
 	public ArrayList<Case> toutDepl = new ArrayList<Case>(); //tous les deplacement du joueur courant
	public ArrayList<Case> depParer = new ArrayList<Case>();
	Case cI; // @ param case initiale: contient tous les attributs de la case de déplacement
	Case cF; //
	int score;

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
        simulerProtection(cI, depPoss);
    }
}

/** @param p: Plateau de jeu
 *  @param c1: Case de départ
 *  ???? à compléter */
public Deplacement(Plateau p, Case c1, Case c2){
	echiquier = p;
	score = echiquier.estimer();

	cI=c1;
	cF=c2;
	initialiserListes();
  			//remplirListDepl(cI, depPoss);

}

/** Réinitialise les listes de deplacements possibles */
public void initialiserListes(){
	depPossTemp.clear();
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

public void depPion(ArrayList<Case> list){
	int x= cI.x;
	int y= cI.y;

    try {
        // le pion est sur la première ligne: il peut avancer de un ou de deux
        if (y==1 && echiquier.cases[x][2].piece==null && echiquier.cases[x][3].piece==null && cI.piece.couleur == "noir" ){
            list.add(echiquier.cases[x][2]);
            list.add(echiquier.cases[x][3]);
        }
        else if (y==6 && echiquier.cases[x][5].piece==null && echiquier.cases[x][4].piece==null && cI.piece.couleur == "blanc"){
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

public void prisePion(ArrayList<Case> list){
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
 *  Les déplacements sont ceux du roi adverse menacé par les PIONS.
 *  @param list: Liste de déplacements à remplir */

public void pionMenaceRoi(ArrayList<Case> list){
		int x= cI.x;
		int y= cI.y;

        if(cI.piece.couleur == "blanc" && echiquier.couleurCourante == "noir" ){
            if( estDansLeTableau(x-1,y-1) )
                list.add(echiquier.cases[x-1][y-1]);
            if (estDansLeTableau(x+1,y-1) )
                list.add(echiquier.cases[x+1][y-1]);
        } else if ( cI.piece.couleur == "noir" && echiquier.couleurCourante == "blanc") {
            if( estDansLeTableau(x-1,y+1) )
                list.add(echiquier.cases[x-1][y+1]);
            if (estDansLeTableau(x+1,y+1) )
                list.add(echiquier.cases[x+1][y+1]);
        }
}

/** Méthode remplissant une liste de déplacement, passée en paramètre.
 *  Les déplacements sont ceux des CAVALIERS
 *  @param list: Liste de déplacements à remplir */

public void depCavalier(ArrayList <Case> list ){

        // on envisage tous les déplacements possibles
        ArrayList <Case> dep = new ArrayList<Case>();

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

public void depFou( ArrayList <Case> list ){

      //Initialise 4 tableaux représentant les sens de déplacement possible en  diagonale: HD (haut-droit), ...
        ArrayList<Case>depHD= new ArrayList<Case>();
        ArrayList<Case>depBD= new ArrayList<Case>();
        ArrayList<Case>depHG= new ArrayList<Case>();
        ArrayList<Case>depBG= new ArrayList<Case>();

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

public void verif(ArrayList <Case> dep, ArrayList <Case> list){
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

public void depTour( ArrayList <Case> list ){

        //Initialise 4 tableaux représentant les sens de déplacement possible en  diagonale: H (haut), ...
            ArrayList<Case>depH= new ArrayList<Case>();
            ArrayList<Case>depB= new ArrayList<Case>();
            ArrayList<Case>depD= new ArrayList<Case>();
            ArrayList<Case>depG= new ArrayList<Case>();

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

public void depRoi( ArrayList <Case> list ){

        int x=cI.x;
        int y=cI.y;

		String coul= cI.piece.couleur;
        ArrayList<Case> dep = new ArrayList<Case>();

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
        Case roiAdverse = echiquier.trouverPiece("Roi", couleurAdverse).get(0);

        ArrayList <Case> depRoiAdverse = new ArrayList<Case>();

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


        ArrayList <Case> tmp = new ArrayList <Case>();
        for(Case c: dep ) {
            x = c.x;
            y = c.y;

            // si le déplacement est dans le tableau et  qu'il n'y a pas de pièce (ou que la pièce n'est pas de la même couleur), et que le déplacement est autorisé
            if ( estDansLeTableau(x,y) && !depRoiAdverse.contains(c)
                    && (echiquier.cases[x][y].piece==null || !echiquier.cases[x][y].piece.couleur.equals(coul))
                ) {
                tmp.add(echiquier.cases[x][y]);
            }
        }

        // le roi ne peut se déplacer que sur des cases qui ne sont pas mises en échec
        for( Case c: tmp ){
            Plateau eq = echiquier.simulateMove(cI, c);
            Deplacement d = new Deplacement(eq, c);

            if(!d.misEnEchec()) {
                list.add(c);
            }
        }

}

//Rempli le tableau de deplacements possibles
public void remplirListDepl(Case c, ArrayList<Case> list){

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

	Case roi = echiquier.trouverPiece("Roi", echiquier.couleurCourante).get(0);
	ArrayList<Case> tmp = new ArrayList<Case>();


	// Si le joueur est en echec on verifie si son deplacement permet de parer l'echec
	if( roi.enEchec ){
		for( Case a : list ) {
			Plateau p = echiquier.simulateMove(c,a);

			if(!p.trouverPiece("Roi", echiquier.couleurCourante).isEmpty()){
				Case r = p.trouverPiece("Roi", echiquier.couleurCourante).get(0);

				Deplacement d = new Deplacement(p, a);

				if(!d.misEnEchec()){
					tmp.add(a);
				}
			}

		}

		list.clear();
		list.addAll(tmp);
	}


}

public void remplirListDeplEchec(Case c, ArrayList<Case> list){
    this.cI = c;

	if (c.piece instanceof Pion) {
		pionMenaceRoi(list); // le pion peut mettre le roi en échec uniquement sur sa diagonale
    } else if (c.piece instanceof Cavalier) {
		depCavalier(list);
    } else if (c.piece instanceof Fou) {
		depFou(list);
    } else if (c.piece instanceof Tour) {
		depTour(list);
    } else if (c.piece instanceof Reine){
		// combinaison d'une tour et d'un fou
		depTour(list);
		depFou(list);
	}
}

// ---------- REGLES PARTICULIERES DES ECHECS ----------

public boolean verifier_petitRoque() {
	int x = cI.x;
	int y = cI.y;

    // on récupère les attributs du roi
    Piece roi = echiquier.trouverPiece("Roi", cI.piece.couleur).get(0).piece;

    // si le roi a bougé, on ne peut pas roquer
    if( roi.deja_bougee )
        return false;

    // de même pour la tour
    if ( echiquier.cases[x+3][y].piece == null )
        return false;
    else if ( echiquier.cases[x+3][y].piece.deja_bougee )
        return false;

	for( int i=0; i<=2; i++ ) {
		Plateau eq = echiquier.simulateMove(cI, echiquier.cases[x+i][y]);
		Deplacement d = new Deplacement(eq, echiquier.cases[x+i][y]);

		if( d.misEnEchec() )
			return false;
	}

    return ( echiquier.cases[x+1][y].piece == null &&
                echiquier.cases[x+2][y].piece == null );
}

public boolean verifier_grandRoque() {
	int x = cI.x;
	int y = cI.y;

    // on récupère les attributs du roi
    Piece roi = echiquier.trouverPiece("Roi", cI.piece.couleur).get(0).piece;

    // si le roi a bougé, on ne peut pas roquer
    if( roi.deja_bougee )
        return false;

    // de même pour la tour
    if ( echiquier.cases[x-4][y].piece == null )
        return false;
    else if ( echiquier.cases[x-4][y].piece.deja_bougee )
        return false;

	for( int i=0; i<=3; i++ ) {
		Plateau eq = echiquier.simulateMove(cI, echiquier.cases[x-i][y]);
		Deplacement d = new Deplacement(eq, echiquier.cases[x-i][y]);

		if( d.misEnEchec() )
			return false;
	}

    return ( echiquier.cases[x-1][y].piece == null &&
                echiquier.cases[x-2][y].piece == null &&
                    echiquier.cases[x-3][y].piece == null );
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
			if((c.piece != null) && (c.piece.couleur != echiquier.couleurCourante)){
				remplirListDeplEchec(c, this.toutDeplEchec);
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


// Calcule et retourne la liste de tous les deplacements possible pour parer l'echec pârmis toutes les pieces du joueur
public boolean parerEchec(){
	for (int i=0;i<echiquier.cases.length;i++) {
		for (int j=0;j<echiquier.cases[0].length;j++) {
			if(echiquier.cases[i][j].piece.couleur.equals(echiquier.couleurCourante)){ // Si la piece appartient au joueur courant
				Deplacement a = new Deplacement(echiquier, echiquier.cases[i][j]);
				simuler(a); //Regarde si les deplacement de cette piece permettent de parer l'echec
			}
		}
	}

	return depParer.isEmpty();
}

//permet de simuler le deplacement d'une piece et d'evaluer l'état du jeu suite au deplacement
public void simuler(Deplacement d){
	for(Case c : d.getDeplPoss()){

		Plateau eq = echiquier.simulateMove(d.cI, c);
        Deplacement a = new Deplacement(eq, c);

		if(!a.misEnEchec()){ //verifie si le deplacement permet de parer l'echec
			depParer.add(c);
		}
	}
}

//permet de simuler un deplacemet et de vérifier si ce dernier ne met pas le roi en echec
public void simulerProtection (Case cD , ArrayList <Case> list ) {
	for(Case c : list){

		Plateau eq = echiquier.simulateMove(cD, c);
        Deplacement a = new Deplacement(eq, c);

		if(a.misEnEchec()){ //verifie si le deplacement met le roi l'echec
			list.clear();
			break;
		}
	}
}


//Roi pouvant etre mis en échec par l'adversaire
public boolean misEnEchec(){
    toutDeplacementsEchec();

 	if( getDeplEchec().contains(echiquier.trouverPiece("Roi", echiquier.couleurCourante).get(0)) )
		return true;
 	else
		return false;
}


// Est en Echec et mate
public boolean enEchecEtMate(){
	if(misEnEchec() && echiquier.deplacementsPossibles().isEmpty()){
		return true;
	}else{
		return false;
	}
}

public int estimer(){
	return echiquier.estimer();
}
//
// ------ VALIDATION DES COUPS -------
//


//return deplacements possibles
public ArrayList<Case> getDeplPoss(){
 	return depPoss;
}

public ArrayList<Case> getDeplEchec(){
 	return toutDeplEchec;
}


public String toString(){
	String s ="* ";
	s += (cI == null) ? " - " : cI.toString();
	s += " vers ";
	s += (cF == null) ? " - " : cF.toString();

	return s;
}


}
