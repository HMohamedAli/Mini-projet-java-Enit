package generateur;

import java.util.ArrayList;
import java.util.List;
import main.Nom;

public class GenerateurTailleOriginaleParN extends GenerateurDeCandidats {

    private final int n;

    public GenerateurTailleOriginaleParN(int n) {
        this.n = n;
    }

    @Override
    public List<Nom[]> genererCandidats(List<Nom> listeSource, List<Nom> listeCible) {
        List<Nom[]> resultats = new ArrayList<>();
        for (Nom source : listeSource) {
            int lenSrc = source.getNomOriginal().length();
            for (Nom cible : listeCible) {
                if (Math.abs(lenSrc - cible.getNomOriginal().length()) <= n) {
                    resultats.add(new Nom[]{source, cible});
                }
            }
        }
        return resultats;
    }
}