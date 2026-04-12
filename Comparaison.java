public abstract class Comparaison {
    protected TraitementNom traitement;

    public Comparaison(TraitementNom traitement) {
        this.traitement = traitement;
    }

  
    protected String preparer(String nom) {
        if (traitement != null) return traitement.traiter(nom);
       if(nom == null) return "";
         return nom;
    }

    public abstract double comparer(String a, String b);
}