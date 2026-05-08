package selectionneur;

import main.CoupleScore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SelectionneurPremierN implements SelectionneurDeResultat {

    private final int n;

    public SelectionneurPremierN(int n) { this.n = n; }

    @Override
    public List<CoupleScore> selectionner(List<CoupleScore> couples) {
        List<CoupleScore> copies = new ArrayList<>(couples);
        Collections.sort(copies, new Comparator<CoupleScore>() {
            public int compare(CoupleScore x, CoupleScore y) {
                return Double.compare(y.getScore(), x.getScore());
            }
        });
        return copies.subList(0, Math.min(n, copies.size()));
    }
}