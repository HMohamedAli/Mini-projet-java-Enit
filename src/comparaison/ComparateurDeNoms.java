package comparaison;

import main.CoupleScore;
import main.Nom;

import java.util.ArrayList;
import java.util.List;


public interface ComparateurDeNoms {

    double comparer(Nom a, Nom b);

    default List<CoupleScore> comparerTous(List<Nom[]> couples) {
        List<CoupleScore> resultats = new ArrayList<>();
        for (Nom[] couple : couples) {
            double score = comparer(couple[0], couple[1]);
            resultats.add(new CoupleScore(couple[0], couple[1], score));
        }
        return resultats;
    }
}