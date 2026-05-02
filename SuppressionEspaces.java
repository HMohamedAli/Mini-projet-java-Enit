public class SuppressionEspaces extends PretraiteurNom {

    @Override
    public Nom traiter(Nom nom) {
        if (nom == null) return null;
        String s = nom.getNom().trim().replaceAll("\\s+", " ");
        return new Nom(nom.getId(), s);
    }
}
