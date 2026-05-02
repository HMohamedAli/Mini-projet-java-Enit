
public class GenerateurDeCandidats {

    public Nom[] genererCandidats(Nom[] liste, Nom cible) {
        if (liste == null || cible == null) return new Nom[0];
        
        // Filter candidates: only keep names that start with same letter
        // and have similar length (within 30%)
        String cibleNom = cible.getNom().toLowerCase();
        char cibleFirstChar = cibleNom.charAt(0);
        int cibleLen = cibleNom.length();
        
        int count = 0;
        for (Nom nom : liste) {
            String nomStr = nom.getNom().toLowerCase();
            if (nomStr.length() == 0) continue;
            
            // Check first letter match
            if (nomStr.charAt(0) != cibleFirstChar) continue;
            
            // Check length similarity (within 30%)
            int len = nomStr.length();
            double lenRatio = (double) Math.max(len, cibleLen) / Math.min(len, cibleLen);
            if (lenRatio > 1.3) continue; // Skip if length differs too much
            
            count++;
        }
        
        // Build candidates array
        Nom[] candidats = new Nom[count];
        int idx = 0;
        for (Nom nom : liste) {
            String nomStr = nom.getNom().toLowerCase();
            if (nomStr.length() == 0) continue;
            
            if (nomStr.charAt(0) != cibleFirstChar) continue;
            
            int len = nomStr.length();
            double lenRatio = (double) Math.max(len, cibleLen) / Math.min(len, cibleLen);
            if (lenRatio > 1.3) continue;
            
            candidats[idx++] = nom;
        }
        
        return candidats;
    }
}
