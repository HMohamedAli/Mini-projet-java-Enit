package main;

import java.util.ArrayList;
import java.util.List;

import comparaison.ComparateurDeNoms;
import generateur.GenerateurDeCandidats;
import pretraitement.PretraiteurNom;
import selectionneur.SelectionneurDeResultat;

public class MoteurDeMatching {

    private final Configuration config;

    public MoteurDeMatching(Configuration config) {
        this.config = config;
    }

    public List<Object[]> executer() {

        List<Nom> listeSource = config.getListeSource().getNoms();
        List<Nom> listeCible  = config.getListeCible().getNoms();

        
        appliquerPretraitements(listeSource);
        appliquerPretraitements(listeCible);

        
        GenerateurDeCandidats generateur = config.getGenerateur();
        List<Nom[]> candidats = generateur.genererCandidats(listeSource, listeCible);

    
        ComparateurDeNoms comparateur = config.getComparateur();
        List<Object[]> scores = comparateur.comparerTous(candidats);

      
        List<Object[]> selectionnes = selectionnerResultats(scores);

 
        LivreurDeResultat livreur = new LivreurDeResultat();
        livreur.livrerResultat(selectionnes);

        return selectionnes;
    }

    private void appliquerPretraitements(List<Nom> liste) {
        for (PretraiteurNom p : config.getPretraiteurs()) {
            for (Nom n : liste) {
                p.traiter(n);
            }
        }
    }

    private List<Object[]> selectionnerResultats(List<Object[]> scores) {
        List<Object[]> resultats = scores;
        for (SelectionneurDeResultat s : config.getSelectionneurs()) {
            resultats = s.selectionner(resultats);
        }
        return resultats;
    }
}