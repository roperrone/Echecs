public class Jeu {
    private Joueur[] tabJoueur;
    static public int currentPlayer;
    private Plateau p;
    
    public Jeu( Joueur j1, Joueur j2 ) {
        tabJoueur = new Joueur[2];
        p = new Plateau();
        
        tabJoueur[0] = j1;
        tabJoueur[1] = j2;
    }
        
    public void start() {
   //     do { 
          
          currentPlayer = (int) Math.random()*2; // 0 ou 1
          
          Joueur blanc = tabJoueur[currentPlayer];
          Joueur noir = tabJoueur[Math.abs(currentPlayer-1)];
          
          blanc.definirCouleur("blanc");
          noir.definirCouleur("noir");	    
	      
	      // ajouter listener -> MouseListner position -> méthode pour déduire la case et ses coordonées (x,y)
	 //     p.selectCase()   
	 //     p.deplacerPiece(x,y) // (x,y) déplacement case final
	      
	      
	      //callback de déplacer pièce
            
       // } while (!partieTerminee()); // tant que la partie n'est pas terminée
    }
    
    public boolean partieTerminee() {
		boolean b = false;
		
		// le joueur courrant est en échec et mat ou la partie est nulle
		if( p.echecEtMat() || p.estNulle() )
			b = true;
		
        return b;
	}
	
	
	
}
