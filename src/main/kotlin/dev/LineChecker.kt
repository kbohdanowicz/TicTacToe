package dev

import dev.CellType.*
import kotlin.math.abs

class LineChecker(
        private val board: Array<Array<CellType>>,
        private val amountToWin: Int
) {
    private val matrixSize = board.size * board[0].size

    fun isHorizontalLine(cellType: CellType): Boolean {
        var sum: Int
        for (row in board) {
            sum = 0
            for (cell in row) {
                if (cell.symbol == cellType.symbol) {
                    sum++
                    return if (sum == amountToWin) true else continue
                }
            }
        }
        return false
    }

    fun isVerticalLine(cellType: CellType): Boolean {
        var sum: Int
        for (index in board.indices) {
            sum = 0
            for (row in board) {
                if (row[index].symbol == cellType.symbol) {
                    sum++
                    return if (sum == amountToWin) true else continue
                }
            }
        }
        return false
    }

    private fun getBoardCoordinates(): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        var i = (board.size - 2) * (-1)
        var pair: Pair<Int, Int>
        while (i < board.size - 1) {
            pair = when {
                i < 0 -> Pair(abs(i), 0)
                i > 0 -> Pair(0, i)
                else -> Pair(0, 0)
            }
            result.add(pair)
            i++
        }
        return result.toList()
    }

    fun isDiagonalLine(cellType: CellType): Boolean {
        val coords = getBoardCoordinates()
        var tempArray: Array<Array<CellType>>
        var lastX: Int
        var lineLength: Int
        var sum: Int
        for (k in 0..1) {
            tempArray = if (k == 0) board else board.reversed().toTypedArray()
            lastX = coords[0].component1()
            lineLength = board.size - 1
            for (pair in coords) {
                sum = 0
                val (x, y) = pair
                if (x < lastX) {
                    lineLength++
                    lastX = x
                } else {
                    lineLength--
                }
                for (j in 0..lineLength) {
                    if (tempArray[x + j][y + j] == cellType)
                        sum++
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