package main;

import java.util.ArrayList;
import java.util.List;

public class ListeMots {

    private final String     nomListe;
    private final String     chemin;
    private final List<Nom>  noms;

    public ListeMots(String nomListe, String chemin, List<Nom> noms) {
        this.nomListe = nomListe;
        this.chemin   = chemin;
        this.noms     = new ArrayList<>();
        if (noms != null) {
            this.noms.addAll(noms);
        }
    }

    public String    getNomListe() { return nomListe; }
    public String    getChemin()   { return chemin;   }
    public List<Nom> getNoms()     { return noms;     }
}