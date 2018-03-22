import java.util.*;
public class Deplacement {
	LinkedList<Case>depPoss= new LinkedList<Case>();
	LinkedList<Case> toutDeplAdv = new LinkedList<Case>();
	LinkedList<Case> toutDepl = new LinkedList<Case>(); //tous les deplacement du joueur courant
	Plateau echiquier;
	boolean echecEtMat=false;
	boolean pat=false;
	Case cI; // @ param case initiale

public Deplacement(Plateau p, Case c){
    this.echiquier = p;
    this.cI=c;
		this.tousDeplacementsAdv();
		this.remplirListDepl(cI, depPoss);
  }

public void depPion(){
	if (cI.y==1 && echiquier.Case[cI.x][cI.y+1]==null && echiquier.Case[cI.x][cI.y+2]==null){
		this.list.add(echiquier.Case[cI.x][cI.y+1]);
		this.list.add(echiquier.Case[cI.x][cI.y+2]);
	}
	if (cI.y==6 && echiquier.Case[cI.x][cI.y-1]==null && echiquier.Case[cI.x][cI.y-2]==null){
		this.list.add(echiquier.Case[cI.x][cI.y-1]);
		this.list.add(echiquier.Case[cI.x][cI.y-2]);
	}
	if (cI.piece.couleur=="blanc" && echiquier.Case[cI.x][cI.y+1].piece==null)
		this.list.add(echiquier.Case[cI.x][cI.y+1]);

	if (cI.piece.couleur=="noir" && echiquier.Case[cI.x][cI.y-1].piece==null)
		this.list.add(echiquier.Case[cI.x][cI.y-1]);
	}

public void depCavalier(LinkedList<Case> list){
	LinkedList <Case> dep = new LinkedList<Case>();
	dep.add(new Case(cI.x+1,cI.y+2));
	dep.add(new Case(cI.x+1,cI.y-2));
	dep.add(new Case(cI.x-1,cI.y+2));
	dep.add(new Case(cI.x-1,cI.y-2));
	dep.add(new Case(cI.x+2,cI.y+1));
	dep.add(new Case(cI.x+2,cI.y-1));
	dep.add(new Case(cI.x-2,cI.y+1));
	dep.add(new Case(cI.x-2,cI.y-1));
	int x=0;
	int y=0;
	for(int i=0; i<dep.size() ; i++){
		x=dep.get(i).x;
		y=dep.get(i).y;
		if (x<=7 && x>=0 && y>=0 && y<=7 && echiquier.Case[x][y].piece.couleur!=cI.piece.couleur)
			this.list.add(dep.get(i));
	}
}

public void depFou(){
	LinkedList<Case>depHD= new LinkedList<Case>();
	LinkedList<Case>depBD= new LinkedList<Case>();
	LinkedList<Case>depHG= new LinkedList<Case>();
	LinkedList<Case>depBG= new LinkedList<Case>();
	int a = Math.min(cI.x,cI.y);
	for (int i=1; i<7-a ; i++){
		if (cI.y+i<=7 && cI.x+i<=7)
			depHD.add(new Case(cI.x+i,cI.y+i));
		if (cI.y-i>=0 && cI.x+i<=7)
			depBD.add(new Case(cI.x+i,cI.y-i));
		if (cI.x-i>=0 && cI.y+i<=7)
			depHG.add(new Case(cI.x-i,cI.y+i));
		if (cI.y-i>=0 && cI.y-i>=0)
			depBG.add(new Case(cI.x-i,cI.y-i));
	}
	verif(depHD);
	verif(depBD);
	verif(depHG);
	verif(depBG);
}

public void verif(LinkedList <Case> depHD){
	if(echiquier.Case[depHD.get(0).x][depHD.get(0).y].couleur!=cI.piece.couleur||echiquier.Case[depHD.get(0).x][depHD.get(0).y]==null)
		this.list.add(depHD.get(0));
	for(int i=1; i<depHD.size() ; i++){
		if (echiquier.Case[depHD.get(i-1).x][depHD.get(i-1).y]==null&&(echiquier.Case[depHD.get(i).x][depHD.get(i).y].couleur!=cI.piece.couleur||echiquier.Case[depHD.get(i).x][depHD.get(i).y]==null))
			this.list.add(depHD.get(i));
	}
}

public void depTour(){
	LinkedList<Case>depH= new LinkedList<Case>();
	LinkedList<Case>depB= new LinkedList<Case>();
	LinkedList<Case>depD= new LinkedList<Case>();
	LinkedList<Case>depG= new LinkedList<Case>();
	int a = Math.min(cI.x,cI.y);
	for (int i=1; i<7-a ; i++){
		if (cI.y+i<=7)
			depH.add(new Case(cI.x,cI.y+i));
		if (cI.x+i<=7)
			depD.add(new Case(cI.x+i,cI.y));
		if (cI.x-i>=0)
			depG.add(new Case(cI.x-i,cI.y));
		if (cI.y-i>=0)
			depB.add(new Case(cI.x,cI.y-i));
	}
	verif(depH);
	verif(depB);
	verif(depD);
	verif(depG);
}

public void depRoi(){
	int x=0;
	int y=0;
	LinkedList<Case> dep = new LinkedList<Case>();
	dep.add(new Case(cI.x+1,cI.y));
	dep.add(new Case(cI.x-1,cI.y));
	dep.add(new Case(cI.x+1,cI.y+1));
	dep.add(new Case(cI.x-1,cI.y+1));
	dep.add(new Case(cI.x,cI.y+1));
	dep.add(new Case(cI.x+1,cI.y-1));
	dep.add(new Case(cI.x-1,cI.y-1));
	dep.add(new Case(cI.x,cI.y-1));
	for(int i=0; i<dep.size() ; i++){
		x=dep.get(i).x;
		y=dep.get(i).y;
		if (x<=7 && x>=0 && y>=0 && y<=7  && !toutDeplAdv.contains(dep.get(i)) && (echiquier.Case[x][y]==null || echiquier.Case[x][y].couleur!=cI.piece.couleur))
			this.list.add(dep.get(i));
	}
}

public boolean misEnEchec(){
	if(toutDeplAdv.contains(p.trouverPiece("Roi"))) return true;
	else return false;
}

public void echecEtMat(){
	if(misEnEchec() && remplir)

	return !echecEtMat;
}

//Rempli tous les deplacements possibles de l'adversaire
public void tousDeplacementsAdv(){
	for(Case c : echiquier.cases){
		if((c.piece != null) && (c.piece.couleur != cI.piece.couleur)){
			remplirListDepl(c, toutDeplAdv);
		}
}

//Remplir tous les deplacements possibles du joueur
public void tousDeplacements(){
	for(Case c : echiquier.cases){
		if((c.piece != null) && (c.piece.couleur == cI.piece.couleur)){
			remplirListDepl(c, toutDepl);
		}
}

public void parerEchec(){
	tousDeplacements();
	for (Case c :toutDepl){

	}

}

public void remplirListDepl(Case c, LinkedList<Case> list){
	if (this.c.piece instanceof Pion)
		depPion(list);
	if (this.c.piece instanceof Cavalier)
		depCavalier(list);
	if (this.c.piece instanceof Fou)
		depFou(list);
	if (this.c.piece instanceof Tour)
		depTour(list);
	if (this.c.piece instanceof Reine){
		depTour(list);
		depFou(list);
	}
	if (this.c.piece instanceof Roi){
		depRoi(list);
	}
}

public boolean deplacementValide(Case cF){

}


}
