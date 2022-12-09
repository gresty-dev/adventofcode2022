package dev.gresty.aoc.adventofcode2022

import kotlin.math.abs

fun main() {
    execute(8) { Day08().solveA(it) }
    execute(8) { Day08().solveB(it) }
}

class Day08 : Day<Int> {
    override fun solveA(input: Sequence<String>): Int {
        val forest = input.fold(Forest()) { f, r -> f.addTreeRow(r) }
        return forest.findVisible()
    }

    override fun solveB(input: Sequence<String>): Int {
        val forest = input.fold(Forest()) { f, r -> f.addTreeRow(r) }
        return forest.calculateScenic()
    }

    class Forest {
        private val trees = mutableListOf<String>()

        fun addTreeRow(row: String): Forest {
            trees.add(row)
            return this
        }

        fun findVisible(): Int {
            val visible = mutableSetOf<Pair<Int, Int>>()
            fun onVisible(r: Int, c: Int) = visible.add(r to c)

            rows().forEach {
                val lastCol = findVisibleInRow(it, trees[0].indices, ::onVisible)
                findVisibleInRow(it, (trees[0].length - 1) downTo (lastCol + 1), ::onVisible)
            }
            cols().forEach {
                val lastRow = findVisibleInColumn(it, trees.indices, ::onVisible)
                findVisibleInColumn(it, (trees.size - 1) downTo (lastRow + 1), ::onVisible)
            }
            return visible.count()
        }

        private fun findVisibleInRow(row: Int, cols: IntProgression, action: (Int, Int) -> Unit): Int {
            val treeRow = trees[row]
            var max = -1
            var lastVisibleCol = 0
            cols.forEach {
                if (treeRow[it] - '0' > max) {
                    max = treeRow[it] - '0'
                    lastVisibleCol = it
                    action(row, it)
                }
            }
            return lastVisibleCol
        }

        private fun findVisibleInColumn(col: Int, rows: IntProgression, action: (Int, Int) -> Unit): Int {
            var max = -1
            var lastVisibleRow = 0
            rows.forEach {
                if (trees[it][col] - '0' > max) {
                    max = trees[it][col] - '0'
                    lastVisibleRow = it
                    action(it, col)
                }
            }
            return lastVisibleRow
        }

        fun calculateScenic(): Int {
            val scenic = Array(trees.size) { _ -> IntArray(trees[0].length) { _ -> 1 } }
            val rows = rows()
            val cols = cols()
            val rowsReversed = rows.reversed()
            val colsReversed = cols.reversed()

            rows.forEach { row ->
                updateScenicForLine(row, cols, false, scenic)
                updateScenicForLine(row, colsReversed, false, scenic)
            }
            cols.forEach { col ->
                updateScenicForLine(col, rows, true, scenic)
                updateScenicForLine(col, rowsReversed, true, scenic)
            }

            return scenic.asSequence()
                .flatMap { it.asSequence() }
                .max()
        }

        private val treesOfHeight = Array(10) { _ -> mutableSetOf<Int>() }

        private fun updateScenicForLine(
            fixed: Int,
            variable: IntProgression,
            transpose: Boolean,
            scenic: Array<IntArray>
        ) {

            fun updateScenicForTreeHeight(treeIndex: Int, height: Int) =
                (0..height).forEach { h ->
                    treesOfHeight[h].forEach { t ->
                        updateScenic(scenic, fixed, t, transpose, abs(treeIndex - t))
                    }
                    treesOfHeight[h].clear()
                }

            variable.forEach {
                val height = tree(fixed, it, transpose)
                updateScenicForTreeHeight(it, height)
                treesOfHeight[height].add(it)
            }
            updateScenicForTreeHeight(variable.last, 9)
        }

        private fun updateScenic(scenic: Array<IntArray>, row: Int, col: Int, transpose: Boolean, value: Int) {
            if (transpose) {
                scenic[col][row] *= value
            } else {
                scenic[row][col] *= value
            }
        }

        private fun rows() = trees.indices
        private fun cols() = trees[0].indices
        private fun tree(row: Int, col: Int, transpose: Boolean) =
            if (transpose) trees[col][row] - '0' else trees[row][col] - '0'
    }

}