public class Jeu {
    private Joueur[] tabJoueur;
    public Plateau plateau;
    public int currentPlayer;
    
    public Jeu( Joueur j1, Joueur j2 ) {
        tabJoueur = new Joueur[2];
        plateau = new Plateau();
        
        tabJoueur[0] = j1;
        tabJoueur[1] = j2;
    }
    
    public void tourSuivant(){        
        // réinitialise l'attribut boolean pion en passant après un tour
        if( !plateau.pionEnPassant.isEmpty() && plateau.couleurCourante == plateau.pionEnPassant.get(0).couleur ) {
            plateau.pionEnPassant.get(0).pion_en_passant = false;
            plateau.pionEnPassant.remove(0);
        }
    }
    
    public void start() {
          Joueur JoueurBlanc = tabJoueur[0];
          Joueur JoueurNoir = tabJoueur[1];
          
          JoueurBlanc.definirCouleur("blanc");
          JoueurNoir.definirCouleur("noir");
    }
    
    public boolean partieTerminee() {
		boolean b = false;

        return b;
	}
	
	
	
}
