import dev.Cell
import dev.LineChecker
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

import dev.Cell.*

class LineCheckerTest {
    @Test
    fun `is horizontal line test`() {
        val arr: Array<Array<Cell>> = arrayOf(
                arrayOf(NONE, NONE, NONE),
                arrayOf(NONE, NONE, NONE),
                arrayOf(CROSS, CROSS, CROSS)
        )
        val lc = LineChecker(arr, 3)

        assertEquals(true, lc.isHorizontalLine(CROSS))
    }


    @Test
    fun `is vertical line test`() {
        val arr: Array<Array<Cell>> = arrayOf(
                arrayOf(CROSS, NONE, NONE),
                arrayOf(CROSS, NONE, NONE),
                arrayOf(CROSS, NONE, NONE)
        )
        val lc = LineChecker(arr, 3)

        assertEquals(true, lc.isVerticalLine(CROSS))
    }

    @Test
    fun `is diagonal line test`() {
        val arr: Array<Array<Cell>> = arrayOf(
                arrayOf(NONE, NONE, CROSS),
                arrayOf(NONE, CROSS, NONE),
                arrayOf(CROSS, NONE, NONE)
        )
        val lc = LineChecker(arr, 3)

        assertEquals(true, lc.isDiagonalLine(CROSS))
    }

    @Test
    fun `is no space left test`() {
        val arr: Array<Array<Cell>> = arrayOf(
                arrayOf(CROSS, CROSS, CROSS),
                arrayOf(CROSS, CROSS, CROSS),
                arrayOf(CROSS, CROSS, CROSS)
        )
        val lc = LineChecker(arr, 3)

        assertEquals(true, lc.isNoSpaceLeft())
    }
}