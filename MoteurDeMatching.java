
public class MoteurDeMatching {

    private double                 seuil;
    private ComparateurDeNoms      comparateur;
    private PretraiteurNom[]       pretraiteurs;
    private int                    nbPretraiteurs;
    private GenerateurDeCandidats  generateur;
    private SelectionneurDeResultat selectionneurs;
    private LivreurDeResultat      livreur;

    private static final int MAX_PRETRAITEURS = 5;

    public MoteurDeMatching(ComparateurDeNoms comparateur, double seuil) {
        this.comparateur     = comparateur;
        this.seuil           = seuil;
        this.pretraiteurs    = new PretraiteurNom[MAX_PRETRAITEURS];
        this.nbPretraiteurs  = 0;
        this.generateur      = new GenerateurDeCandidats();
        this.selectionneurs  = new SelectionneurDeResultat();
        this.livreur         = new LivreurDeResultat();
    }

    public void setSeuil(double seuil) {
        this.seuil = seuil;
    }

    public double getSeuil() { return seuil; }


    public void ajouterPretraiteur(PretraiteurNom p) {
        if (nbPretraiteurs < MAX_PRETRAITEURS) {
            pretraiteurs[nbPretraiteurs++] = p;
        }
    }

  
    private Nom pretraiter(Nom nom) {
        Nom resultat = nom;
        for (int i = 0; i < nbPretraiteurs; i++) {
            resultat = pretraiteurs[i].traiter(resultat);
        }
        return resultat;
    }

  
    public void rechercher(Nom[] listeClients, Nom[] listeSanctions) {
        System.out.println("=== Recherche en masse ===");
        for (Nom client : listeClients) {
            trouverMatch(listeSanctions, client);
        }
    }


    public void trouverMatch(Nom[] liste, Nom nom) {
      
        Nom ciblePretraitee = pretraiter(nom);

        Nom[] listePretraitee = new Nom[liste.length];
        for (int i = 0; i < liste.length; i++) {
            listePretraitee[i] = pretraiter(liste[i]);
        }


        Nom[] candidats = generateur.genererCandidats(listePretraitee, ciblePretraitee);

      
        Nom[] resultats = selectionneurs.selectionnerResultat(
            candidats, seuil, comparateur, ciblePretraitee);

   
        System.out.println("Recherche pour : " + nom);
        if (resultats.length == 0) {
            System.out.println("  Aucune correspondance (seuil=" + seuil + ")");
        } else {
            System.out.println("  " + resultats.length + " correspondance(s) :");
            livreur.livrerResultat(resultats);
        }
    }
}