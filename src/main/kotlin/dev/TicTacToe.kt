package dev

import dev.CellType.*
import kotlin.text.StringBuilder

class TicTacToe(
        private val boardSizeXY: Int,
        private var playerCount: Int,
        amountToWin: Int,
) {
    private val board: Array<Array<CellType>> = Array(boardSizeXY) {
        Array(boardSizeXY) { EMPTY }
    }
    private val allowedMoves: Map<String, Pair<Int, Int>>
    private val lineChecker = LineChecker(board, amountToWin)

    private var currentPlayer = Player()
    private var isWrongInput = false
    private var turnCount = 0

    init {
        // initialize allowedMoves property
        val tempList = mutableListOf<Pair<Int, Int>>()
        for (x in 0 until boardSizeXY) {
            for (y in 0 until boardSizeXY) {
                tempList.add(Pair(x, y))
            }
        }
        allowedMoves = tempList.map { "${getAsciiAlphabetByIndex(it.first, false)}${it.second + 1}" to it }.toMap()
    }

    private fun getAsciiAlphabetByIndex(number: Int, upperCase: Boolean = true): Char {
        val asciiAlphabetStartNumber = 65
        return if (upperCase) {
            (number + asciiAlphabetStartNumber).toChar()
        } else {
            (number + asciiAlphabetStartNumber).toChar().toLowerCase()
        }
    }

    private fun clearBoard() {
        for (row in board)
            for (i in row.indices)
                row[i] = EMPTY
    }

    private fun clearScreen() {
        for (i in 0..20)
            println("\n")
    }

    private fun isThereAnyLine(cellType: CellType): Boolean {
        return lineChecker.run {
            isHorizontalLine(cellType)
                    || isVerticalLine(cellType)
                    || isDiagonalLine(cellType)
        }
    }

    private fun changePlayer() {
        val nextPlayerNumber = currentPlayer.number + 1
        currentPlayer = if (nextPlayerNumber >= playerCount) Player(0) else Player(nextPlayerNumber)
    }

    private fun handleUserInput() {
        val input = readLine() as String
        if (allowedMoves.containsKey(input)) {
            val (i, j) = allowedMoves.getValue(input)
            if (board[i][j].symbol == EMPTY.symbol) {
                board[i][j] = currentPlayer.cellType
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
            if (isThereAnyLine(currentPlayer.cellType)) {
                printBoardWithMessage("Player ${currentPlayer.number + 1} wins in $turnCount turns!")
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
            printBoardWithMessage("Player ${currentPlayer.number + 1} turn!")
            handleUserInput()
        }
    }

    // do NOT extend this function anymore
    override fun toString(): String {
        val verticalMarkers = List(board.size) { getAsciiAlphabetByIndex(it) } // A, B, C...
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
                append(row.joinToString(" | ", twoSpaces) { it.symbol })
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