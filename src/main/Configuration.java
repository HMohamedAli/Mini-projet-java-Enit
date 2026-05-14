package main;

import comparaison.ComparaisonExacte;
import comparaison.ComparaisonJaroWinkler;
import comparaison.ComparaisonLevenshtein;
import comparaison.ComparaisonOrdreTokens;
import comparaison.ComparaisonPartielle;
import comparaison.ComparaisonSoundex;
import comparaison.ComparaisonTokens;
import comparaison.ComparateurDeNoms;
import generateur.GenerateurDeCandidats;
import generateur.GenerateurDecompositionTokens;
import generateur.GenerateurIndexeurDeTokens;
import generateur.GenerateurPermutationOptimale;
import generateur.GenerateurProduitCartesien;
import generateur.GenerateurTailleParN;
import generateur.GenerateurTailleParPourcentage;
import pretraitement.EnMajuscules;
import pretraitement.PretraiteurNom;
import pretraitement.RemplacementCaracteresSpeciaux;
import pretraitement.SupprimerAccents;
import pretraitement.SuppressionEspaces;
import pretraitement.TokeniseurParEspace;
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
        afficherSeparateur("CONFIGURATION TERMINEE");
    }

    private void choixPretraiteurs(Scanner sc) {
        System.out.println("\n 1-- PRETRAITEMENTS disponibles :");
        System.out.println("  1 - En majuscules");
        System.out.println("  2 - Supprimer les accents");
        System.out.println("  3 - Supprimer les espaces");
        System.out.println("  4 - Remplacer les caracteres speciaux");
        System.out.println("  5 - Tokeniser par espace (decoupe en sous-tokens)");
        System.out.println("  0 - Aucun pretraitement");
        System.out.println("(Entrez plusieurs numeros separes par des espaces, ex: 1 2 5)");
        System.out.print("Votre choix : ");

        String ligne = sc.nextLine().trim();
        if (ligne.equals("0") || ligne.isEmpty()) {
            System.out.println("Aucun preraitement selectionné.");
            return;
        }
        for (String token : ligne.split("\\s+")) {
            int choix = lireEntier(token);
            if (choix == 1) {
                pretraiteurs.add(new EnMajuscules());
                System.out.println("   EnMajuscules ajouté");
            }
            if (choix == 2) {
                pretraiteurs.add(new SupprimerAccents());
                System.out.println("  SupprimerAccents ajouté");
            }
            if (choix == 3) {
                pretraiteurs.add(new SuppressionEspaces());
                System.out.println("  SuppressionEspaces ajouté");
            }
            if (choix == 4) {
                pretraiteurs.add(new RemplacementCaracteresSpeciaux());
                System.out.println(" RemplacementCaracteresSpeciaux ajouté");
            }
            if (choix == 5) {
                pretraiteurs.add(new TokeniseurParEspace());
                System.out.println(" TokeniseurParEspace ajouté");
            }
        }
    }

    private void choixGenerateur(Scanner sc) {
        System.out.println("\n 2-- GENERATEUR DE CANDIDATS disponibles :");
        System.out.println("  1 - Produit cartésien ");
        System.out.println("  2 - Taille proche (par N caractéres)");
        System.out.println("  3 - Taille proche (par pourcentage)");
        System.out.println("  4 - Décomposition par tokens");
        System.out.println("  5 - Indexeur de tokens");
        System.out.println("  6 - Permutation optimale (alignement de tokens)");
        System.out.print("Votre choix : ");

        int choix = lireEntier(sc.nextLine().trim());

        if (choix == 1) {
            generateur = new GenerateurProduitCartesien();
            System.out.println("GenerateurProduitCartesien sélectionné");
        } else if (choix == 2) {
            System.out.print("  Tolérance en nombre de caractéres (N) : ");
            int n = lireEntier(sc.nextLine().trim());
            generateur = new GenerateurTailleParN(n);
            System.out.println("GenerateurTailleParN(" + n + ") sélectionné");
        } else if (choix == 3) {
            System.out.print("  Pourcentage de tolérance (100 pour 100%) : ");
            double pct = lireDouble(sc.nextLine().trim());
            generateur = new GenerateurTailleParPourcentage(pct);
            System.out.println(" GenerateurTailleParPourcentage(" + pct + ") sélectionné");
        } else if (choix == 4) {
            System.out.print("  Seuil de tokens communs : ");
            int seuil = lireEntier(sc.nextLine().trim());
            generateur = new GenerateurDecompositionTokens(seuil);
            System.out.println(" GenerateurDecompositionTokens sélectionné");
        } else if (choix == 5) {
            System.out.print("  Seuil de tokens communs : ");
            int seuil = lireEntier(sc.nextLine().trim());
            generateur = new GenerateurIndexeurDeTokens(seuil);
            System.out.println("GenerateurIndexeurDeTokens sélectionné");
        } else if (choix == 6) {
            System.out.print("  Seuil de score d'alignement (entre 0.0 et 1.0) : ");
            double seuil = lireDouble(sc.nextLine().trim());
            generateur = new GenerateurPermutationOptimale(seuil);
            System.out.println("GenerateurPermutationOptimale(" + seuil + ") sélectionné");
        } else {
            generateur = new GenerateurProduitCartesien();
            System.out.println("  Choix invalide, GenerateurProduitCartesien utilisé par défaut");
        }
    }

    private void choixComparateur(Scanner sc) {
        System.out.println("\n 3-- ALGORITHME DE COMPARAISON  :");
        System.out.println("  1 - Comparaison exacte          [ComparateurDeChaines]");
        System.out.println("  2 - Distance de Levenshtein     [ComparateurDeChaines]");
        System.out.println("  3 - Comparaison partielle       [ComparateurDeChaines]");
        System.out.println("  4 - Comparaison par tokens      [ComparateurDeNoms]");
        System.out.println("  5 - Soundex (phonétique)        [ComparateurDeChaines]");
        System.out.println("  6 - Jaro-Winkler                 [ComparateurDeChaines]");
        System.out.println("  7 - Ordre des tokens             [ComparateurDeNoms]");
        System.out.print("Votre choix : ");

        int choix = lireEntier(sc.nextLine().trim());

        if (choix == 1) {
            comparateur = new ComparaisonExacte();
            System.out.println("   ComparaisonExacte sélectionnée");
        } else if (choix == 2) {
            comparateur = new ComparaisonLevenshtein();
            System.out.println("  ComparaisonLevenshtein sélectionnee");
        } else if (choix == 3) {
            comparateur = new ComparaisonPartielle();
            System.out.println("  ComparaisonPartielle sélectionnee");
        } else if (choix == 4) {
            comparateur = new ComparaisonTokens();
            System.out.println(" ComparaisonTokens sélectionnee");
        } else if (choix == 5) {
            comparateur = new ComparaisonSoundex();
            System.out.println("  ComparaisonSoundex sélectionnee");
        } else if (choix == 6) {
            comparateur = new ComparaisonJaroWinkler();
            System.out.println(" ComparaisonJaroWinkler sélectionnee");
        } else if (choix == 7) {
            comparateur = new ComparaisonOrdreTokens();
            System.out.println("   ComparaisonOrdreTokens selectionnee");
        } else {
            comparateur = new ComparaisonLevenshtein();
            System.out.println("  Choix invalide, ComparaisonLevenshtein utilisee par defaut");
        }
    }

    private void choixSelectionneur(Scanner sc) {
        System.out.println("\n4-- SELECTIONNEUR DE RESULTATS :");
        System.out.println("  1 - Seuil minimum de score");
        System.out.println("  2 - Premiers N resultats");
        System.out.print("Votre choix : ");

        int choix = lireEntier(sc.nextLine().trim());

        if (choix == 1) {
            System.out.print("  Seuil (entre 0.0 et 1.0) : ");
            double seuil = lireDouble(sc.nextLine().trim());
            selectionneurs.add(new SelectionneurSeuil(seuil));
            System.out.println(" SelectionneurSeuil(" + seuil + ") ajoutée");
        } else if (choix == 2) {
            System.out.print("  Nombre de resultats N : ");
            int n = lireEntier(sc.nextLine().trim());
            selectionneurs.add(new SelectionneurPremierN(n));
            System.out.println("  SelectionneurPremierN(" + n + ") ajoutée");
        } else {
            selectionneurs.add(new SelectionneurSeuil(0.8));
            System.out.println("  Choix invalide, SelectionneurSeuil(0.8) utilisé par defaut");
        }
    }

    private int lireEntier(String s) {
        int valeur = -1;
        try {
            valeur = Integer.parseInt(s);
        } catch (NumberFormatException e) {
        }
        return valeur;
    }

    private double lireDouble(String s) {
        double valeur = 0.8;
        try {
            valeur = Double.parseDouble(s);
        } catch (NumberFormatException e) {
        }
        return valeur;
    }

    private void afficherSeparateur(String titre) {
        System.out.println("\n========================================");
        System.out.println("  " + titre);
        System.out.println("========================================");
    }
}