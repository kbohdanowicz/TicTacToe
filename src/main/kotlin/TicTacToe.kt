import java.lang.StringBuilder

class TicTacToe {
    private val board: Array<Array<Cell>> = Array(3) {
        Array(3) { Cell() }
    }
    private val allowedMoves = mutableMapOf(
            "a1" to Pair(0, 0),
            "a2" to Pair(0, 1),
            "a3" to Pair(0, 2),
            "b1" to Pair(1, 0),
            "b2" to Pair(1, 1),
            "b3" to Pair(1, 2),
            "c1" to Pair(2, 0),
            "c2" to Pair(2, 1),
            "c3" to Pair(2, 2)
    )
    private val amountToWin = 3
    private var currentPlayer = 1
    private var currentCellType = CellType.CIRCLE
    private var isWrongInput = false
    private var turnCount = 0
    private val lineChecker = LineChecker(board, amountToWin)

    private fun clearBoard() {
        for (row in board)
            for (i in row.indices)
                row[i] = Cell()
    }

    private fun clearScreen() {
        for (i in 0..20)
            println("\n")
    }

    private fun isGameOver(currentCellType: CellType): Boolean {
        return lineChecker.run {
            isHorizontalLine(currentCellType)
                    || isVerticalLine(currentCellType)
                    || isDiagonalLine(currentCellType)
        }
    }

    private fun changePlayer() {
        if (currentPlayer == 1) {
            currentPlayer = 2
            currentCellType = CellType.CROSS
        } else {
            currentPlayer = 1
            currentCellType = CellType.CIRCLE
        }
    }

    private fun handleUserInput() {
        val input = readLine() as String
        if (allowedMoves.containsKey(input)) {
            val (i, j) = allowedMoves.getValue(input)
            if (board[i][j].type == CellType.NONE) {
                board[i][j] = Cell(currentCellType)
            } else {
                isWrongInput = true
            }
        } else {
            isWrongInput = true
        }
    }

    private fun printBoardWithMessage(message: String) {
        println(message)
        println("\n$this\n")
    }

    fun newGame() {
        clearBoard()
        while (true) {
            clearScreen()
            if (isGameOver(currentCellType)) {
                printBoardWithMessage("Player $currentPlayer wins in $turnCount turns!")
                break
            } else if (lineChecker.isNoSpaceLeft()) {
                printBoardWithMessage("Draw!")
                break
            }
            turnCount++
            if (isWrongInput) {
                println("[ERROR]: Wrong input format!")
                isWrongInput = false
                turnCount--
            } else if (turnCount > 1) {
                changePlayer()
            }
            printBoardWithMessage("Player $currentPlayer turn!")
            handleUserInput()
        }
    }

    override fun toString(): String {
        val result = StringBuilder()
        val verticalMarkers = listOf("A", "B", "C")
        val horizontalMarkers = listOf("1", "2", "3")
        val threeSpaces = "   "
        val twoSpaces = "  "

        result.apply {
            append(horizontalMarkers.joinToString(threeSpaces, threeSpaces))
            append("\n")
        }
        for ((i, row) in board.withIndex()) {
            result.apply {
                append(verticalMarkers[i])
                append(row.joinToString(" | ", twoSpaces))
            }
            if(i < board.lastIndex) {
                result.apply {
                    append("\n  ")
                    append("-----------")
                    append("\n")
                }
            }
        }

        return result.toString()
    }
}