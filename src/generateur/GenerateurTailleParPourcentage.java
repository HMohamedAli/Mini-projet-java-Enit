package generateur;

import java.util.ArrayList;
import java.util.List;
import main.Nom;

public class GenerateurTailleParPourcentage implements GenerateurDeCandidats {

    private final double pct;

    public GenerateurTailleParPourcentage(double pct) {
        this.pct = pct;
    }

    @Override
    public List<Nom[]> genererCandidats(List<Nom> listeSource, List<Nom> listeCible) {
        List<Nom[]> resultats = new ArrayList<>();
        for (Nom source : listeSource) {
            int lenSrc = longueurNomPretraite(source);
            for (Nom cible : listeCible) {
                int lenCib = longueurNomPretraite(cible);
                double ecart = Math.abs(lenSrc - lenCib) * 100.0 / Math.max(lenSrc, 1);
                if (ecart <= pct) {
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
