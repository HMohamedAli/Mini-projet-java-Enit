public class Main {
    
    public static void main(String[] args) {
        System.out.println("=== Test Moteur de Matching ===\n");
        
        // Create some test data
        Nom[] listeSanctions = {
            new Nom("S001", "Ahmed Hassan"),
            new Nom("S002", "Ali Mohamed"),
            new Nom("S003", "Ahmed Ali"),
            new Nom("S004", "Bernard Dupont"),
            new Nom("S005", "Bruno Durand")
        };
        
        Nom[] listeClients = {
            new Nom("C001", "Ahmed Hassan"),
            new Nom("C002", "Ali Mohamad"),
            new Nom("C003", "Bernard Dupont"),
            new Nom("C004", "Ahmed Hasan")
        };
        
        // Test 1: Comparaison Exacte with Preprocessing
        System.out.println("--- Test 1: Exact Match with Preprocessing ---");
        MoteurDeMatching moteur1 = new MoteurDeMatching(
            new ComparaisonExacte(), 
            1.0  // Seuil 100%
        );
        moteur1.ajouterPretraiteur(new EnMajuscules());
        moteur1.ajouterPretraiteur(new SupprimerAccents());
        moteur1.ajouterPretraiteur(new SuppressionEspaces());
        
        moteur1.rechercher(listeClients, listeSanctions);
        
        // Test 2: Partial Match with Different Preprocessing
        System.out.println("\n--- Test 2: Partial Match (Similarity >= 0.6) ---");
        MoteurDeMatching moteur2 = new MoteurDeMatching(
            new ComparaisonPartielle(), 
            0.6  // Seuil 60%
        );
        moteur2.ajouterPretraiteur(new EnMajuscules());
        moteur2.ajouterPretraiteur(new SupprimerAccents());
        moteur2.ajouterPretraiteur(new RemplacementCaracteresSpeciaux());
        
        moteur2.rechercher(listeClients, listeSanctions);
        
        // Test 3: Direct search with one client
        System.out.println("\n--- Test 3: Direct Search for Single Client ---");
        Nom clientTest = new Nom("C099", "Ahmed Hasan");
        moteur1.trouverMatch(listeSanctions, clientTest);
    }
}
