package dev

import dev.CellType.*

class LineChecker(
        private val board: Array<Array<CellType>>,
        private val amountToWin: Int
) {
    private val arraySize = board.size * board[0].size

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

    fun isDiagonalLine(cellType: CellType): Boolean {
        var tempArray: Iterable<IndexedValue<Array<CellType>>>
        var sum: Int
        for (j in 0..1) {
            tempArray = if (j == 0) board.withIndex() else board.reversed().withIndex()
            sum = 0
            for ((index, row) in tempArray) {
                if (row[index].symbol == cellType.symbol) {
                    sum++
                    return if (sum == amountToWin) true else continue
                }
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
                    return if (sum == arraySize) true else continue
                }
            }
        }
        return false
    }
}