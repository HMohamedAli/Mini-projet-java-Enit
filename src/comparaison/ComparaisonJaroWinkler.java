package comparaison;

import main.Nom;

public class ComparaisonJaroWinkler implements ComparateurDeChaines, ComparateurDeNoms {

    private static final double DEFAULT_PREFIX_WEIGHT = 0.1;
    private static final int MAX_PREFIX_LENGTH = 4;

    @Override
    public double comparer(String a, String b) {
        if (a == null || b == null) return 0.0;
        if (a.equals(b)) return 1.0;
        double jaro = jaro(a, b);
        int prefixLength = 0;
        int limit = Math.min(Math.min(MAX_PREFIX_LENGTH, a.length()), b.length());
        for (int i = 0; i < limit; i++) {
            if (a.charAt(i) == b.charAt(i)) prefixLength++;
            else break;
        }
        return jaro + prefixLength * DEFAULT_PREFIX_WEIGHT * (1.0 - jaro);
    }

    @Override
    public double comparer(Nom a, Nom b) {
        if (a == null || b == null) return 0.0;
        String sa = String.join(" ", a.getNomPretraite());
        String sb = String.join(" ", b.getNomPretraite());
        return comparer(sa, sb);
    }

    private double jaro(String a, String b) {
        int lenA = a.length(), lenB = b.length();
        int matchDist = Math.max(lenA, lenB) / 2 - 1;
        if (matchDist < 0) matchDist = 0;

        boolean[] matchedA = new boolean[lenA];
        boolean[] matchedB = new boolean[lenB];
        int matches = 0;

        for (int i = 0; i < lenA; i++) {
            int start = Math.max(0, i - matchDist);
            int end = Math.min(lenB, i + matchDist + 1);
            for (int j = start; j < end; j++) {
                if (matchedB[j]) continue;
                if (a.charAt(i) != b.charAt(j)) continue;
                matchedA[i] = true;
                matchedB[j] = true;
                matches++;
                break;
            }
        }

        if (matches == 0) return 0.0;

        int transpositions = 0;
        int j = 0;
        for (int i = 0; i < lenA; i++) {
            if (!matchedA[i]) continue;
            while (!matchedB[j]) j++;
            if (a.charAt(i) != b.charAt(j)) transpositions++;
            j++;
        }

        return (1.0 / 3.0) * (
            (double) matches / lenA +
            (double) matches / lenB +
            (double) (matches - transpositions / 2) / matches
        );
    }
}
