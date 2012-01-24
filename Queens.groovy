class Queen {
  int x, y = 0

  boolean isMoveable(int posx, int posy) {
    boolean result = false
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
    "[x: $x, y: $y]"
  }
}

class Board {
  static int BOARD_SIZE = 8

  def board = [[]]
  def queens = []

  Board() {
    init()
  }

  void init() {
    for (int x = 0; x < BOARD_SIZE; x++) {
      for (int y = 0; y < BOARD_SIZE; y++) {
        if (board[x] == null) {
          board[x] = []
        }
        board[x][y] = false
      }
    }
  }

  void placeQueen(Queen q) {
    boolean added = false
    int currentRow = -1
    Queen queenToAdd = q
    if (queens.isEmpty()) {
      addQueen(q)
      added = true
    }
    while(!added && currentRow != q.y) {
      currentRow = queenToAdd.y
      println "queenToAdd: $queenToAdd, currentRow: $currentRow"
      for (int x = queenToAdd.x; x < BOARD_SIZE; x++) {
        if (!board[x][queenToAdd.y]) {
          queenToAdd.x = x
          addQueen(queenToAdd)
          added = true
          break
          //println "added: $queenToAdd, currentRow: $currentRow, queens: $queens, this:\n$this"
        }
      }
      if (added && currentRow != q.y) {
        queenToAdd = new Queen(x: 0, y: currentRow + 1)
        currentRow = -1
        queenToAdd.x = 0
        println "queenToAdd2: $queenToAdd, currentRow: $currentRow"
        added = false
        continue
      }
      if (!added) {
        println "failed to add"
        queenToAdd = null
        while (queenToAdd == null) {
          currentRow--
          queenToAdd = new Queen(x: queens[currentRow].x + 1, y: queens[currentRow].y)
          println "about to add: $queenToAdd"
          resetToPrevQueen()
          if (queenToAdd.x >= BOARD_SIZE) {
            println "can't add coz x is outside border"
            queenToAdd = null
          }
        }
        println "queenToAdd3: $queenToAdd, currentRow: $currentRow"
      }
    }
  }

  def addQueen(Queen q) {
    queens.add(q)
    for (int x = 0; x < BOARD_SIZE; x++) {
      for (int y = 0; y < BOARD_SIZE; y++) {
        if (q.isMoveable(x, y)) {
          board[x][y] = true
        }
      }
    }
  }

  def resetToPrevQueen() {
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
    for (int x = BOARD_SIZE - 1; x >= 0; x--) {
      for (int y = 0; y < BOARD_SIZE; y++) {
        res = "$res${board[y][x]} "
      }
      res = "$res\n"
    }
    res
  }
}

def test() {
  Queen q1 = new Queen(x: 0, y: 0)
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
}
test()

def board = new Board()
(0..Board.BOARD_SIZE - 1).each {Integer num ->
  Queen q = new Queen(x: 0, y: num)
  board.placeQueen(q)
}
println board.queens.join('\n')
