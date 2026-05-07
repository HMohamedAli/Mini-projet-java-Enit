package pretraitement;

import main.Nom;
import java.util.ArrayList;
import java.util.List;

public class SupprimerAccents implements PretraiteurNom {

    @Override
    public void traiter(Nom nom) {
        if (nom == null) return;
        List<String> resultat = new ArrayList<>();
        for (String token : nom.getNomPretraite()) {
            resultat.add(supprimerAccents(token));
        }
        nom.setNomPretraite(resultat);
    }

    private String supprimerAccents(String s) {
        s = s.replace("é","e").replace("è","e").replace("ê","e").replace("ë","e");
        s = s.replace("à","a").replace("â","a").replace("ä","a");
        s = s.replace("ù","u").replace("û","u").replace("ü","u");
        s = s.replace("î","i").replace("ï","i");
        s = s.replace("ô","o").replace("ö","o");
        s = s.replace("ç","c").replace("ñ","n");
        return s;
    }
}