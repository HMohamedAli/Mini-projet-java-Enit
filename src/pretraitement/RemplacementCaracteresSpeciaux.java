package pretraitement;

import main.Nom;
import java.util.ArrayList;
import java.util.List;

public class RemplacementCaracteresSpeciaux implements PretraiteurNom {

    @Override
    public void traiter(Nom nom) {
        if (nom == null) return;
        List<String> resultat = new ArrayList<>();
        for (String token : nom.getNomPretraite()) {
            String nettoye = token.replace("-", "").replace("_", "").replace("'", "");
            resultat.add(nettoye);
        }
        nom.setNomPretraite(resultat);
    }
}