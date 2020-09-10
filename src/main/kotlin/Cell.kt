data class Cell(val type: CellType = CellType.NONE) {
    override fun toString(): String {
        return when(type) {
            CellType.CIRCLE -> "O"
            CellType.CROSS -> "X"
            else -> " "
        }
    }
}