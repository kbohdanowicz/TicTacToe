package dev

enum class BoardSize {
    REGULAR {
        override fun value() = 3
    },
    EXTENDED {
        override fun value() = 4
    };

    abstract fun value(): Int
}