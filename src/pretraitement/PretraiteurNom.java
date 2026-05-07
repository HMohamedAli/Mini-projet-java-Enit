package pretraitement;

import main.Nom;

/**
 * Interface de prétraitement. Modifie la liste de tokens nomPretraite du Nom.
 */
public interface PretraiteurNom {
    public abstract void traiter(Nom nom);
}