package generateur;

import java.util.List;
import main.Nom;

public abstract class GenerateurDeCandidats {
   
    public abstract List<Nom[]> genererCandidats(List<Nom> listeSource, List<Nom> listeCible);
}