import dev.CellType
import dev.LineChecker
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

import dev.CellType.*
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LineCheckerTest {
    @Test
    fun `is horizontal line test`() {
        val arr: Array<Array<CellType>> = arrayOf(
                arrayOf(EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY),
                arrayOf(SECOND, SECOND, SECOND)
        )
        val lc = LineChecker(arr, 3)

        assertEquals(true, lc.isHorizontalLine(SECOND))
    }


    @Test
    fun `is vertical line test`() {
        val arr: Array<Array<CellType>> = arrayOf(
                arrayOf(SECOND, EMPTY, EMPTY),
                arrayOf(SECOND, EMPTY, EMPTY),
                arrayOf(SECOND, EMPTY, EMPTY)
        )
        val lc = LineChecker(arr, 3)

        assertEquals(true, lc.isVerticalLine(SECOND))
    }

    @Test
    fun `is diagonal line test`() {
        val arr: Array<Array<CellType>> = arrayOf(
                arrayOf(EMPTY, EMPTY, SECOND),
                arrayOf(EMPTY, SECOND, EMPTY),
                arrayOf(SECOND, EMPTY, EMPTY)
        )
        val lc = LineChecker(arr, 3)

        assertEquals(true, lc.isDiagonalLine(SECOND))
    }

    @Test
    fun `is no space left test`() {
        val arr: Array<Array<CellType>> = arrayOf(
                arrayOf(SECOND, SECOND, SECOND),
                arrayOf(SECOND, SECOND, SECOND),
                arrayOf(SECOND, SECOND, SECOND)
        )
        val lc = LineChecker(arr, 3)

        assertEquals(true, lc.isNoSpaceLeft())
    }
}