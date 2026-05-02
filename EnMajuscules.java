
public class EnMajuscules extends PretraiteurNom {

    @Override
    public Nom traiter(Nom b) {
        if (b == null) return null;
        return new Nom(b.getId(), b.getNom().toUpperCase());
    }
}