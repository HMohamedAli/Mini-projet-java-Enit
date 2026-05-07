package pretraitement;

import main.Nom;
import java.util.ArrayList;
import java.util.List;

public class SuppressionEspaces implements PretraiteurNom {

    @Override
    public void traiter(Nom nom) {
        if (nom == null) return;
        List<String> resultat = new ArrayList<>();
        for (String token : nom.getNomPretraite()) {
            String nettoye = token.trim().replaceAll("\\s+", " ");
            if (!nettoye.isEmpty()) {
                resultat.add(nettoye);
            }
        }
        nom.setNomPretraite(resultat);
    }
}