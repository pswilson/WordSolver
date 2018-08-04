import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class BasicSolver {
    private Dictionary dict;
    private Permutations perms;

    public BasicSolver(Dictionary dict) {
        this.dict = dict;
        this.perms = new Permutations();
    }

    public Set<String> solve(String letters) {
        Set<String> wordsFound = new HashSet<String>();

        // Hacky approach ... likely a better way that would allow easy short circuiting

        // Build each possible subset and check all permutations of the subset
        int len = letters.length();
        int numSubSets = 1 << len;

        for (int i = 1; i < numSubSets; i++) { // excluding 0 which maps to an empty subset
            String subSet = "";
            for (int bitPos = 0; bitPos < len; bitPos++) {
                if ((i & (1 << bitPos)) > 0) {
                    subSet += letters.charAt(bitPos);
                }
            }

            // Check all permutations of this subset for valid words
            wordsFound.addAll(checkPermutations(subSet));
        }

        return wordsFound;
    }

    private Set<String> checkPermutations(String letters) {
        Set<String> words = new HashSet<String>();
        List<String> permutations = perms.getPerms(letters);
        for (String permutation : permutations) {
            if (dict.isWord(permutation)) {
                words.add(permutation);
            }
        }
        return words;
    }
}
