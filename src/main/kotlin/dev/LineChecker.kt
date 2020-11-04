package dev

import dev.CellType.*
import kotlin.math.abs

class LineChecker(
        private val board: Matrix<CellType>,
        private val amountToWin: Int
) {
    fun isHorizontalLine(cellType: CellType, arr: Matrix<CellType> = board): Boolean {
        arr.forEach { row ->
            val isLongEnough =
                    row.joinToString("") { it.toString() }
                            .contains(cellType.toString().repeat(amountToWin))
            if (isLongEnough)
                return true
        }
        return false
    }

    fun isVerticalLine(cellType: CellType): Boolean = isHorizontalLine(cellType, board.transposed())

    private fun Matrix<CellType>.getCoordinates(): List<Pair<Int, Int>> {
        val minusXtoX = ((size - 2) * (-1))..(size - 2)
        return minusXtoX.map {
            when {
                it < 0 -> abs(it) to 0
                it > 0 -> 0 to it
                else -> 0 to 0
            }
        }
    }

    fun isDiagonalLine(cellType: CellType): Boolean {
        for (k in 0..1) {
            val tBoard = if (k == 0) board else board.reversed().toTypedArray()
            val tempBoard = if (tBoard.rowCount < tBoard.columnCount) tBoard.transposed() else tBoard

            println(tempBoard.toStr())

            val maxLineLength = tempBoard.shorterEdge
            val coords = tempBoard.getCoordinates()

            var lastX = coords.first().first
            var lastY = coords.first().second
            var lineLength = 2

            // create a string of
            coords.forEachIndexed() { i, (x, y) ->
                // calculate line length
                if (x < lastX) {
                    if (lineLength < maxLineLength) {
                        lineLength++
                    }
                    lastX = x
                } else if (y > lastY) {
                    lineLength--
                    lastY = y
                }
                val diagonalLine = (0 until lineLength).map { tempBoard[x + it][y + it] }
                if (diagonalLine.joinToString("") { it.toString() }
                                .contains(cellType.toString().repeat(amountToWin))) {
                    return true
                }
                // if (lastCell != cellType) {
                //     sum = 0
                // }
                // lastCell = if (tempBoard[x + i][y + i] == cellType) {
                //     sum++
                //     cellType
                // } else {
                //     tempBoard[x + i][y + i]
                // }
                //
                // if (sum >= amountToWin)
                //     return true
            }
        }
        return false
    }

    fun isNoSpaceLeft(): Boolean {
        val sum = board.sumBy { row ->
            row.filter { it != NONE }.sumBy { 1 }
        }
        return sum == board.itemCount
    }
}