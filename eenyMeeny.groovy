class EenyMeeny {

  static void main(args) {
    EenyMeeny em = new EenyMeeny()
    println em.eenyMeeny(5, 3)
    println em.eenyMeenyRecursiveTramp(3000, 3)
    // println em.eenyMeenyRecursive(3000, 3)  // throws StackOverflowError
  }

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
      eenyMeenyHelper(new ArrayList(0..(n-1)), 0, k, [])
    }
  }

  def eenyMeenyRecursiveTramp(n, k) {
    if (n == 1) {
      return ['sequence': [], 'winner': n]
    }
    else {
      eenyMeenyHelperTramp(new ArrayList(0..(n-1)), 0, k, [])
    }
  }

  private eenyMeenyHelper(List players, Integer currPos, Integer k, List sequence) {
    if (players.size() == 1) {
      return ['sequence': sequence, 'winner': players.head()]
    }
    else {
      def indexToRemove = (currPos + k - 1) % players.size()
      def removedPlayer = players.remove(indexToRemove)
      eenyMeenyHelper(players, indexToRemove, k, sequence << removedPlayer)
    }
  }

  private def eenyMeenyHelperTramp = {List players, Integer currPos, Integer k, List sequence ->
    if (players.size() == 1) {
      return ['sequence': sequence, 'winner': players.head()]
    }
    else {
      def indexToRemove = (currPos + k - 1) % players.size()
      def removedPlayer = players.remove(indexToRemove)
      eenyMeenyHelperTramp.trampoline(players, indexToRemove, k, sequence << removedPlayer)
    }
  }.trampoline()
}
