public class RemplacementCaracteresSpeciaux extends PretraiteurNom {

    @Override
    public Nom traiter(Nom nom) {
        if (nom == null) return null;
        String s = nom.getNom();
        s = s.replace("-", "").replace("_", "").replace("'", "");
        return new Nom(nom.getId(), s);
    }
}
