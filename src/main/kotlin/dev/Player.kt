package dev

data class Player(val number: Int = 0) {
    val cellType = CellType.getByIndex(number)
}