package main

import (
	"strings"
)

const (
	minWordLen int = 2
)

// We'll use a map for a set
type set map[string]bool

func solveForPrefix(dict Dictionary, wordsFound set, prefix string, remaining string) set {
	if len(prefix) == 0 || dict.IsPrefix(prefix) || dict.IsWord(prefix) {
		if len(prefix) >= minWordLen && dict.IsWord(prefix) {
			// It's a word
			wordsFound[prefix] = true
		}

		if len(remaining) == 0 {
			return wordsFound
		}

		var newPrefix string
		var newRemainder string
		remLen := len(remaining)
		for i := 0; i < remLen; i++ {
			// Iterate through all remaining letters
			if remaining[i] == ' ' {
				// If this is a blank then try all letters in it's place
				alphaStart := int('A')
				alphaEnd := int('Z')
				for n := alphaStart; n <= alphaEnd; n++ {
					newPrefix = prefix + string(n)
					newRemainder = remaining[:i] + remaining[i+1:]
					solveForPrefix(dict, wordsFound, newPrefix, newRemainder)
				}
			} else {
				newPrefix = prefix + string(remaining[i])
				newRemainder = remaining[:i] + remaining[i+1:]
				solveForPrefix(dict, wordsFound, newPrefix, newRemainder)
			}
		}
	}

	return wordsFound
}

// Solve takes the given set of letters and returns a list of all possible words that could be made from the letters
func Solve(dict Dictionary, letters string) []string {
	wordSet := make(set)
	solveForPrefix(dict, wordSet, "", strings.ToUpper(letters))

	wordList := make([]string, len(wordSet))
	i := 0
	for k := range wordSet {
		wordList[i] = k
		i++
	}

	return wordList
}
