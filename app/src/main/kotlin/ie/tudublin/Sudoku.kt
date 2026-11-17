package ie.tudublin

class Sudoku(input: Array<IntArray>) {
    val board: Array<IntArray> = Array(9) { input[it].copyOf() }
    var iterations = 0
        private set

    companion object {
        const val MAX_ITERATIONS = 2_000_000
    }

    fun solve(): Boolean {
        iterations = 0
        if (!givensAreValid()) return false
        return solveCell(0)
    }

    private fun givensAreValid(): Boolean {
        for (r in 0..8) for (c in 0..8) {
            val v = board[r][c]
            if (v == 0) continue
            board[r][c] = 0
            val ok = isValid(r, c, v)
            board[r][c] = v
            if (!ok) return false
        }
        return true
    }

    private fun solveCell(pos: Int): Boolean {
        if (iterations > MAX_ITERATIONS) return false
        if (pos == 81) return true
        val r = pos / 9
        val c = pos % 9
        if (board[r][c] != 0) return solveCell(pos + 1)
        for (v in 1..9) {
            if (isValid(r, c, v)) {
                iterations++
                board[r][c] = v
                if (solveCell(pos + 1)) return true
                board[r][c] = 0
                if (iterations > MAX_ITERATIONS) return false
            }
        }
        return false
    }

    private fun isValid(r: Int, c: Int, v: Int): Boolean {
        for (i in 0..8) {
            if (board[r][i] == v || board[i][c] == v) return false
        }
        val br = r / 3 * 3
        val bc = c / 3 * 3
        for (i in 0..2) for (j in 0..2) {
            if (board[br + i][bc + j] == v) return false
        }
        return true
    }
}