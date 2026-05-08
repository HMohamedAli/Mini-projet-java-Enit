package comparaison;

import main.Nom;


public class ComparaisonLevenshtein implements ComparateurDeChaines, ComparateurDeNoms {

    @Override
    public double comparer(String a, String b) {
        if (a == null || b == null) return 0.0;
        int distance = calculerDistance(a, b);
        int maxLen   = Math.max(a.length(), b.length());
        return maxLen == 0 ? 1.0 : 1.0 - (double) distance / maxLen;
    }

    @Override
    public double comparer(Nom a, Nom b) {
        if (a == null || b == null) return 0.0;
        String sa = String.join(" ", a.getNomPretraite());
        String sb = String.join(" ", b.getNomPretraite());
        return comparer(sa, sb);
    }

    private int calculerDistance(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];
        for (int j = 0; j <= n; j++) prev[j] = j;
        for (int i = 1; i <= m; i++) {
            curr[0] = i;
            for (int j = 1; j <= n; j++) {
                int cout = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                curr[j] = Math.min(Math.min(prev[j] + 1, curr[j - 1] + 1), prev[j - 1] + cout);
            }
            int[] tmp = prev; prev = curr; curr = tmp;
        }
        return prev[n];
    }
}