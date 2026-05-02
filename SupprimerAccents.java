
public class SupprimerAccents extends PretraiteurNom {

    @Override
    public Nom traiter(Nom b) {
        if (b == null) return null;
        String s = b.getNom().toLowerCase();
        s = s.replace("é","e").replace("è","e").replace("ê","e").replace("ë","e");
        s = s.replace("à","a").replace("â","a").replace("ä","a");
        s = s.replace("ù","u").replace("û","u").replace("ü","u");
        s = s.replace("î","i").replace("ï","i");
        s = s.replace("ô","o").replace("ö","o");
        s = s.replace("ç","c").replace("ñ","n");
        return new Nom(b.getId(), s);
    }
}