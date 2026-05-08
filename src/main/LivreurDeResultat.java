package main;

import java.util.List;

public class LivreurDeResultat {

    public void livrerResultat(List<CoupleScore> couples) {
        System.out.printf("%-5s  %-30s  %-30s  %s%n", "#", "Source", "Cible", "Score");
        for (int i = 0; i < 80; i++) {
            System.out.print("-");
        }
        int rang = 1;
        for (CoupleScore c : couples) {
            System.out.printf("%-5d  %-30s  %-30s  %.4f%n",
                    rang++,
                    c.getSource().getNomOriginal(),
                    c.getCible().getNomOriginal(),
                    c.getScore());
        }
        System.out.printf("%d résultat(s) livré(s).%n", couples.size());
    }
}