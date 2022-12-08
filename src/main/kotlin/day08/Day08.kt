package dev.gresty.aoc.adventofcode2022.day08

import dev.gresty.aoc.adventofcode2022.execute

fun main() {
    execute("day08.txt") { solve08A(it) }
    execute("day08.txt") { solve08B(it) }
}

fun solve08A(input: Sequence<String>): Int {
    val forest = input.fold(Forest()) { f, r -> f.addTreeRow(r) }
    val visible = mutableSetOf<Pair<Int, Int>>()
    forest.findVisible { row, col, _ -> visible.add(row to col) }
    return visible.count()
}

fun solve08B(input: Sequence<String>): Int {
    return 0
}

class Forest {
    private val trees = mutableListOf<String>()

    fun addTreeRow(row: String) : Forest {
        trees.add(row)
        return this
    }

    fun findVisible(action: (row: Int, col: Int, height:Int) -> Unit) {
        trees.indices.forEach {
            val lastCol = findVisibleInRow(it, trees[0].indices, action)
            findVisibleInRow(it, (trees[0].length - 1) downTo (lastCol + 1), action)
        }
        trees[0].indices.forEach {
            val lastRow = findVisibleInColumn(it, trees.indices, action)
            findVisibleInColumn(it, (trees.size - 1) downTo (lastRow + 1), action)
        }
    }

    private fun findVisibleInRow(row: Int, cols: IntProgression, action: (Int, Int, Int) -> Unit) : Int {
        val treeRow = trees[row]
        var max = -1
        var lastVisibleCol = 0
        cols.forEach {
            if (treeRow[it] - '0' > max) {
                max = treeRow[it] - '0'
                lastVisibleCol = it
                action(row, it, max)
            }
        }
        return lastVisibleCol
    }

    private fun findVisibleInColumn(col: Int, rows: IntProgression, action: (Int, Int, Int) -> Unit) : Int {
        var max = -1
        var lastVisibleRow = 0
        rows.forEach {
            if (trees[it][col] - '0' > max) {
                max = trees[it][col] - '0'
                lastVisibleRow = it
                action(it, col, max)
            }
        }
        return lastVisibleRow
    }
}

