package main;

import comparaison.ComparateurDeNoms;
import generateur.GenerateurDeCandidats;
import pretraitement.PretraiteurNom;
import selectionneur.SelectionneurDeResultat;

import java.util.List;

public class MoteurDeMatching {

    private final Configuration config;

    public MoteurDeMatching(Configuration config) {
        this.config = config;
    }

    public List<CoupleScore> executer() {

        List<Nom> listeSource = config.getListeSource().getNoms();
        List<Nom> listeCible  = config.getListeCible().getNoms();

        appliquerPretraitements(listeSource);
        appliquerPretraitements(listeCible);

        GenerateurDeCandidats generateur = config.getGenerateur();
        List<Nom[]> candidats = generateur.genererCandidats(listeSource, listeCible);

        ComparateurDeNoms comparateur = config.getComparateur();
        List<CoupleScore> scores = comparateur.comparerTous(candidats);

        List<CoupleScore> selectionnes = selectionnerResultats(scores);

        
        new LivreurDeResultat().livrerResultat(selectionnes);

        return selectionnes;
    }

    private void appliquerPretraitements(List<Nom> liste) {
        for (PretraiteurNom p : config.getPretraiteurs()) {
            for (Nom n : liste) {
                p.traiter(n);
            }
        }
    }

    private List<CoupleScore> selectionnerResultats(List<CoupleScore> scores) {
        List<CoupleScore> resultats = scores;
        for (SelectionneurDeResultat s : config.getSelectionneurs()) {
            resultats = s.selectionner(resultats);
        }
        return resultats;
    }
}