import java.util.List;

public class ComparaisonPartielle extends ComparateurDeNoms {

    @Override
    public double comparer(List<Nom> a, List<Nom> b) {
        if (a == null || b == null || a.isEmpty() || b.isEmpty()) return 0.0;
        
        Nom nomA = a.get(0);
        Nom nomB = b.get(0);
        
        if (nomA == null || nomB == null) return 0.0;
        
        String strA = nomA.getNom().toLowerCase();
        String strB = nomB.getNom().toLowerCase();
        
        if (strA.contains(strB) || strB.contains(strA)) {
            return 0.8; 
        }
        
        int commonChars = 0;
        for (char c : strA.toCharArray()) {
            if (strB.indexOf(c) >= 0) {
                commonChars++;
            }
        }
        
        int maxLen = Math.max(strA.length(), strB.length());
        if (maxLen == 0) return 1.0;
        
        return (double) commonChars / maxLen;
    }
}
