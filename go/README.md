# A Basic Word Solver - Go version

### Intro

This is a Go implementation for a Word Solver. The basic problem is to find all possible words you could spell from a given set of letters. Blanks/spaces given in the input set can be interpreted as any letter. Not all letters must be used in the words but the words must be 2 letters or longer. The dictionary to check against is the Scrabble tournament word list (twl06.txt.)

### Solution Overview

This code uses the same enhanced approach as in the java and python implementations.

#### The Solver Implementation

This implementation builds up possible words a letter at a time, checking that what's built is a prefix or a word and bailing out if it is not a prefix of any longer words. There are still optimizations that could be done to improve the performance of this solver.

### The Dictionary Implementation

The dictionary used here is a map with each entry key being a word and/or a prefix of a word and the value being a simple struct containing flags indicating if this is a prefix or word. A limitation of using the map rather than a trie as in the Java implementation is that it would be more difficult (more work) to (fully) remove a word since it's harder to tell if any prefixes for the word being removed are also prefixes for other words.

### Project Layout

The project has been kept simple (flat) and there's nothing special required run the code other than having the go tools installed.

You can locate the code wherever you'd like in your go tree or simply run the code such as:

    go run *.go abc

### License

This code is released under the [MIT](https://choosealicense.com/licenses/mit/) license.
