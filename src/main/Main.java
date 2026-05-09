package main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private static final Path DOSSIER_SOURCE = Paths.get("listsource");
    private static final Path DOSSIER_CIBLE = Paths.get("listecible");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        creerDossiers();

        while (true) {
            System.out.println("\n==== MENU PRINCIPAL ====");
            System.out.println("1. Charger une liste");
            System.out.println("2. Chercher un nom");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            String choix = sc.nextLine().trim();

            if (choix.equals("0")) {
                System.out.println("Au revoir !");
                break;
            } else if (choix.equals("1")) {
                menuChargerListe(sc);
            } else if (choix.equals("2")) {
                menuChercherNom(sc);
            } else {
                System.out.println("Choix invalide.");
            }
        }

        sc.close();
    }

    private static void creerDossiers() {
        try {
            Files.createDirectories(DOSSIER_SOURCE);
            Files.createDirectories(DOSSIER_CIBLE);
            System.out.println("Dossiers listsource/ et listecible/ crees.");
        } catch (IOException e) {
            System.out.println("Erreur creation dossiers : " + e.getMessage());
        }
    }

    private static void menuChargerListe(Scanner sc) {
        System.out.println("\n--- Charger une liste ---");
        System.out.println("1. Charger une liste cible");
        System.out.println("2. Charger une liste source");
        System.out.print("Votre choix : ");

        String choix = sc.nextLine().trim();
        boolean estSource;
        if (choix.equals("1")) {
            estSource = false;
        } else if (choix.equals("2")) {
            estSource = true;
        } else {
            System.out.println("Choix invalide.");
            return;
        }

        System.out.print("Chemin du fichier CSV : ");
        String chemin = sc.nextLine().trim();
        Path cheminFichier = Paths.get(chemin);

        if (!Files.exists(cheminFichier)) {
            System.out.println("Fichier introuvable.");
            return;
        }

        try {
            List<String> lignes = Files.readAllLines(cheminFichier, StandardCharsets.UTF_8);
            if (lignes.isEmpty()) {
                System.out.println("Fichier vide.");
                return;
            }

            boolean hasHeader = detecterEntete(lignes.get(0));
            List<Nom> noms = new ArrayList<>();
            for (int i = (hasHeader ? 1 : 0); i < lignes.size(); i++) {
                String ligne = lignes.get(i).trim();
                if (ligne.isEmpty()) continue;
                String[] parts = ligne.contains(";") ? ligne.split(";") : ligne.split(",");
                String id = parts[0].trim();
                String nomOriginal = parts.length > 1 ? parts[1].trim() : parts[0].trim();
                String nomListe = estSource ? "source" : "cible";
                noms.add(new Nom(id, nomOriginal, nomListe));
            }

            Path dossierDest = estSource ? DOSSIER_SOURCE : DOSSIER_CIBLE;
            String nomFichier = cheminFichier.getFileName().toString();
            if (!nomFichier.toLowerCase().endsWith(".csv")) {
                nomFichier += ".csv";
            }
            Path destination = dossierDest.resolve(nomFichier);
            Files.copy(cheminFichier, destination, StandardCopyOption.REPLACE_EXISTING);

            System.out.println(noms.size() + " nom(s) chargé(s) depuis " + nomFichier);

        } catch (IOException e) {
            System.out.println("Erreur lecture fichier : " + e.getMessage());
        }
    }

    private static boolean detecterEntete(String premiereLigne) {
        String lower = premiereLigne.trim().toLowerCase();
        return lower.contains("id") || lower.contains("nom") || lower.contains("name") || lower.contains("prenom");
    }

    private static void menuChercherNom(Scanner sc) {
        try {
            List<Path> sources = listerFichiers(DOSSIER_SOURCE);
            if (sources.isEmpty()) {
                System.out.println("Aucune liste source disponible. Chargez-en une d'abord.");
                return;
            }
            System.out.println("\n--- Listes source disponibles ---");
            for (int i = 0; i < sources.size(); i++) {
                System.out.println((i + 1) + ". " + sources.get(i).getFileName());
            }
            System.out.print("Choisissez une liste source : ");
            int choixSrc = lireEntier(sc.nextLine().trim()) - 1;
            if (choixSrc < 0 || choixSrc >= sources.size()) {
                System.out.println("Choix invalide.");
                return;
            }

            List<Path> cibles = listerFichiers(DOSSIER_CIBLE);
            if (cibles.isEmpty()) {
                System.out.println("Aucune liste cible disponible. Chargez-en une d'abord.");
                return;
            }
            System.out.println("\n--- Listes cible disponibles ---");
            for (int i = 0; i < cibles.size(); i++) {
                System.out.println((i + 1) + ". " + cibles.get(i).getFileName());
            }
            System.out.print("Choisissez une liste cible : ");
            int choixCib = lireEntier(sc.nextLine().trim()) - 1;
            if (choixCib < 0 || choixCib >= cibles.size()) {
                System.out.println("Choix invalide.");
                return;
            }

            ListeMots listeSource = chargerListeMots(sources.get(choixSrc), "source");
            ListeMots listeCible = chargerListeMots(cibles.get(choixCib), "cible");
            if (listeSource == null || listeCible == null) return;

            Configuration config = new Configuration();
            config.setListeSource(listeSource);
            config.setListeCible(listeCible);
            config.configurerDepuisMenus(sc);

            MoteurDeMatching moteur = new MoteurDeMatching(config);
            long debut = System.currentTimeMillis();
            List<CoupleScore> resultats = moteur.executer();
            long duree = System.currentTimeMillis() - debut;

            System.out.println("\nPaires retenues : " + resultats.size());
            System.out.println("Temps           : " + duree + " ms");

        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private static List<Path> listerFichiers(Path dossier) throws IOException {
        return Files.list(dossier)
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
    }

    private static ListeMots chargerListeMots(Path cheminFichier, String nomListe) throws IOException {
        List<String> lignes = Files.readAllLines(cheminFichier, StandardCharsets.UTF_8);
        if (lignes.isEmpty()) {
            System.out.println("Fichier vide : " + cheminFichier.getFileName());
            return null;
        }

        boolean hasHeader = detecterEntete(lignes.get(0));
        List<Nom> noms = new ArrayList<>();
        for (int i = (hasHeader ? 1 : 0); i < lignes.size(); i++) {
            String ligne = lignes.get(i).trim();
            if (ligne.isEmpty()) continue;
            String[] parts = ligne.contains(";") ? ligne.split(";") : ligne.split(",");
            String id = parts[0].trim();
            String nomOriginal = parts.length > 1 ? parts[1].trim() : parts[0].trim();
            noms.add(new Nom(id, nomOriginal, nomListe));
        }

        return new ListeMots(nomListe, cheminFichier.toString(), noms);
    }

    private static int lireEntier(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
