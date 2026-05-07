package main;

import java.util.ArrayList;
import java.util.List;

public class Nom {

    private final String id;
    private final String nomOriginal;
    private final String nomListe;
    private List<String> nomPretraite; 

    public Nom(String id, String nomOriginal, String nomListe) {
        this.id = id;
        this.nomOriginal = nomOriginal;
        this.nomListe = nomListe;
        this.nomPretraite = new ArrayList<>();
        this.nomPretraite.add(nomOriginal);
    }

    public String getId() {
        return id;
    }

    public String getNomOriginal() {
        return nomOriginal;
    }

    public String getNomListe() {
        return nomListe;
    }

    public List<String> getNomPretraite() {
        return nomPretraite;
    }

    public void setNomPretraite(List<String> tokens) {
        this.nomPretraite = tokens;
    }

    @Override
    public String toString() {
        return "Nom{id='" + id + "', original='" + nomOriginal + "', tokens=" + nomPretraite + "}";
    }
}