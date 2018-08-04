import java.util.Set;
import java.util.HashSet;

public class EnhancedSolver {
    private static final int MIN_WORD_LEN = 2;

    private Dictionary dict;
    private Permutations perms;

    public EnhancedSolver(Dictionary dict) {
        this.dict = dict;
        this.perms = new Permutations();
    }

    public Set<String> solve(String letters) {
        return solveForPrefix("", letters);
    }

    private Set<String> solveForPrefix(String prefix, String remainingLetters) {
        // System.out.println("Incoming ...");
        // System.out.println("Prefix: " + prefix);
        // System.out.println("Remaining: " + remainingLetters);

        Set<String> wordsFound = new HashSet<String>();

        // if prefix is not a word prefix then bail out
        // if prefix is a word add it to the word List
        //
        // iterate through characters in remainder
        // append each character to prefix
        // build new remainder by joining chaacters before and after seleted character
        // call solveForPrefix
        //

        if (prefix.length() == 0 || dict.containsPrefix(prefix)) {
            // We're starting from scratch or have a prefix found in the dictionary so
            // continue

            if (prefix.length() >= MIN_WORD_LEN && dict.isWord(prefix)) {
                // It's a word
                wordsFound.add(prefix);
            }

            if (remainingLetters.isEmpty()) {
                // System.out.println("No remaining letters so bailing out");
                return wordsFound;
            }

            int len = remainingLetters.length();
            for (int i = 0; i < len; i++) {
                // Iterate through all remaining letters
                // If this is a blank then try all letters in it's place
                if (remainingLetters.charAt(i) == ' ') {
                    for (char c = 'A'; c <= 'Z'; c++) {
                        String newPrefix = prefix + c;

                        String newRemainder = "" + (i > 0 ? remainingLetters.substring(0, i) : "")
                                + (i < len ? remainingLetters.substring(i + 1) : "");
                        wordsFound.addAll(solveForPrefix(newPrefix, newRemainder));
                    }
                } else {
                    String newPrefix = prefix + remainingLetters.charAt(i);

                    String newRemainder = "" + (i > 0 ? remainingLetters.substring(0, i) : "")
                            + (i < len ? remainingLetters.substring(i + 1) : "");
                    wordsFound.addAll(solveForPrefix(newPrefix, newRemainder));
                }
            }
        }

        return wordsFound;
    }
}
