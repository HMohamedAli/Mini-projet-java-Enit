package generateur;

import java.util.ArrayList;
import java.util.List;
import main.Nom;

public class GenerateurTailleParN implements GenerateurDeCandidats {

    private final int n;

    public GenerateurTailleParN(int n) {
        this.n = n;
    }

    @Override
    public List<Nom[]> genererCandidats(List<Nom> listeSource, List<Nom> listeCible) {
        List<Nom[]> resultats = new ArrayList<>();
        for (Nom source : listeSource) {
            int lenSrc = longueurNomPretraite(source);
            for (Nom cible : listeCible) {
                int lenCib = longueurNomPretraite(cible);
                if (Math.abs(lenSrc - lenCib) <= n) {
                    resultats.add(new Nom[]{source, cible});
                }
            }
        }
        return resultats;
    }

    private int longueurNomPretraite(Nom nom) {
        return String.join("", nom.getNomPretraite()).length();
    }
}
