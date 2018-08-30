# Word Solver
import sys
import datetime

import Dictionary
import BasicSolver


def usage():
    print('\nUsage: {} <letters>   # use a space for a wildcard\n'.format(sys.argv[0]))
    sys.exit()


def display_sorted_words(words):
    # print in alphabetical within length order ...

    # tl is list of tuples of ('word', len(word))
    tl = [(w, len(w)) for w in words]
    # stl is sorted list of tuples, sorted by word
    stl = sorted(tl, key=lambda w: w[0])
    # sstl is sorted list of tuples, sorted by len
    sstl = sorted(stl, key=lambda l: l[1])

    pl = 0
    for t in sstl:
        if t[1] != pl:
            pl = t[1]
            print('===== {} Letters ====='.format(pl))
        print('{}'.format(t[0]))


if len(sys.argv) < 2:
    usage()

letters = sys.argv[1]

# Pass in the dictionary file name since we don't get the path correct if using the default
startTime = datetime.datetime.now()
d = Dictionary.Dictionary('./twl06.txt')
endTime = datetime.datetime.now()
delta = endTime - startTime
print('\nDictionary load took {}'.format(delta))

solver = BasicSolver.BasicSolver(d)

startTime = datetime.datetime.now()
words = solver.solve(letters)
endTime = datetime.datetime.now()
delta = endTime - startTime
print('\nApproach took {}'.format(delta))

# words = s.solve(letters.lower())
print('\nGiven "{}", you could make {} words:'.format(letters, len(words)))
display_sorted_words(words)
