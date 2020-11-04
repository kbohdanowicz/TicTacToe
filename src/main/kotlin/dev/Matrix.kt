package dev

typealias Matrix<T> = Array<Array<T>>

@Suppress("FunctionName")
inline fun <reified T> Matrix(rows: Int, cols: Int, noinline init: (Int) -> T): Matrix<T> =
        Array(rows) { Array(cols, init) }

@Suppress("FunctionName")
inline fun <reified T> Matrix(size: Pair<Int, Int>, noinline init: (Int) -> T): Matrix<T> =
        Array(size.first) { Array(size.second, init) }

infix fun Int.by(other: Int): Pair<Int, Int> = this to other

val <T> Matrix<T>.itemCount: Int
    get() = size * this.first().size

val <T> Matrix<T>.dimensions: Pair<Int, Int>
    get() = size to this.first().size

val <T> Matrix<T>.shorterEdge: Int
    get() = if (size < this.first().size) size else this.first().size

val <T> Matrix<T>.rowCount: Int
    get() = size

val <T> Matrix<T>.columnCount: Int
    get() = this.first().size

fun <T> Matrix<T>.toStr(): String {
    return StringBuilder().apply {
        append("[")
        this@toStr.forEach { row ->
            if (this@toStr.indexOf(row) != 0) append(" ")
            append(row.joinToString(", ", "[", "]"))
            if (this@toStr.indexOf(row) != this@toStr.lastIndex) append("\n")
        }
        append("]")
    }.toString()
}

inline fun <reified T> Matrix<T>.transposed(): Matrix<T> {
    return Array(this.first().size) { Array(size) { this.first().first() } }
        .also {
            forEachIndexed { x, row ->
                row.forEachIndexed { y, _ ->
                    it[y][x] = this[x][y]
                }
            }
        }
}
