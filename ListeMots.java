
public abstract class ListeMots {

    private String nomListe;
    private String emplacement;  

    public ListeMots(String nomListe, String emplacement) {
        this.nomListe   = nomListe;
        this.emplacement = emplacement;
    }

    public String getNomListe()   { return nomListe;   }
    public String getEmplacement(){ return emplacement; }

   public abstract String[] getList();
}