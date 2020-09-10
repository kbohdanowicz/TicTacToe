import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LineCheckerTest {
    @Test
    fun `is horizontal line test`() {
        val arr: Array<Array<Cell>> = arrayOf(
                arrayOf(Cell(), Cell(), Cell()),
                arrayOf(Cell(), Cell(), Cell()),
                arrayOf(Cell(CellType.CROSS), Cell(CellType.CROSS), Cell(CellType.CROSS))
        )
        val lc = LineChecker(arr, 3)

        assertEquals(true, lc.isHorizontalLine(CellType.CROSS))
    }


    @Test
    fun `is vertical line test`() {
        val arr: Array<Array<Cell>> = arrayOf(
                arrayOf(Cell(CellType.CROSS), Cell(), Cell()),
                arrayOf(Cell(CellType.CROSS), Cell(), Cell()),
                arrayOf(Cell(CellType.CROSS), Cell(), Cell())
        )
        val lc = LineChecker(arr, 3)

        assertEquals(true, lc.isVerticalLine(CellType.CROSS))
    }

    @Test
    fun `is diagonal line test`() {
        val arr: Array<Array<Cell>> = arrayOf(
                arrayOf(Cell(), Cell(), Cell(CellType.CROSS)),
                arrayOf(Cell(), Cell(CellType.CROSS), Cell()),
                arrayOf(Cell(CellType.CROSS), Cell(), Cell())
        )
        val lc = LineChecker(arr, 3)

        assertEquals(true, lc.isDiagonalLine(CellType.CROSS))
    }
}