import dev.CellType
import dev.LineChecker
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

import dev.CellType.*
import dev.Matrix
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LineCheckerTest {
    @Test
    fun `is horizontal line test`() {
        val arr: Array<Array<CellType>> = arrayOf(
                arrayOf(NONE, NONE, NONE, NONE, NONE),
                arrayOf(NONE, NONE, NONE, NONE, NONE),
                arrayOf(SECOND, FIRST, FIRST, FIRST, NONE)
        )
        val lc = LineChecker(arr, arr.size)

        assertTrue(lc.isHorizontalLine(FIRST))
    }

    @Test
    fun `is horizontal line test FAIL`() {
        val arr: Array<Array<CellType>> = arrayOf(
                arrayOf(NONE, NONE, NONE),
                arrayOf(NONE, NONE, NONE),
                arrayOf(FIRST, FIRST, NONE)
        )
        val lc = LineChecker(arr, arr.size)

        assertFalse(lc.isHorizontalLine(FIRST))
    }

    @Test
    fun `is vertical line test`() {
        val arr: Array<Array<CellType>> = arrayOf(
                arrayOf(FIRST, NONE, NONE),
                arrayOf(FIRST, NONE, NONE),
                arrayOf(FIRST, NONE, NONE)
        )
        val lc = LineChecker(arr, arr.size)

        assertTrue(lc.isVerticalLine(FIRST))
    }

    @Test
    fun `is vertical line test FAIL`() {
        val arr: Array<Array<CellType>> = arrayOf(
                arrayOf(NONE, NONE, NONE),
                arrayOf(FIRST, NONE, NONE),
                arrayOf(FIRST, NONE, NONE)
        )
        val lc = LineChecker(arr, arr.size)

        assertFalse(lc.isVerticalLine(FIRST))
    }

    @Test
    fun `is diagonal line test 1`() {
        val arr: Matrix<CellType> = arrayOf(
                arrayOf(NONE, NONE, NONE, FIRST),
                arrayOf(NONE, NONE, FIRST, NONE),
                arrayOf(NONE, FIRST, NONE, NONE)
        )
        val lc = LineChecker(arr, arr.size)

        assertTrue(lc.isDiagonalLine(FIRST))
    }

    @Test
    fun `is diagonal line test 2`() {
        val arr: Matrix<CellType> = arrayOf(
                arrayOf(FIRST, NONE, NONE, NONE),
                arrayOf(NONE, FIRST, NONE, NONE),
                arrayOf(NONE, NONE, FIRST, NONE)
        )
        val lc = LineChecker(arr, arr.size)

        assertTrue(lc.isDiagonalLine(FIRST))
    }

    @Test
    fun `is diagonal line test FAIL`() {
        val arr: Matrix<CellType> = arrayOf(
                arrayOf(FIRST, NONE, NONE, NONE),
                arrayOf(NONE, FIRST, NONE, NONE),
                arrayOf(NONE, NONE, NONE, NONE)
        )
        val lc = LineChecker(arr, arr.size)

        assertFalse(lc.isDiagonalLine(FIRST))
    }

    @Test
    fun `is no space left test`() {
        val arr: Matrix<CellType> = arrayOf(
                arrayOf(FIRST, FIRST, FIRST),
                arrayOf(FIRST, FIRST, FIRST),
                arrayOf(FIRST, FIRST, FIRST)
        )
        val lc = LineChecker(arr, arr.size)

        assertTrue(lc.isNoSpaceLeft())
    }

    @Test
    fun `is no space left test FAIl`() {
        val arr: Matrix<CellType> = arrayOf(
                arrayOf(FIRST, FIRST, FIRST),
                arrayOf(FIRST, FIRST, FIRST),
                arrayOf(FIRST, FIRST, NONE)
        )
        val lc = LineChecker(arr, arr.size)

        assertFalse(lc.isNoSpaceLeft())
    }
}