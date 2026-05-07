package pretraitement;

import main.Nom;
import java.util.ArrayList;
import java.util.List;

public class TokeniseurParEspace implements PretraiteurNom {

    @Override
    public void traiter(Nom nom) {
        if (nom == null) return;
        List<String> resultat = new ArrayList<>();
        for (String token : nom.getNomPretraite()) {
            for (String sousToken : token.split("\\s+")) {
                if (!sousToken.isEmpty()) {
                    resultat.add(sousToken);
                }
            }
        }
        nom.setNomPretraite(resultat);
    }
}