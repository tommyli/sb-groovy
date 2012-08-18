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

def eenyMeenyRecursive(n, k) {
  if (n == 1) {
    return ['sequence': [], 'winner': n]
  }
  else {
    eenyMeenyHelper(new ArrayList(0..(n-1)), k, k, [])
  }
}

def eenyMeenyHelper(List players, Integer currk, Integer k, List sequence) {
  if (players.size() == 1) {
    return ['sequence': sequence, 'winner': players.head()]
  }
  else {
    if (currk == 1) {
      eenyMeenyHelper(players.tail(), k, k, (sequence << players.head()))
    }
    else {

      eenyMeenyHelper(players.tail() << players.head(), currk - 1, k, sequence)
    }
  }
}

println eenyMeeny(10, 20)
println eenyMeenyRecursive(10, 20)
