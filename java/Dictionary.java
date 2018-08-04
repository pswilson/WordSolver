import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

public class Dictionary {
    // Create a root dictionary node
    DictionaryNode dictionary = new DictionaryNode("", false);
    int wordCount = 0;

    public Dictionary() {
        // Start off with an empty dictionary
    }

    public Dictionary(String dictionaryFile) {
        // Initialize a dictionary from a file
        System.out.println("Creating dictionary ...");
        initFromFile(dictionaryFile);
    }

    public boolean isWord(String word) {
        DictionaryNode node = findNode(word);
        return (node != null && node.isWord());
    }

    public boolean containsPrefix(String word) {
        DictionaryNode node = findNode(word);
        return (node != null);
    }

    private DictionaryNode findNode(String word) {
        if (word == null || word.length() == 0) {
            return null;
        }

        DictionaryNode curNode = dictionary;
        DictionaryNode nextNode = null;
        String prefix = "";
        for (char c : word.toCharArray()) {
            prefix += c;
            nextNode = curNode.children.get(prefix);
            if (nextNode == null) {
                return null;
            }
            curNode = nextNode;
        }
        return curNode;
    }

    public void addWord(String word) throws BadDataException {
        if (word == null) {
            throw new BadDataException();
        }
        if (word.length() == 0) {
            // nothing to do
            return;
        }

        boolean isWord;
        String prefix = "";
        DictionaryNode curNode = dictionary;
        DictionaryNode nextNode;
        int wordLen = word.length();

        word = word.toUpperCase();
        for (char c : word.toCharArray()) {
            prefix += c;
            nextNode = curNode.children.get(prefix);
            isWord = prefix.length() == wordLen;
            if (nextNode == null) {
                nextNode = new DictionaryNode(prefix, isWord);
                curNode.children.put(prefix, nextNode);
            }
            curNode = nextNode;
        }
    }

    private void initFromFile(String fileName) {
        // NOTE: The error handling is minimal for this specific use.

        wordCount = 0;
        BufferedReader reader = null;
        try {
            Path path = FileSystems.getDefault().getPath(".", fileName);
            reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            System.out.println("Reading the dictionary from '" + fileName + "'.");
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    addWord(line);
                    wordCount++;
                } catch (BadDataException e) {
                    System.out.println("Ignoring bogus word");
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading dictionary file: " + e);
            System.exit(1);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
        System.out.println("Added " + wordCount + " words to the dictionary.");
    }

    private class DictionaryNode {
        public String value;
        private boolean isWord;

        // NOTE: Might be simpler to use a flat dictionary with every prefix and word
        // being a node
        // and marked appropriately as a prefix and/or word
        // NOTE: Add word will need to walk through the characters in a word and add or
        // update each prefix
        // Remove word would need to walk backword through a word removing the last
        // charaacter and updating the appropriate 'parent' prefix nodes
        public Map<String, DictionaryNode> children = new HashMap<String, DictionaryNode>();

        public DictionaryNode(String value, boolean isWord) {
            this.value = value;
            this.isWord = isWord;
        }

        public boolean isPrefix() {
            return !children.isEmpty();
        }

        public boolean isWord() {
            return isWord;
        }
    }

    public class BadDataException extends Exception {
    };
}
