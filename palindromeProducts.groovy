// Palindrome Products
// Detect palindrome products in a given range.

// A palindromic number is a number that remains the same when its digits are reversed. For example, 121 is a palindromic number but 112 is not.

// Given a range of numbers, find the largest and smallest palindromes which are products of numbers within that range.

// Your solution should return the largest and smallest palindromes, along with the factors of each within the range. If the largest or smallest palindrome has more than one pair of factors within the range, then return all the pairs.

def range1 = new ArrayList((1..9))
def range2 = new ArrayList((10..99))
def smallRange = new ArrayList((1..3))

def isPalindrome(Long num) {
  num.toString() == num.toString().reverse()
}

// note that ([range, range].combinations()) does not work, excludes pairs with same values
// implement my own version instead
def combinations(numRange) {
  def result = []
  for (int i = 0; i < numRange.size(); i++) {
    for (int j = i; j < numRange.size(); j++) {
      result << [numRange[i], numRange[j]]
    }
  }

  return result
}

def palindromeProducts(numRange) {
  combinations(numRange)
    .findAll {isPalindrome(it[0] * it[1])}
}

def minMaxPaliProducts(numRange) {
  def results = palindromeProducts(numRange)
  def minPair = results.min {it[0] * it[1]}
  def maxPair = results.max {it[0] * it[1]}
  def minResults = results.findAll {it[0] * it[1] == minPair[0] * minPair[1]}
  def maxResults = results.findAll {it[0] * it[1] == maxPair[0] * maxPair[1]}

  return minResults + maxResults
}

assert isPalindrome(121)
assert !isPalindrome(123)
assert combinations(smallRange) == [[1, 1], [1, 2], [1, 3], [2, 2], [2, 3], [3, 3]]
assert minMaxPaliProducts(range1) == [[1, 1], [1, 9], [3, 3]]
assert minMaxPaliProducts(range2) == [[11, 11], [91, 99]]
