package comparaison;

import main.Nom;


public class ComparaisonExacte implements ComparateurDeChaines, ComparateurDeNoms {

    @Override
    public double comparer(String a, String b) {
        if (a == null || b == null) return 0.0;
        return a.equals(b) ? 1.0 : 0.0;
    }

    @Override
    public double comparer(Nom a, Nom b) {
        if (a == null || b == null) return 0.0;
        String sa = String.join(" ", a.getNomPretraite());
        String sb = String.join(" ", b.getNomPretraite());
        return comparer(sa, sb);
    }
}