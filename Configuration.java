public class Configuration {
    private double seuil;
    private ComparateurDeNoms comparateur;
    private PretraiteurNom[] pretraiteurs;
    
    public Configuration(double seuil, ComparateurDeNoms comparateur) {
        this.seuil = seuil;
        this.comparateur = comparateur;
        this.pretraiteurs = new PretraiteurNom[5];
    }
    
    public double getSeuil() { 
        return seuil; 
    }
    
    public void setSeuil(double seuil) { 
        this.seuil = seuil; 
    }
    
    public ComparateurDeNoms getComparateur() { 
        return comparateur; 
    }
    
    public void setComparateur(ComparateurDeNoms comparateur) { 
        this.comparateur = comparateur; 
    }
    
    public void addPretraiteur(PretraiteurNom pretraiteur) {
        for (int i = 0; i < pretraiteurs.length; i++) {
            if (pretraiteurs[i] == null) {
                pretraiteurs[i] = pretraiteur;
                return;
            }
        }
    }
    
    public PretraiteurNom[] getPretraiteurs() { 
        return pretraiteurs; 
    }
}
