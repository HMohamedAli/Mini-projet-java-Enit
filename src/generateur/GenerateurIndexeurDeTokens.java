package generateur;

import java.util.*;
import main.Nom;


public class GenerateurIndexeurDeTokens extends GenerateurDeCandidats {
    private final String separateur;
    private final int seuilTokensCommuns;
    private final TreeMap<String, List<Nom>> index = new TreeMap<>();

    public GenerateurIndexeurDeTokens(String separateur, int seuilTokensCommuns) {
        this.separateur = separateur;
        this.seuilTokensCommuns = seuilTokensCommuns;
    }

    @Override
    public List<Nom[]> genererCandidats(List<Nom> listeSource, List<Nom> listeCible) {
      
        index.clear();
       for (Nom cible : listeCible) {
    String[] tokens = cible.getNomPretraite().split(separateur);
    for (String t : tokens) {
        if (!t.isEmpty()) {
       
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
            String[] tokensSource = source.getNomPretraite().split(separateur);

            for (String t : tokensSource) {
                List<Nom> ciblesPartageantLeToken = index.getOrDefault(t, Collections.emptyList());
                for (Nom cible : ciblesPartageantLeToken) {
                    compteurs.put(cible, compteurs.getOrDefault(cible, 0) + 1);
                }
            }


            for (Map.Entry<Nom, Integer> entry : compteurs.entrySet()) {
                if (entry.getValue() >= seuilTokensCommuns) {
                    candidats.add(new Nom[]{source, entry.getKey()});
                }
            }
        }
        return candidats;
    }
}