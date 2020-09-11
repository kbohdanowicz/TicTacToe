package dev

import dev.Cell.*

class LineChecker(
        private val array2d: Array<Array<Cell>>,
        private val amountToWin: Int
) {
    private val arraySize = array2d.size * array2d[0].size

    fun isHorizontalLine(cellType: Cell): Boolean {
        var sum: Int
        for (row in array2d) {
            sum = 0
            for (cell in row) {
                if (cell.toString() == cellType.toString()) {
                    sum++
                    return if (sum == amountToWin) true else continue
                }
            }
        }
        return false
    }

    fun isVerticalLine(cellType: Cell): Boolean {
        var sum: Int
        for (index in array2d.indices) {
            sum = 0
            for (row in array2d) {
                if (row[index].toString() == cellType.toString()) {
                    sum++
                    return if (sum == amountToWin) true else continue
                }
            }
        }
        return false
    }

    fun isDiagonalLine(cellType: Cell): Boolean {
        var tempArray: Iterable<IndexedValue<Array<Cell>>>
        var sum: Int
        for (j in 0..1) {
            tempArray = if (j == 0) array2d.withIndex() else array2d.reversed().withIndex()
            sum = 0
            for ((index, row) in tempArray) {
                if (row[index].toString() == cellType.toString()) {
                    sum++
                    return if (sum == amountToWin) true else continue
                }
            }
        }
        return false
    }

    fun isNoSpaceLeft(): Boolean {
        var sum = 0
        for (row in array2d) {
            for (cell in row) {
                if (cell.toString() != NONE.toString()) {
                    sum++
                    return if (sum == arraySize) true else continue
                }
            }
        }
        return false
    }
}