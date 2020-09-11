package dev

import java.lang.StringBuilder
import dev.BoardSize.*
import dev.Cell.*

class TicTacToe(private val boardSize: BoardSize = REGULAR) {
    private val board: Array<Array<Cell>> = Array(boardSize.value()) {
        Array(boardSize.value()) { NONE }
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
    private val amountToWin = board.size
    private var currentPlayer = 1
    private var currentCellType = CIRCLE
    private var isWrongInput = false
    private var turnCount = 0
    private val lineChecker = LineChecker(board, amountToWin)

    private fun clearBoard() {
        for (row in board)
            for (i in row.indices)
                row[i] = NONE
    }

    private fun clearScreen() {
        for (i in 0..20)
            println("\n")
    }

    private fun isGameOver(currentCellType: Cell): Boolean {
        return lineChecker.run {
            isHorizontalLine(currentCellType)
                    || isVerticalLine(currentCellType)
                    || isDiagonalLine(currentCellType)
        }
    }

    private fun changePlayer() {
        if (currentPlayer == 1) {
            currentPlayer = 2
            currentCellType = Cell.CROSS
        } else {
            currentPlayer = 1
            currentCellType = Cell.CIRCLE
        }
    }

    private fun handleUserInput() {
        val input = readLine() as String
        if (allowedMoves.containsKey(input)) {
            val (i, j) = allowedMoves.getValue(input)
            if (board[i][j].toString() == NONE.toString()) {
                board[i][j] = currentCellType
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
        val verticalMarkers = List(board.size) { (it + 65).toChar() }
        val horizontalMarkers = List(board.size) { it + 1 }
        val threeSpaces = "   "
        val twoSpaces = "  "
        val result = StringBuilder()

        with(result) {
            append(horizontalMarkers.joinToString(threeSpaces, threeSpaces))
            append("\n")
        }
        for ((i, row) in board.withIndex()) {
            with(result) {
                append(verticalMarkers[i])
                append(row.joinToString(" | ", twoSpaces))
            }
            if (i < board.lastIndex) {
                with(result) {
                    append("\n  ")
                    repeat(board.size) { append("---") }
                    repeat(board.size - 1) { append("-") }
                    append("\n")
                }
            }
        }
        return result.toString()
    }
}