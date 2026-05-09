package comparaison;

import main.Nom;
import java.util.List;

public class ComparaisonOrdreTokens implements ComparateurDeNoms {

    private final ComparaisonLevenshtein levenshtein = new ComparaisonLevenshtein();
    private final double seuilToken;

    public ComparaisonOrdreTokens() {
        this.seuilToken = 0.7;
    }

    public ComparaisonOrdreTokens(double seuilToken) {
        this.seuilToken = seuilToken;
    }

    @Override
    public double comparer(Nom a, Nom b) {
        if (a == null || b == null) return 0.0;

        List<String> tokensA = a.getNomPretraite();
        List<String> tokensB = b.getNomPretraite();

        if (tokensA.isEmpty() || tokensB.isEmpty()) return 0.0;

        int lcsLength = lcs(tokensA, tokensB);
        return (2.0 * lcsLength) / (tokensA.size() + tokensB.size());
    }

    private int lcs(List<String> a, List<String> b) {
        int m = a.size(), n = b.size();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                double sim = levenshtein.comparer(a.get(i - 1), b.get(j - 1));
                dp[i][j] = (sim >= seuilToken)
                    ? dp[i - 1][j - 1] + 1
                    : Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[m][n];
    }
}
