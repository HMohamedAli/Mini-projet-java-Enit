public class TraitementEnMajuscules extends TraitementNom {
    @Override
    public String traiter(String nom) {
        if (nom == null) return "";
        return nom.toUpperCase();
    }
}