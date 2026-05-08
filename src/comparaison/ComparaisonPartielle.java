package comparaison;

import main.Nom;


public class ComparaisonPartielle implements ComparateurDeChaines, ComparateurDeNoms {

    @Override
    public double comparer(String a, String b) {
        if (a == null || b == null) return 0.0;
        if (a.contains(b) || b.contains(a)) return 0.8;
        int communs = 0;
        for (char c : a.toCharArray()) {
            if (b.indexOf(c) >= 0) communs++;
        }
        int maxLen = Math.max(a.length(), b.length());
        return maxLen == 0 ? 1.0 : (double) communs / maxLen;
    }

    @Override
    public double comparer(Nom a, Nom b) {
        if (a == null || b == null) return 0.0;
        String sa = String.join(" ", a.getNomPretraite());
        String sb = String.join(" ", b.getNomPretraite());
        return comparer(sa, sb);
    }
}