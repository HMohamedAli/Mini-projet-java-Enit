package comparaison;

import main.Nom;

public class ComparaisonSoundex implements ComparateurDeChaines, ComparateurDeNoms {

    @Override
    public double comparer(String a, String b) {
        if (a == null || b == null) return 0.0;
        return soundex(a).equals(soundex(b)) ? 1.0 : 0.0;
    }

    @Override
    public double comparer(Nom a, Nom b) {
        if (a == null || b == null) return 0.0;
        String sa = String.join(" ", a.getNomPretraite());
        String sb = String.join(" ", b.getNomPretraite());
        return comparer(sa, sb);
    }

    private String soundex(String s) {
        if (s == null || s.isEmpty()) return "";
        s = s.toUpperCase();
        char first = s.charAt(0);
        StringBuilder code = new StringBuilder();
        code.append(first);
        String prevCode = "";
        for (int i = 1; i < s.length(); i++) {
            String codeChar = mapToDigit(s.charAt(i));
            if (codeChar == null) continue;
            if (!codeChar.equals(prevCode)) {
                code.append(codeChar);
                prevCode = codeChar;
            }
        }
        while (code.length() < 4) code.append('0');
        return code.substring(0, 4);
    }

    private String mapToDigit(char c) {
        switch (c) {
            case 'B': case 'F': case 'P': case 'V': return "1";
            case 'C': case 'G': case 'J': case 'K': case 'Q': case 'S': case 'X': case 'Z': return "2";
            case 'D': case 'T': return "3";
            case 'L': return "4";
            case 'M': case 'N': return "5";
            case 'R': return "6";
            default: return null;
        }
    }
}
