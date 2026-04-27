public class Resultat {
    private String id;
    private String nomTrouve;
    private String sourceFichier;
    private double score;

    public Resultat(String id, String nomTrouve, String sourceFichier, double score) {
        this.id = id;
        this.nomTrouve = nomTrouve;
        this.sourceFichier = sourceFichier;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public String getNomTrouve() {
        return nomTrouve;
    }

    public String getSourceFichier() {
        return sourceFichier;
    }

    public double getScore() {
        return score;
    }

    public String afficher() {
        String result = "Score: " + score + ", nom trouvé: " + nomTrouve + ", id: " + id + ", source: " + sourceFichier;
        System.out.println(result);
        return result;
    }

    @Override
    public String toString() {
        return afficher();
    }
}