package comparaison;

import main.Nom;
import java.util.List;


public class ComparaisonTokens implements ComparateurDeNoms {

    private final ComparaisonLevenshtein levenshtein = new ComparaisonLevenshtein();

    @Override
    public double comparer(Nom a, Nom b) {
        if (a == null || b == null) return 0.0;

        List<String> tokensA = a.getNomPretraite();
        List<String> tokensB = b.getNomPretraite();

        if (tokensA.isEmpty() || tokensB.isEmpty()) return 0.0;

        double sommeMeilleurs = 0.0;

        for (String tokenA : tokensA) {
            double meilleur = 0.0;
            for (String tokenB : tokensB) {
                double sim = levenshtein.comparer(tokenA, tokenB);
                if (sim > meilleur) {
                    meilleur = sim;
                }
            }
            sommeMeilleurs += meilleur;
        }

        return sommeMeilleurs / tokensA.size();
    }
}