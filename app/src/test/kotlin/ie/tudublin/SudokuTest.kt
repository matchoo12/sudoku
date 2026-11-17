package ie.tudublin

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class SudokuTest {
    private fun parse(s: String): Array<IntArray> = s.trimIndent().lines()
        .map { l -> IntArray(9) { l[it].digitToIntOrNull() ?: 0 } }.toTypedArray()

    @Test
    fun solvesPuzzle1() {
        val s = Sudoku(parse("""
            53..7....
            6..195...
            .98....6.
            8...6...3
            4..8.3..1
            7...2...6
            .6....28.
            ...419..5
            ....8..79
        """))
        assertTrue(s.solve())
        val expected = parse("""
            534678912
            672195348
            198342567
            859761423
            426853791
            713924856
            961537284
            287419635
            345286179
        """)
        for (r in 0..8) assertArrayEquals(expected[r], s.board[r])
    }

    @Test
    fun solvesPuzzle2() {
        val s = Sudoku(parse("""
            ..3.2.6..
            9..3.5..1
            ..18.64..
            ..81.29..
            7.......8
            ..67.82..
            ..26.95..
            8..2.3..9
            ..5.1.3..
        """))
        assertTrue(s.solve())
        val all = (1..9).toSet()
        for (i in 0..8) {
            assertEquals(all, s.board[i].toSet())
            assertEquals(all, (0..8).map { s.board[it][i] }.toSet())
            val br = i / 3 * 3
            val bc = i % 3 * 3
            assertEquals(all, (0..8).map { s.board[br + it / 3][bc + it % 3] }.toSet())
        }
    }

    @Test
    fun invalidBoardIsRejected() {
        val s = Sudoku(parse("""
            55.......
            .........
            .........
            .........
            .........
            .........
            .........
            .........
            .........
        """))
        assertFalse(s.solve())
    }
}

