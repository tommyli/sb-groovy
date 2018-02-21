// https://programmingpraxis.com/2018/02/20/n-gram-frequency/

// Given a string of characters and an n-gram size n, prepare a frequency table of all combinations of size n blocks of characters. For instance, with the string “Programming Praxis” and an n-gram size of 2, the n-grams are “Pr”, “ro”, “og”, “gr”, “ra”, “am”, “mm”, “mi”, “in”, “ng”, “g “, ” P”, “Pr”, “ra”, “ax”, “xi”, and “is”; the two n-grams “Pr” and “ra” appear twice, and all the others appear once.
// Your task is to write a program that computes a frequency table of n-grams. When you are finished, you are welcome to read or run a suggested solution, or to post your own solution or discuss the exercise in the comments below.

String phrase = "Programming Praxis"

def distributions(String phrase, ngramSize) {
  int i = 0
  def ngrams = []
  (phrase.size() - 1).times {
    String ngram = phrase.substring(i, i + ngramSize)
    i++
    ngrams << ngram
  }

  ngrams.groupBy()
    .collectEntries {k,v -> [(k): v.size]}
    .sort()
}

assert distributions(phrase, 2) == [
' P': 1,
'am': 1,
'ax': 1,
'g ': 1,
'gr': 1,
'in': 1,
'is': 1,
'mi': 1,
'mm': 1,
'ng': 1,
'og': 1,
'Pr': 2,
'ra': 2,
'ro': 1,
'xi': 1
].sort()
