class Queen {
  int num, x, y = 0

  boolean isMoveable(int posx, int posy) {
    def result = false
    if (posx < 0 || posy < 0 || posx >= Board.BOARD_SIZE || posy >= Board.BOARD_SIZE) {
      return false
    }
    else if (posx == x || posy == y) {
      return true
    }
    else if (Math.abs(posx - x) == Math.abs(posy - y)) {
      return true
    }
    return result
  }

  String toString() {
    "[num: $num, x: $x, y: $y]"
  }
}

class Board {
  static int BOARD_SIZE = 8

  def board = [[]]
  def queens = []
  def lastGoodQueens = []

  Board() {
    init()
  }

  def init() {
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        if (board[x] == null) {
          board[x] = []
        }
        board[x][y] = false
      }
    }
  }

  boolean placeQueen(Queen q) {
    boolean added = false
    int currentRow = -1
    Queen queenToAdd = q
    if (queens.isEmpty()) {
      addQueen(q)
      added = true
    }
    while(!added && currentRow != q.num) {
      currentRow = queenToAdd.num
      println "queenToAdd: $queenToAdd, currentRow: $currentRow"
      for (int x = queenToAdd.x; x < 8; x++) {
        if (!board[x][queenToAdd.num]) {
          queenToAdd.x = x
          queenToAdd.y = queenToAdd.num
          addQueen(queenToAdd)
          added = true
          println "added: $queenToAdd, currentRow: $currentRow, queens: $queens, this:\n$this"
        }
      }
      if (added && currentRow != q.num) {
        //added = false
        //currentRow++
        //queenToAdd = (currentRow + 1) == q.num ? q : queens[currentRow+1]
        queenToAdd = new Queen(num: currentRow + 1, x: 0, y: currentRow + 1)
        currentRow = -1
        //queenToAdd = currentRow == q.num ? q : queens[currentRow]
        queenToAdd.x = 0
        println "queenToAdd2: $queenToAdd, currentRow: $currentRow"
        added = false
        continue
      }
//      if (added && currentRow == q.num) {
//        //currentRow++
//        queenToAdd = q
//        queenToAdd.x = 0
//      }
      if (!added) {
        println "failed to add"
        queenToAdd = null
        while (queenToAdd == null) {
          currentRow--
          queenToAdd = new Queen(num: queens[currentRow].num, x: queens[currentRow].x + 1, y: queens[currentRow].y)
          println "about to add: $queenToAdd"
          popQueen()
          if (queenToAdd.x > 7) {
            println "can't add coz x is outside border"
            queenToAdd = null
          }
        }
        println "queenToAdd3: $queenToAdd, currentRow: $currentRow"
      }
    }
    
    return added
  }

  def addQueen(Queen q) {
    queens.add(q)
    lastGoodQueens.add(q)
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        if (q.isMoveable(x, y)) {
          board[x][y] = true
        }
      }
    }
  }

  def popQueen() {
    queens.pop()
    init()
    def oldQueens = []
    oldQueens.addAll(queens)
    queens.clear()
    for (int i = 0; i < oldQueens.size(); i++) {
      addQueen(oldQueens[i])
    }
    println "after popping: $this, queens: $queens"
  }

  String toString() {
    def res = ''
    for (int x = 7; x >= 0; x--) {
      for (int y = 0; y < 8; y++) {
        res += "${board[y][x]} "
      }
      res += '\n'
    }
    res
  }
}

def board = new Board()
boolean result = true
(0..7).each {Integer num ->
  Queen q = new Queen(num: num, x: 0, y: num)
  result = result && board.placeQueen(q)
}
//Queen qa = new Queen(num: 0, x: 0, y: 0)
//Queen qb = new Queen(num: 1, x: 2, y: 1)
//Queen qc = new Queen(num: 2, x: 6, y: 2)
//board.addQueen(qa)
//board.addQueen(qb)
//board.addQueen(qc)
println board
println board.queens.join(',\n')

Queen q1 = new Queen(num: 1, x: 0, y: 0)
assert q1.isMoveable(0, 0)
assert q1.isMoveable(0, 1)
assert q1.isMoveable(1, 0)
assert q1.isMoveable(1, 1)
assert q1.isMoveable(2, 2)
assert !q1.isMoveable(1, 2)
assert !q1.isMoveable(2, 1)
assert !q1.isMoveable(-1, 1)
assert !q1.isMoveable(3, 9)
q1.x = 3
q1.y = 4
assert q1.isMoveable(4, 5)
assert q1.isMoveable(2, 3)
