package dev

import dev.CellType.*
import kotlin.math.abs

class LineChecker(
        private val board: Array<Array<CellType>>,
        private val amountToWin: Int
) {
    private val matrixSize = board.size * board[0].size

    fun isHorizontalLine(cellType: CellType): Boolean {
        for (row in board) {
            var sum = 0
            var lastCellType = cellType
            for (cell in row) {
                if (cell == cellType && lastCellType == cellType) {
                    lastCellType = cellType
                    sum++
                    return if (sum == amountToWin) true else continue
                } else {
                    lastCellType = cell
                }
            }
        }
        return false
    }

    fun isVerticalLine(cellType: CellType): Boolean {
        for (i in board[0].indices) {
            var sum = 0
            var lastCellType = cellType
            for (row in board) {
                if (row[i] == cellType && lastCellType == cellType) {
                    lastCellType = cellType
                    sum++
                    return if (sum == amountToWin) true else continue
                } else {
                    lastCellType = row[i]
                }
            }
        }
        return false
    }

    private fun Array<Array<CellType>>.getCoordinates(): List<Pair<Int, Int>> {
        val coords = mutableListOf<Pair<Int, Int>>()
        var i = (this.size - 2) * (-1)
        var pair: Pair<Int, Int>
        while (i < this.size - 1) {
            pair = when {
                i < 0 -> Pair(abs(i), 0)
                i > 0 -> Pair(0, i)
                else -> Pair(0, 0)
            }
            coords.add(pair)
            i++
        }
        return coords.toList()
    }

    private fun Array<Array<CellType>>.transposed(): Array<Array<CellType>> {
        val transposedBoard = Array(this[0].size) {
            Array(this.size) { UNDEFINED }
        }
        for (colIndex in this[0].indices) {
            for ((rowIndex, row) in this.withIndex()) {
                transposedBoard[colIndex][rowIndex] = row[colIndex]
            }
        }
        for (row in transposedBoard) {
            if (row.contains(UNDEFINED))
                throw Exception("Matrix transposing failed")
        }
        return transposedBoard
    }

    private val Array<Array<CellType>>.shorterEdge: Int
        get() = if (this.size < this[0].size) this.size else this[0].size

    fun isDiagonalLine(cellType: CellType): Boolean {
        for (k in 0..1) {
            val tempBoard = if (k == 0) board else board.transposed()
            val maxLineLength = tempBoard.shorterEdge
            val coords = tempBoard.getCoordinates()
            var lastX = coords[0].first
            var lastY = coords[0].second
            var lastCellType = cellType
            var lineLength = 2
            for (pair in coords) {
                var sum = 0
                val (x, y) = pair
                if (x < lastX) {
                    if (lineLength < maxLineLength) {
                        lineLength++
                    }
                    lastX = x
                } else if (y > lastY){
                    lineLength--
                    lastY = y
                }
                for (j in 0 until lineLength) {
                    if (lastCellType != cellType)
                        sum = 0
                    lastCellType = if (tempBoard[x + j][y + j] == cellType) {
                        sum++
                        cellType
                    } else {
                        tempBoard[x + j][y + j]
                    }
                }
                if (sum >= amountToWin)
                    return true
            }
        }
        return false
    }

    fun isNoSpaceLeft(): Boolean {
        var sum = 0
        for (row in board) {
            for (cell in row) {
                if (cell.symbol != EMPTY.symbol) {
                    sum++
                    return if (sum == matrixSize) true else continue
                }
            }
        }
        return false
    }
}