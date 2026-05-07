package generateur;

import java.util.ArrayList;
import java.util.List;
import main.Nom;

public class GenerateurTailleOriginaleParPourcentage extends GenerateurDeCandidats {

    private final double pct;

    public GenerateurTailleOriginaleParPourcentage(double pct) {
        this.pct = pct;
    }

    @Override
    public List<Nom[]> genererCandidats(List<Nom> listeSource, List<Nom> listeCible) {
        List<Nom[]> resultats = new ArrayList<>();
        for (Nom source : listeSource) {
            int lenSrc = source.getNomOriginal().length();
            for (Nom cible : listeCible) {
                int lenCib = cible.getNomOriginal().length();
                double ecart = Math.abs(lenSrc - lenCib) * 100.0 / Math.max(lenSrc, 1);
                if (ecart <= pct) {
                    resultats.add(new Nom[]{source, cible});
                }
            }
        }
        return resultats;
    }
}