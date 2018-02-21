// Pythagorean Triplet in Java
// There exists exactly one Pythagorean triplet for which a + b + c = 1000. Find the product a * b * c.

def isPythagorean(a, b, c) {
  a**2 + b**2 == c**2
}

def isTripletSum1000(a, b, c) {
  a+b+c == 1000
}

def findPythagoreanTriplet() {
  def a = new ArrayList((1..1000))
  def b = new ArrayList((1..1000))
  def c = new ArrayList((1..1000))

  def doublet = ([a, b].combinations()).findAll {it[0] < it[1] && it[0] + it[1] < 999}

  def triplet = []
  doublet.each {pair ->
    def match = c.find {cnum -> cnum > pair[1] && isTripletSum1000(pair[0], pair[1], cnum) && isPythagorean(pair[0], pair[1], cnum)}
    if (match) {
      triplet = pair + [match]
    }
  }

  return triplet
}

// Naive, brute force implementation, too slow
def findPythagoreanTripletNaive() {
  def a = new ArrayList((1..1000))
  def b = new ArrayList((1..1000))
  def c = new ArrayList((1..1000))

  [a, b, c].combinations()
    .find {isPythagorean(it[0], it[1], it[2]) && isTripletSum1000(it[0], it[1], it[2])}
}

assert isPythagorean(3, 4, 5)
assert isTripletSum1000(333, 333, 334)
assert findPythagoreanTriplet().sum() == 1000
