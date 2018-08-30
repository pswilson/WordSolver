# Basic Word Dictionary
#   to support a Word Solver and Boggle Solver

DEFAULT_DICTIONARY_FILE = 'twl06.txt'


# Private class to keep track of whether a node is a prefix and/or a word
class _DictionaryNode:
    is_prefix = False
    is_word = False


# The Dictionary class itself
class Dictionary:
    def __init__(self, dictionary_file = DEFAULT_DICTIONARY_FILE):
        # Use a map of DictionaryNodes to store each prefix/word
        self.data = {}
        self.prefix_count = 0
        self.word_count = 0

        # TODO: Error handling
        # TODO: User pkg_resources to get the file path
        with open(dictionary_file, 'r') as f:
            for line in f:
                self.add_word(line.strip())

        print('Added {} prefixes and {} words to the dictionary.'.format(self.prefix_count, self.word_count))



    def add_word(self, word: str):
        if len(word) == 0:
            return

        # Normalize case before we put things in the dictionary
        word = word.upper()

        # Build up all of the prefixes for this word
        # add to the dictionary if necessary and set the prefix flag
        for i in range(1, len(word)):
            p = word[:i]
            if not p in self.data:
                self.data[p] = _DictionaryNode()

            if not self.data[p].is_prefix:
                self.prefix_count += 1
                self.data[p].is_prefix = True

        # make sure we add the word itself and set the word flag
        if not word in self.data:
            self.data[word] = _DictionaryNode()
            self.word_count += 1
        self.data[word].is_word = True


    def remove_word(self, word: str):
        print('removing word {}'.format(word))

        word = word.upper()

        # TODO:
        #   This is problematic with the flat implementation
        #   since we won't be able to easily tell if any prefixes
        #   from this word are also prefixes for other words


    def is_prefix(self, word: str) -> bool:
        if word in self.data:
            return self.data[word].is_prefix
        return False


    def is_word(self, word: str) -> bool:
        if word in self.data:
            return self.data[word].is_word
        return False
