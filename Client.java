public class Client {
    private String id;
    private String nom;

    public Client(String id, String nom) {
        this.id  = id;
        this.nom = nom;
    }

    public String getId()  { return id;  }
    public String getNom() { return nom; }

public String afficher() {
        String result = "id: " + id + ", nom: " + nom;
        System.out.println(result);
        return result;
    }
public void setNom(String nom) {
        this.nom = nom;
    }
}