public class ComparaisonExacte extends Comparaison {

    public ComparaisonExacte(TraitementNom traitement) {
        super(traitement);
    }

    @Override
    public double comparer(String a, String b) {
        String pa = preparer(a);
        String pb = preparer(b);
        if(pa.equals(pb)) return 1.0;
        return 0.0;
    }
}