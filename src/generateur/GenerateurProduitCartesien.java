package generateur;

import java.util.ArrayList;
import java.util.List;
import main.Nom;



public class GenerateurProduitCartesien implements GenerateurDeCandidats {

    @Override
    public List<Nom[]> genererCandidats(List<Nom> listeSource, List<Nom> listeCible) {
        List<Nom[]> resultats = new ArrayList<>(listeSource.size() * listeCible.size());
        for (Nom source : listeSource) {
            for (Nom cible : listeCible) {
                resultats.add(new Nom[]{source, cible});
            }
        }
        return resultats;
    }
}
