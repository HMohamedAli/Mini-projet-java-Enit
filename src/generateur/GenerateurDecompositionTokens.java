package generateur;

import java.util.*;
import main.Nom;

public class GenerateurDecompositionTokens implements GenerateurDeCandidats {
    private final int seuilTokensCommuns;

    public GenerateurDecompositionTokens(int seuilTokensCommuns) {
        this.seuilTokensCommuns = seuilTokensCommuns;
    }

    @Override
    public List<Nom[]> genererCandidats(List<Nom> listeSource, List<Nom> listeCible) {
        List<Nom[]> candidats = new ArrayList<>();

        for (Nom source : listeSource) {
            List<String> tokensSource = source.getNomPretraite();

            for (Nom cible : listeCible) {
                List<String> tokensCible = cible.getNomPretraite();

                int communs = 0;
                for (String t : tokensSource) {
                    if (tokensCible.contains(t))
                        communs++;
                }

                if (communs >= seuilTokensCommuns) {
                    candidats.add(new Nom[] { source, cible });
                }
            }
        }
        return candidats;
    }

}