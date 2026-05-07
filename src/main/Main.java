package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

       
        List<Nom> nomsSource = new ArrayList<>();
        nomsSource.add(new Nom("1", "Jean Martin",   "source"));
        nomsSource.add(new Nom("2", "Marie Dupont",  "source"));
        nomsSource.add(new Nom("3", "Ahmed Ben Ali", "source"));

        List<Nom> nomsCible = new ArrayList<>();
        nomsCible.add(new Nom("A", "Martin Jean",    "cible"));
        nomsCible.add(new Nom("B", "Marie Dupond",   "cible"));
         nomsCible.add(new Nom("C", "Ahmed Benali",   "cible"));

        ListeMots listeSource = new ListeMots("source", "data/source.txt", nomsSource);
        ListeMots listeCible  = new ListeMots("cible",  "data/cible.txt",  nomsCible);

    
        Configuration config = new Configuration();
        config.setListeSource(listeSource);
        config.setListeCible(listeCible);
        config.configurerDepuisMenus(sc);

        
        MoteurDeMatching moteur = new MoteurDeMatching(config);

        long debut = System.currentTimeMillis();
        List<Object[]> resultats = moteur.executer();
        long duree = System.currentTimeMillis() - debut;

        System.out.println("\nPaires retenues : " + resultats.size());
        System.out.println("Temps           : " + duree + " ms");

        sc.close();
    }
}