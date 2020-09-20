package dev

import java.lang.Exception

enum class CellType(val symbol: String) {
    FIRST("O"),
    SECOND("X"),
    THIRD("A"),
    FOURTH("B"),
    FIFTH("C"),
    SIXTH("D"),
    SEVENTH("E"),
    EIGHTH("F"),
    NINTH("G"),
    TENTH("H"),
    EMPTY(" "),
    UNDEFINED("UNDEFINED");
    companion object {
        fun getByIndex(number: Int): CellType {
            for (cellType in values()) {
                if (cellType == EMPTY || cellType == UNDEFINED)
                    throw Exception("Player number stack overflow")
                if (number == cellType.ordinal)
                    return cellType
            }
            throw Exception("Invalid player number")
        }
    }
}