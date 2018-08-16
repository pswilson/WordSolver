# The Basic Word Solver
# Creates a set of all valid (as found in the specified dictionary)
# words which can be made from the given set of letters

import Dictionary

MIN_WORD_LEN = 2
MAX_WORD_LEN = 16


def char_range(c_min, c_max, step=1):
    for c in range(ord(c_min), ord(c_max) + 1, step):
        yield chr(c)


class BasicSolver:
    def __init__(self, dictionary: Dictionary):
        self.dictionary = dictionary

    def solve_for_prefix(self, prefix: str, remaining: str):
        words_found = set()

        if len(prefix) == 0 or self.dictionary.is_prefix(prefix) or self.dictionary.is_word(prefix):
            # We're starting from scratch or have a prefix found in the dictionary so continue

            if len(prefix) >= MIN_WORD_LEN and self.dictionary.is_word(prefix):
                # It's a word
                words_found.add(prefix)

            if len(remaining) == 0:
                return words_found

            remaining_len = len(remaining)
            for i in range(0, remaining_len):
                # Iterate through all remaining letters
                if remaining[i] == ' ':
                    # If this is a blank then try all letters in it's place
                    for c in char_range('A', 'Z'):
                        new_prefix = prefix + str(c)
                        new_remainder = remaining[:i] + remaining[i+1:]
                        words_found |= self.solve_for_prefix(
                            new_prefix, new_remainder)
                else:
                    new_prefix = prefix + remaining[i]
                    new_remainder = remaining[:i] + remaining[i+1:]
                    words_found |= self.solve_for_prefix(
                        new_prefix, new_remainder)

        return words_found

    def solve(self, letters: str):
        return self.solve_for_prefix('', letters.upper())
