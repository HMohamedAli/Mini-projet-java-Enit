
import java.util.ArrayList;
import java.util.List;

public class SelectionneurDeResultat {

    public Nom[] selectionnerResultat(Nom[] liste, double seuil,
                                      ComparateurDeNoms comparateur,
                                      Nom cible) {
        if (liste == null || cible == null) return new Nom[0];

        List<Nom> cibleList = new ArrayList<>();
        cibleList.add(cible);
        
        List<Nom> resultats = new ArrayList<>();
        for (Nom n : liste) {
            List<Nom> nList = new ArrayList<>();
            nList.add(n);
            if (comparateur.comparer(cibleList, nList) >= seuil) {
                resultats.add(n);
            }
        }
        return resultats.toArray(new Nom[0]);
    }
}