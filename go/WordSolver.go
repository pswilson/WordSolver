package main

import (
	"fmt"
	"os"
	"sort"
	"time"
)

func displaySortedWords(words []string) {
	sort.Slice(words, func(i, j int) bool {
		l1 := len(words[i])
		l2 := len(words[j])

		if l1 != l2 {
			return l1 < l2
		}

		return words[i] < words[j]
	})

	prevLen := 0
	curLen := 0
	for _, w := range words {
		curLen = len(w)
		if curLen != prevLen {
			fmt.Printf("===== %d Letters =====\n", curLen)
			prevLen = curLen
		}
		fmt.Println(w)
	}
}

func main() {
	if len(os.Args) < 2 {
		fmt.Printf("Usage: %s <letters>  // use a space for a wildcard\n", os.Args[0])
		os.Exit(-1)
	}

	letters := os.Args[1]

	startTime := time.Now()
	dict := LoadDictionary("./twl06.txt")
	endTime := time.Now()
	delta := endTime.Sub(startTime)

	fmt.Println("\nDictionary load took", delta)

	startTime = time.Now()
	words := Solve(dict, letters)
	endTime = time.Now()
	delta = endTime.Sub(startTime)

	fmt.Println("\nApproach took", delta)

	fmt.Printf("\nGiven \"%s\", you could make %d words:\n", letters, len(words))
	displaySortedWords(words)
}
