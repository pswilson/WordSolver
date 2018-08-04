# A Basic Word Solver - Java version

### Intro

This is a java implementation for a Word Solver. The basic problem is to find all possible words you could spell from a given set of letters. Not all letters must be used in the words but the words must be 2 letters or longer. The dictionary to check against is the Scrabble tournament word list (twl06.txt.)

### Solution Overview

This code uses two different approaches.

#### Basic

The basic approach is an implementation you might create on a first pass after decomposing the problem. This simplistic solution:

- creates all possible subsets of the given letters,
- generates all permutations of each subset,
- checks each permutation against the dictionary.

This approach may seem ok for small test sets of letters but there is a lot of unnecessary work being done and it quickly becomes inefficient with larger sets and on different letter distributions.

#### Enhanced

The enhanced approach short circuits much of the work done in the basic solution by building up possible words a letter at a time, checking that what's built is a prefix or a word and bailing out if it is not a prefix of any longer words. There are still optimizations that could be done to improve the performance of this solver.

### The Dictionary Implementation

The dictionary used here is a simple implementation of a trie (or 'prefix tree') with each node containing the current prefix, a flag indicating if this is a word, and a map of prefixes of child nodes. A flat dictionary might be more efficient as is used by both solvers.

### Project Layout

The project has been kept simple and there's nothing special required to build and run the code other than a JDK. You can create build/configuration files if you want to use your build tool of choice.

If you have [sbt](https://www.scala-sbt.org/index.html) installed you can simply do:

    sbt "run <letters> [-c]"

NOTE: Since there are two main methods you will be prompted for which one to run.

The -c option will run (compare) both the Basic and Enhanced solvers.

### License

This code is released under the [MIT](https://choosealicense.com/licenses/mit/) license.
