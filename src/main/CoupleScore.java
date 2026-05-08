package main;

public class CoupleScore {

    private final Nom    source;
    private final Nom    cible;
    private final double score;

    public CoupleScore(Nom source, Nom cible, double score) {
        this.source = source;
        this.cible  = cible;
        this.score  = score;
    }

    public Nom    getSource() { return source; }
    public Nom    getCible()  { return cible;  }
    public double getScore()  { return score;  }

    @Override
    public String toString() {
        return String.format("CoupleScore{source='%s', cible='%s', score=%.4f}",
                source.getNomOriginal(), cible.getNomOriginal(), score);
    }
}