package pretraitement;

import main.Nom;
import java.util.ArrayList;
import java.util.List;

public class EnMajuscules implements PretraiteurNom {

    @Override
    public void traiter(Nom nom) {
        if (nom == null) return;
        List<String> resultat = new ArrayList<>();
        for (String token : nom.getNomPretraite()) {
            resultat.add(token.toUpperCase());
        }
        nom.setNomPretraite(resultat);
    }
}