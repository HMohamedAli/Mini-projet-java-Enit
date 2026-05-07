package main;

import comparaison.ComparaisonExacte;
import comparaison.ComparaisonLevenshtein;
import comparaison.ComparaisonPartielle;
import comparaison.ComparateurDeNoms;
import generateur.GenerateurDeCandidats;
import generateur.GenerateurDecompositionTokens;
import generateur.GenerateurIndexeurDeTokens;
import generateur.GenerateurProduitCartesien;
import generateur.GenerateurTailleOriginaleParN;
import generateur.GenerateurTailleOriginaleParPourcentage;
import pretraitement.EnMajuscules;
import pretraitement.PretraiteurNom;
import pretraitement.RemplacementCaracteresSpeciaux;
import pretraitement.SupprimerAccents;
import pretraitement.SuppressionEspaces;
import selectionneur.SelectionneurDeResultat;
import selectionneur.SelectionneurPremierN;
import selectionneur.SelectionneurSeuil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Configuration {

    private ListeMots listeSource;
    private ListeMots listeCible;
    private final List<PretraiteurNom> pretraiteurs = new ArrayList<>();
    private GenerateurDeCandidats generateur;
    private ComparateurDeNoms comparateur;
    private final List<SelectionneurDeResultat> selectionneurs = new ArrayList<>();

    public void setListeSource(ListeMots l) {
        this.listeSource = l;
    }

    public void setListeCible(ListeMots l) {
        this.listeCible = l;
    }

    public void setGenerateur(GenerateurDeCandidats g) {
        this.generateur = g;
    }

    public void setComparateur(ComparateurDeNoms c) {
        this.comparateur = c;
    }

    public void ajouterPretraiteur(PretraiteurNom p) {
        pretraiteurs.add(p);
    }

    public void ajouterSelectionneur(SelectionneurDeResultat s) {
        selectionneurs.add(s);
    }

    public ListeMots getListeSource() {
        return listeSource;
    }

    public ListeMots getListeCible() {
        return listeCible;
    }

    public List<PretraiteurNom> getPretraiteurs() {
        return pretraiteurs;
    }

    public GenerateurDeCandidats getGenerateur() {
        return generateur;
    }

    public ComparateurDeNoms getComparateur() {
        return comparateur;
    }

    public List<SelectionneurDeResultat> getSelectionneurs() {
        return selectionneurs;
    }

    public void configurerDepuisMenus(Scanner sc) {
        afficherSeparateur("CONFIGURATION DU PIPELINE");
        choixPretraiteurs(sc);
        choixGenerateur(sc);
        choixComparateur(sc);
        choixSelectionneur(sc);
        afficherSeparateur("CONFIGURATION TERMINÉE");
    }

    private void choixPretraiteurs(Scanner sc) {
        System.out.println("\n[1] PRÉTRAITEMENTS disponibles :");
        System.out.println("  1 - En majuscules");
        System.out.println("  2 - Supprimer les accents");
        System.out.println("  3 - Supprimer les espaces");
        System.out.println("  4 - Remplacer les caractères spéciaux");
        System.out.println("  0 - Aucun prétraitement");
        System.out.println("(Entrez plusieurs numéros séparés par des espaces, ex: 1 2)");
        System.out.print("Votre choix : ");

        String ligne = sc.nextLine().trim();
        if (ligne.equals("0") || ligne.isEmpty()) {
            System.out.println("Aucun prétraitement sélectionné.");
            return;
        }
        for (String token : ligne.split("\\s+")) {
            int choix = lireEntier(token);
            if (choix == 1) {
                pretraiteurs.add(new EnMajuscules());
                System.out.println("  ✓ EnMajuscules ajouté");
            }
            if (choix == 2) {
                pretraiteurs.add(new SupprimerAccents());
                System.out.println("  ✓ SupprimerAccents ajouté");
            }
            if (choix == 3) {
                pretraiteurs.add(new SuppressionEspaces());
                System.out.println("  ✓ SuppressionEspaces ajouté");
            }
            if (choix == 4) {
                pretraiteurs.add(new RemplacementCaracteresSpeciaux());
                System.out.println("  ✓ RemplacementCaracteresSpeciaux ajouté");
            }
        }
    }

    private void choixGenerateur(Scanner sc) {
        System.out.println("\n[2] GÉNÉRATEUR DE CANDIDATS disponibles :");
        System.out.println("  1 - Produit cartésien (toutes les paires)");
        System.out.println("  2 - Taille originale proche (par N caractères)");
        System.out.println("  3 - Taille originale proche (par pourcentage)");
        System.out.println("  4 - Décomposition par tokens");
        System.out.println("  5 - Indexeur de tokens");
        System.out.print("Votre choix : ");

        int choix = lireEntier(sc.nextLine().trim());

        if (choix == 1) {
            generateur = new GenerateurProduitCartesien();
            System.out.println("  ✓ GenerateurProduitCartesien sélectionné");
        } else if (choix == 2) {
            System.out.print("  Tolérance en nombre de caractères (N) : ");
            int n = lireEntier(sc.nextLine().trim());
            generateur = new GenerateurTailleOriginaleParN(n);
            System.out.println("  ✓ GenerateurTailleOriginaleParN(" + n + ") sélectionné");
        } else if (choix == 3) {
            System.out.print("  Pourcentage de tolérance (ex: 20 pour 20%) : ");
            double pct = lireDouble(sc.nextLine().trim());
            generateur = new GenerateurTailleOriginaleParPourcentage(pct);
            System.out.println("  ✓ GenerateurTailleOriginaleParPourcentage(" + pct + ") sélectionné");
        } else if (choix == 4) {
            System.out.print("  Séparateur (ex: \\s+) : ");
            String sep = sc.nextLine().trim();
            System.out.print("  Seuil de tokens communs : ");
            int seuil = lireEntier(sc.nextLine().trim());
            generateur = new GenerateurDecompositionTokens(sep, seuil);
            System.out.println("  ✓ GenerateurDecompositionTokens sélectionné");
        } else if (choix == 5) {
            System.out.print("  Séparateur : ");
            String sep = sc.nextLine().trim();
            System.out.print("  Seuil de tokens communs : ");
            int seuil = lireEntier(sc.nextLine().trim());
            generateur = new GenerateurIndexeurDeTokens(sep, seuil);
            System.out.println("  ✓ GenerateurIndexeurDeTokens sélectionné");
        } else {
            generateur = new GenerateurProduitCartesien();
            System.out.println("  Choix invalide, GenerateurProduitCartesien utilisé par défaut");
        }
    }

    private void choixComparateur(Scanner sc) {
        System.out.println("\n[3] ALGORITHME DE COMPARAISON (choisissez-en un) :");
        System.out.println("  1 - Comparaison exacte");
        System.out.println("  2 - Distance de Levenshtein");
        System.out.println("  3 - Comparaison partielle");
        System.out.print("Votre choix : ");

        int choix = lireEntier(sc.nextLine().trim());

        if (choix == 1) {
            comparateur = new ComparaisonExacte();
            System.out.println("  ✓ ComparaisonExacte sélectionnée");
        } else if (choix == 2) {
            comparateur = new ComparaisonLevenshtein();
            System.out.println("  ✓ ComparaisonLevenshtein sélectionnée");
        } else if (choix == 3) {
            comparateur = new ComparaisonPartielle();
            System.out.println("  ✓ ComparaisonPartielle sélectionnée");
        } else {
            comparateur = new ComparaisonExacte();
            System.out.println("  Choix invalide, ComparaisonExacte utilisée par défaut");
        }
    }

    private void choixSelectionneur(Scanner sc) {
        System.out.println("\n[4] SÉLECTIONNEUR DE RÉSULTATS :");
        System.out.println("  1 - Seuil minimum de score");
        System.out.println("  2 - Premiers N résultats");
        System.out.print("Votre choix : ");

        int choix = lireEntier(sc.nextLine().trim());

        if (choix == 1) {
            System.out.print("  Seuil (entre 0.0 et 1.0) : ");
            double seuil = lireDouble(sc.nextLine().trim());
            selectionneurs.add(new SelectionneurSeuil(seuil));
            System.out.println("  ✓ SelectionneurSeuil(" + seuil + ") ajouté");
        } else if (choix == 2) {
            System.out.print("  Nombre de résultats N : ");
            int n = lireEntier(sc.nextLine().trim());
            selectionneurs.add(new SelectionneurPremierN(n));
            System.out.println("  ✓ SelectionneurPremierN(" + n + ") ajouté");
        } else {
            selectionneurs.add(new SelectionneurSeuil(0.8));
            System.out.println("  Choix invalide, SelectionneurSeuil(0.8) utilisé par défaut");
        }
    }

    private int lireEntier(String s) {
        int valeur = -1;
        try {
            valeur = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            /* ignoré */ }
        return valeur;
    }

    private double lireDouble(String s) {
        double valeur = 0.8;
        try {
            valeur = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            /* ignoré */ }
        return valeur;
    }

    private void afficherSeparateur(String titre) {
        System.out.println("\n========================================");
        System.out.println("  " + titre);
        System.out.println("========================================");
    }
}