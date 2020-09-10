class LineChecker(
        private val array2d: Array<Array<Cell>>,
        private val amountToWin: Int
) {
    fun isHorizontalLine(cellType: CellType): Boolean {
        var sum: Int
        for (row in array2d) {
            sum = 0
            for (cell in row) {
                if (cell.type == cellType) {
                    sum++
                    return if (sum == amountToWin) true else continue
                }
            }
        }
        return false
    }

    fun isVerticalLine(cellType: CellType): Boolean {
        var sum: Int
        for (index in array2d.indices) {
            sum = 0
            for (row in array2d) {
                if (row[index].type == cellType) {
                    sum++
                    return if (sum == amountToWin) true else continue
                }
            }
        }
        return false
    }

    fun isDiagonalLine(cellType: CellType): Boolean {
        var sum = 0
        for ((i, row) in array2d.withIndex()) {
            if (row[i].type == cellType) {
                sum++
                return if (sum == amountToWin) true else continue
            }
        }

        sum = 0
        for ((i, row) in array2d.withIndex()) {
            if (row[(array2d.lastIndex) - i].type == cellType) {
                sum++
                return if (sum == amountToWin) true else continue
            }
        }
        return false
    }
}