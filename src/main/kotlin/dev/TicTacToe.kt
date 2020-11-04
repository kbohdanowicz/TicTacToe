package dev

import dev.CellType.*
import java.lang.IllegalArgumentException
import kotlin.text.StringBuilder

class TicTacToe(
        rowCount: Int,
        colCount: Int,
        private val playerCount: Int,
        amountToWin: Int
) {
    private val maxBoardSize = 26
    private val board: Matrix<CellType>
    private val alphabet: Array<Char>
    private val lineChecker: LineChecker
    private val allowedMoves: Map<String, Coordinates>

    private var currentPlayer = Player()
    private var isInputValid = true
    private var turnCount = 0

    init {
        if (rowCount >= maxBoardSize || colCount >= maxBoardSize)
            throw IllegalArgumentException("Max board size is $maxBoardSize")

        board = Matrix(rowCount, colCount) { NONE }
        alphabet = Array(maxBoardSize) { (it + 97).toChar() }
        lineChecker = LineChecker(board, amountToWin)

        val flatCoordinates = board.flatMap { row ->
            row.indices.map { board.indexOf(row) to it }
        }
        // associate key to Pair(x, y)
        allowedMoves = flatCoordinates.associateBy { "${alphabet[it.first]}${it.second + 1}" }
    }

    private fun clearBoard() {
        board.forEach { row ->
            row.indices.forEach { row[it] = NONE }
        }
    }

    private fun clearScreen() {
        for (i in 0..20)
            println("\n")
    }

    private fun isAnyLine(cellType: CellType): Boolean {
        return lineChecker.run {
            isHorizontalLine(cellType)
                    || isVerticalLine(cellType)
                    || isDiagonalLine(cellType)
        }
    }

    private fun changePlayer() {
        currentPlayer = Player((currentPlayer.number + 1) % playerCount)
    }

    private fun handleUserInput() {
        val input = readLine() as String
        if (allowedMoves.containsKey(input)) {
            val (i, j) = allowedMoves.getValue(input)
            if (board[i][j] == NONE) {
                board[i][j] = currentPlayer.cellType
            } else {
                isInputValid = false
            }
        } else {
            isInputValid = false
        }
    }

    private fun printBoard() {
        println("\n$this\n")
    }

    fun newGame() {
        clearBoard()
        do {
            println("Player ${currentPlayer.number + 1} turn!")
            printBoard()
            handleUserInput()
            clearScreen()
            when {
                isAnyLine(currentPlayer.cellType) -> {
                    println("Player ${currentPlayer.number + 1} wins in $turnCount turns!")
                    printBoard()
                    break
                }
                lineChecker.isNoSpaceLeft() -> {
                    println("Draw!")
                    printBoard()
                    break
                }
                !isInputValid -> {
                    println("[ERROR]: Wrong input format!")
                    isInputValid = true
                    continue
                }
            }
            turnCount++
            changePlayer()
        } while (true)
    }

    // do NOT extend this function anymore
    override fun toString(): String {
        val verticalMarkers = List(board.size) { alphabet[it].toUpperCase() }
        val horizontalMarkers = List(board.first().size) { it + 1 }
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
                append(row.joinToString(" | ", twoSpaces) { it.toString() })
            }
            if (i < board.lastIndex) {
                with(result) {
                    append("\n  ")
                    repeat(board.first().size) { append("---") }
                    repeat(board.first().size - 1) { append("-") }
                    append("\n")
                }
            }
        }
        return result.toString()
    }
}