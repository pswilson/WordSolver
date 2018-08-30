package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strings"
)

// TODO: Add error handling

type dictionaryNode struct {
	isPrefix bool
	isWord   bool
}

// Dictionary conatins the words and prefixes
type Dictionary map[string]*dictionaryNode

func newDictionaryNode(isPrefix bool, isWord bool) *dictionaryNode {
	n := new(dictionaryNode)
	n.isPrefix = isPrefix
	n.isWord = isWord
	return n
}

// LoadDictionary reads thew specified file and adds all words to the dictionary
func LoadDictionary(fileName string) Dictionary {
	var dict = make(Dictionary)
	wordCount := 0
	prefixCount := 0

	file, err := os.Open(fileName)
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		w, p := addWord(dict, strings.ToUpper(scanner.Text()))
		wordCount += w
		prefixCount += p
	}

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}

	fmt.Printf("Added %d prefixes and %d words to the dictionary.\n", prefixCount, wordCount)

	return dict
}

func addWord(dict Dictionary, word string) (int, int) {
	if len(word) == 0 {
		return 0, 0
	}

	var prefix = ""
	var n *dictionaryNode
	prefixCount := 0
	for _, c := range word {
		prefix += string(c)
		n = dict[prefix]
		if n == nil {
			// We haven't seen this prefix before
			dict[prefix] = newDictionaryNode(false, false)
		}
		if len(prefix) != len(word) && !dict[prefix].isPrefix {
			dict[prefix].isPrefix = true
			prefixCount++
		}
	}
	dict[word].isWord = true

	return 1, prefixCount
}

// IsPrefix checks to see if the specified letters begin a word in the dictionary
func (d Dictionary) IsPrefix(letters string) bool {
	if n, ok := d[letters]; ok {
		return n.isPrefix
	}
	return false
}

// IsWord checks to see if the specified word is in the dictionary
func (d Dictionary) IsWord(word string) bool {
	if n, ok := d[word]; ok {
		return n.isWord
	}
	return false
}
