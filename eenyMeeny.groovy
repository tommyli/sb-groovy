def eenyMeeny(n, k) {
  def children = new ArrayList(0..(n-1))
  def i = 0
  def currentk = k
  def sequence = []
  while (currentk > 0 && sequence.size() < children.size() - 1) {
    // println "i is $i, currentk is $currentk"
    if (currentk == 1) {
      sequence << "${children[i]}"
      children[i] = null
      currentk = k
    }
    else {
      currentk--
      i++
      if (i > (n - 1)) {
        i = i - n
      }
    }

    while (children[i] == null ) {
      i++
      if (i > (n - 1)) {
        i = i - n
      }
    }
  }

  ['sequence': sequence, 'winner': children[i]]
}

println eenyMeeny(10, 20)
