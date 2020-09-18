package dev

import dev.CellType.*
import kotlin.text.StringBuilder

class TicTacToe(
        private val boardSize: Int,
        private var playerCount: Int//,
        // TODO private val amountToWin: Int
) {
    private val board: Array<Array<CellType>> = Array(boardSize) {
        Array(boardSize) { EMPTY }
    }
    private val allowedMoves: Map<String, Pair<Int, Int>>
    private val amountToWin = board.size
    private val lineChecker = LineChecker(board, amountToWin)

    private var currentPlayer = Player()
    private var isWrongInput = false
    private var turnCount = 0

    init {
        // initialize allowedMoves property
        val tempList = mutableListOf<Pair<Int, Int>>()
        for (x in 0 until boardSize) {
            for (y in 0 until boardSize) {
                tempList.add(Pair(x, y))
            }
        }
        allowedMoves = tempList.map { "${getAsciiAlphabetLetter(it.first)}${it.second + 1}" to it }.toMap()
    }

    private fun getAsciiAlphabetLetter(number: Int, upperCase: Boolean = true): Char {
        return if (upperCase) (number + 65).toChar() else (number + 65).toChar().toLowerCase()
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

    // do NOT expand this function anymore
    override fun toString(): String {
        val verticalMarkers = List(board.size) { getAsciiAlphabetLetter(it) } // A, B, C...
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