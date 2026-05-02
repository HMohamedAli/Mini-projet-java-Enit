
import java.util.List;

public class ComparaisonExacte extends ComparateurDeNoms {

    @Override
    public double comparer(List<Nom> a, List<Nom> b) {
        if (a == null || b == null) return 0.0;
        if (a.size() != b.size()) return 0.0;
        for (int i = 0; i < a.size(); i++) {
            Nom nomA = a.get(i);
            Nom nomB = b.get(i);
            if (nomA == null || nomB == null) return 0.0;
            if (!nomA.getNom().equalsIgnoreCase(nomB.getNom())) return 0.0;
        }
        return 1.0;
    }
}