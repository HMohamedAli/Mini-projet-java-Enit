public class Nom {
    private String id;
    private String nom;

    public Nom(String id, String nom) {
        this.id  = id;
        this.nom = nom;
    }

    public String getId()       { return id;  }
    public String getNom()      { return nom; }
    public String getId(int i)  { return id.substring(0, Math.min(i, id.length())); }
    public String getNom(int i) { return nom.substring(0, Math.min(i, nom.length())); }

    @Override
    public String toString() { return "[" + id + "] " + nom; }
}