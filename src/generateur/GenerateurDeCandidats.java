package generateur;

import java.util.List;
import main.Nom;

public interface GenerateurDeCandidats {
   
    public  List<Nom[]> genererCandidats(List<Nom> listeSource, List<Nom> listeCible);
}