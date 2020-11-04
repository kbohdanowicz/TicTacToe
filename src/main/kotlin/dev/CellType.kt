package dev

import java.lang.Exception

enum class CellType(private val symbol: String) {
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
    NONE(" ");

    override fun toString(): String = this.symbol

    companion object {
        fun getByIndex(number: Int): CellType =
                when (val cellType = values()[number]) {
                    NONE -> throw Exception("Player number stack overflow")
                    else -> cellType
                }
    }
}