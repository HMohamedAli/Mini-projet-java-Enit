package generateur;

import java.util.*;
import main.Nom;

public class GenerateurIndexeurDeTokens implements GenerateurDeCandidats {

    private final int seuilTokensCommuns;
    private final TreeMap<String, List<Nom>> index = new TreeMap<>();

    public GenerateurIndexeurDeTokens(int seuilTokensCommuns) {
        this.seuilTokensCommuns = seuilTokensCommuns;
    }

    @Override
    public List<Nom[]> genererCandidats(List<Nom> listeSource, List<Nom> listeCible) {

        index.clear();

        for (Nom cible : listeCible) {
            List<String> tokens = cible.getNomPretraite();

            for (String t : tokens) {
                if (t != null && !t.isEmpty()) {
                    List<Nom> listeAssociee = index.get(t);

                    if (listeAssociee == null) {
                        listeAssociee = new ArrayList<>();
                        index.put(t, listeAssociee);
                    }

                    listeAssociee.add(cible);
                }
            }
        }

        List<Nom[]> candidats = new ArrayList<>();

        for (Nom source : listeSource) {
            Map<Nom, Integer> compteurs = new HashMap<>();
            List<String> tokensSource = source.getNomPretraite();

            for (String t : tokensSource) {
                List<Nom> ciblesPartageantLeToken = index.getOrDefault(t, Collections.emptyList());

                for (Nom cible : ciblesPartageantLeToken) {
                    int ancienCompte = compteurs.getOrDefault(cible, 0);
                    compteurs.put(cible, ancienCompte + 1);
                }
            }

            for (Map.Entry<Nom, Integer> entry : compteurs.entrySet()) {
                if (entry.getValue() >= seuilTokensCommuns) {
                    candidats.add(new Nom[] { source, entry.getKey() });
                }
            }
        }

        return candidats;
    }
}