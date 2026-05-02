
public class LivreurDeResultat {

   
    public void livrerResultat(Nom[] liste) {
        if (liste == null || liste.length == 0) {
            System.out.println("  Aucun resultat.");
            return;
        }
        for (Nom n : liste) {
            System.out.println("  --> " + n);
        }
    }
}