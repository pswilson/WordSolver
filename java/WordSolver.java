import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WordSolver {
    private static final String DICTIONARY_FILE = "twl06.txt";
    private static final int MIN_WORD_LEN = 2;
    private static final int MAX_WORD_LEN = 16;

    public WordSolver() {
    }

    // Runs the enhanced solver and optionally the basic solver to compare times
    public void runSolvers(String letters, Boolean compare) {
        Dictionary dict = new Dictionary(DICTIONARY_FILE);
        EnhancedSolver enhancedSolver = new EnhancedSolver(dict);

        // for timing
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSSZ");
        long startTime;
        long endTime;
        long duration;

        startTime = System.currentTimeMillis();
        Set<String> possibleWords = enhancedSolver.solve(letters);
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        System.out.println(
                "\nThe Enhanced Solver took " + duration + " milliseconds (or " + sdf.format(new Date(duration)) + ")");

        if (compare) {
            BasicSolver basicSolver = new BasicSolver(dict);
            startTime = System.currentTimeMillis();
            Set<String> possibleWords1 = basicSolver.solve(letters);
            endTime = System.currentTimeMillis();
            duration = endTime - startTime;
            System.out.println(
                    "The Basic Solver took " + duration + " milliseconds (or " + sdf.format(new Date(duration)) + ")");

            System.out.println("The two apporaches produced "
                    + (possibleWords.equals(possibleWords1) ? "the same" : "DIFFERENT") + " results.\n");
        }

        System.out.println("Given \"" + letters + "\" you could make " + possibleWords.size() + " words:\n");

        displayOrderedWords(possibleWords);
    }

    // Display words grouped by length and ordered alphabetically within grouo
    private void displayOrderedWords(Set<String> words) {
        // Sort by word length and then alphabetically within each length

        // iterate through the set
        // get the word length
        // add to a length bucket
        // iterate through the buckets and sort each bucket
        // May be simpler to just have an array arrays
        HashMap<Integer, List<String>> orderedWords = new HashMap<Integer, List<String>>();
        for (String word : words) {
            int wordLength = word.length();
            List<String> bucketWords = orderedWords.get(wordLength);
            if (bucketWords == null) {
                bucketWords = new ArrayList<String>();
            }
            bucketWords.add(word);
            orderedWords.put(wordLength, bucketWords);
        }
        // Now that we have all of the words grouped by length sort each group
        // alphabetically
        for (Integer bucketKey : orderedWords.keySet()) {
            List<String> bucketWords = orderedWords.get(bucketKey);
            if (bucketWords != null) {
                System.out.println("===== " + bucketKey + " Letters =====");
                String[] sortBucketWords = bucketWords.toArray(new String[0]);
                Arrays.sort(sortBucketWords);
                // Print out the word
                for (String word : sortBucketWords) {
                    System.out.println(word);
                }
            }
        }
        System.out.println();
    }

    // Simple display of the words
    private static void displayWords(Set<String> words) {
        for (String word : words) {
            System.out.println(word);
        }
    }

    public static void main(String[] args) {
        if (args.length >= 1 && args[0].length() >= MIN_WORD_LEN && args[0].length() <= MAX_WORD_LEN) {
            boolean compare = false;
            if (args.length >= 2) {
                compare = args[1].equals("-c");
            }
            WordSolver solver = new WordSolver();
            solver.runSolvers(args[0].toUpperCase(), compare);
        } else {
            System.out.println("Usage: Solver <available letters)> [-c]");
            System.out.println("\t<available letters)>: a set of " + MIN_WORD_LEN + "-" + MAX_WORD_LEN + " letters");
            System.out.println("\t-c: to also run the basic solver and compare times");
        }
    }
}
