package dev

enum class Cell {
    CIRCLE {
        override fun toString(): String = "O"
    },
    CROSS {
        override fun toString(): String = "X"
    },
    NONE {
        override fun toString(): String = " "
    };

    abstract override fun toString(): String
}