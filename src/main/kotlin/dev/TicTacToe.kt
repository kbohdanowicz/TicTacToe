package dev

import dev.CellType.*
import java.lang.IllegalArgumentException
import kotlin.text.StringBuilder

class TicTacToe(
        rowCount: Int,
        columnCount: Int,
        private val playerCount: Int,
        amountToWin: Int
) {
    private val maxBoardSize = 26
    private val board: Array<Array<CellType>>
    private val alphabet: Array<Char>
    private val lineChecker: LineChecker
    private val allowedMoves: Map<String, Pair<Int, Int>>

    private var currentPlayer = Player()
    private var isInputInvalid = false
    private var turnCount = 0

    init {
        if (rowCount >= maxBoardSize || columnCount >= maxBoardSize) {
            throw IllegalArgumentException("Max board size is $maxBoardSize")
        }

        board = Array(rowCount) { Array(columnCount) { EMPTY } }
        alphabet = Array(maxBoardSize) { (it + 97).toChar() }
        lineChecker = LineChecker(board, amountToWin)

        val tempList = mutableListOf<Pair<Int, Int>>()
        for (x in 0 until rowCount) {
            for (y in 0 until columnCount) {
                tempList.add(Pair(x, y))
            }
        }
        allowedMoves = tempList.map { "${alphabet[it.first]}${it.second + 1}" to it }.toMap() }

    private fun clearBoard() {
        for (row in board)
            for (i in row.indices)
                row[i] = EMPTY
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
        val nextPlayerNumber = (currentPlayer.number + 1) % playerCount
        currentPlayer = Player(nextPlayerNumber)
    }

    private fun handleUserInput() {
        val input = readLine() as String
        if (allowedMoves.containsKey(input)) {
            val (i, j) = allowedMoves.getValue(input)
            if (board[i][j].symbol == EMPTY.symbol) {
                board[i][j] = currentPlayer.cellType
            } else {
                isInputInvalid = true
            }
        } else {
            isInputInvalid = true
        }
    }

    private fun printBoard() {
        println("\n$this\n")
    }

    fun newGame() {
        clearBoard()
        while (true) {
            println("Player ${currentPlayer.number + 1} turn!")
            printBoard()
            handleUserInput()
            clearScreen()
            turnCount++
            if (isAnyLine(currentPlayer.cellType)) {
                println("Player ${currentPlayer.number + 1} wins in $turnCount turns!")
                printBoard()
                break
            }
            if (lineChecker.isNoSpaceLeft()) {
                println("Draw!")
                printBoard()
                break
            }
            if (isInputInvalid) {
                println("[ERROR]: Wrong input format!")
                isInputInvalid = false
                turnCount--
                continue
            }
            changePlayer()
        }
    }

    // do NOT extend this function anymore
    override fun toString(): String {
        val verticalMarkers = List(board.size) { alphabet[it].toUpperCase() }
        val horizontalMarkers = List(board[0].size) { it + 1 }
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
                    repeat(board[0].size) { append("---") }
                    repeat(board[0].size - 1) { append("-") }
                    append("\n")
                }
            }
        }
        return result.toString()
    }
}