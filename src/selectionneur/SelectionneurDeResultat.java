package selectionneur;

import main.CoupleScore;
import java.util.List;

public interface SelectionneurDeResultat {
    List<CoupleScore> selectionner(List<CoupleScore> couples);
}