package generateur;

import comparaison.ComparaisonLevenshtein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import main.Nom;

public class GenerateurPermutationOptimale extends GenerateurDeCandidats {

    private static final int PERMUTATION_LIMIT = 7;
    private final double seuil;
    private final ComparaisonLevenshtein comparateur;

    public GenerateurPermutationOptimale(double seuil) {
        this.seuil = seuil;
        this.comparateur = new ComparaisonLevenshtein();
    }

    @Override
    public List<Nom[]> genererCandidats(List<Nom> listeSource, List<Nom> listeCible) {
        List<Nom[]> candidats = new ArrayList<>();
        for (Nom source : listeSource) {
            for (Nom cible : listeCible) {
                double meilleurScore = meilleurScoreAlignement(source, cible);
                if (meilleurScore >= seuil) {
                    candidats.add(new Nom[]{source, cible});
                }
            }
        }
        return candidats;
    }

    private double meilleurScoreAlignement(Nom a, Nom b) {
        List<String> tokA = a.getNomPretraite();
        List<String> tokB = b.getNomPretraite();
        if (tokA.isEmpty() || tokB.isEmpty()) return 0.0;

        List<String> shorter, longer;
        if (tokA.size() <= tokB.size()) {
            shorter = tokA;
            longer = tokB;
        } else {
            shorter = tokB;
            longer = tokA;
        }

        int lenS = shorter.size();
        int lenL = longer.size();
        double meilleur;

        if (lenL > PERMUTATION_LIMIT) {
            meilleur = scoreGreedy(shorter, longer);
        } else {
            List<String> mutable = new ArrayList<>(longer);
            meilleur = explorerPermutations(shorter, mutable, 0);
        }

        return meilleur / lenS;
    }

    private double explorerPermutations(List<String> shorter, List<String> longer, int start) {
        if (start == longer.size() - 1) {
            return evaluer(shorter, longer);
        }
        double meilleur = 0.0;
        for (int i = start; i < longer.size(); i++) {
            Collections.swap(longer, start, i);
            double score = explorerPermutations(shorter, longer, start + 1);
            if (score > meilleur) meilleur = score;
            Collections.swap(longer, start, i);
        }
        return meilleur;
    }

    private double evaluer(List<String> shorter, List<String> longer) {
        double somme = 0.0;
        for (int i = 0; i < shorter.size(); i++) {
            somme += comparateur.comparer(shorter.get(i), longer.get(i));
        }
        return somme;
    }

    private double scoreGreedy(List<String> shorter, List<String> longer) {
        boolean[] utilised = new boolean[longer.size()];
        double somme = 0.0;
        for (String s : shorter) {
            double meilleurSim = 0.0;
            int meilleurIdx = -1;
            for (int j = 0; j < longer.size(); j++) {
                if (utilised[j]) continue;
                double sim = comparateur.comparer(s, longer.get(j));
                if (sim > meilleurSim) {
                    meilleurSim = sim;
                    meilleurIdx = j;
                }
            }
            if (meilleurIdx >= 0) {
                utilised[meilleurIdx] = true;
                somme += meilleurSim;
            }
        }
        return somme;
    }

    public double getSeuil() {
        return seuil;
    }
}
