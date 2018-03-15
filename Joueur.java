public class Joueur {
    
    private String nom;
    private String couleurCourante;
    private int nbPartiesGagnees;
    private int nbPartiesPerdues;
    private int nbPartiesNulles;
    private int score;
        
    public Joueur(String nom) {
        this.nom = nom;
        
        nbPartiesGagnees = 0;
        nbPartiesPerdues = 0;
        nbPartiesNulles = 0;
        
        score = 0;
    }
    
    public void definirCouleur(String c){
		couleurCourante = c;
	}
	
	public String getCouleur(){ return couleurCourante; }
    
    /**
     * Met à jour le score
     * @param -1, 0 ou 1 pour une défaite, partie nulle ou victoire
     * */
    public void updateScore(int add){
        
        switch(add){
            case 0:
                nbPartiesNulles++;
            break;
            case 1:
                nbPartiesGagnees++;
            break;
            case -1:
                nbPartiesPerdues++;
            break;
        }
        
        score += add;
    }
    
    public String toString() {
        return "Nom du joueur: "+nom+"\n"+
        "Nombre de parties gagnées: "+nbPartiesGagnees+"\n Nombre de parties perdues: "+nbPartiesPerdues+
        "\n Nombre de match nul: "+nbPartiesNulles;      
    }
    

    
}
