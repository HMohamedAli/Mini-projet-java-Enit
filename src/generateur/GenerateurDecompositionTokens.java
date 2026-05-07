package generateur;

import java.util.*;
import main.Nom;

public class GenerateurDecompositionTokens extends GenerateurDeCandidats {
    private final String separateur;
    private final int seuilTokensCommuns;

    public GenerateurDecompositionTokens(String separateur, int seuilTokensCommuns) {
        this.separateur = separateur;
        this.seuilTokensCommuns = seuilTokensCommuns;
    }

    @Override
    public List<Nom[]> genererCandidats(List<Nom> listeSource, List<Nom> listeCible) {
        List<Nom[]> candidats = new ArrayList<>();

        for (Nom source : listeSource) {
            Set<String> tokensSource = tokeniser(source.getNomPretraite());
            
            for (Nom cible : listeCible) {
                Set<String> tokensCible = tokeniser(cible.getNomPretraite());
                
                
                int communs = 0;
                for (String t : tokensSource) {
                    if (tokensCible.contains(t)) communs++;
                }

                if (communs >= seuilTokensCommuns) {
                    candidats.add(new Nom[]{source, cible});
                }
            }
        }
        return candidats;
    }

    private Set<String> tokeniser(String texte) {
        return new HashSet<>(Arrays.asList(texte.split(separateur)));
    }
}