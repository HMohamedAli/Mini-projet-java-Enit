package main;

import java.util.List;

/**
 * Livre les résultats finaux au consommateur (console, fichier, base de données…).
 * Cette implémentation affiche sur la sortie standard.
 */
public class LivreurDeResultat {

    public void livrerResultat(List<Object[]> couples) {
        System.out.printf("%-5s  %-30s  %-30s  %s%n",
                "#", "Source", "Cible", "Score");
        System.out.println("-".repeat(80));
        int rang = 1;
        for (Object[] c : couples) {
            Nom source = (Nom) c[0];
            Nom cible = (Nom) c[1];
            Double score = (Double) c[2];
            System.out.printf("%-5d  %-30s  %-30s  %.4f%n",
                    rang++,
                    source.getNomOriginal(),
                    cible.getNomOriginal(),
                    score);
        }
        System.out.printf("%d résultat(s) livré(s).%n", couples.size());
    }
}
