package dev.gresty.aoc.adventofcode2022

import java.lang.RuntimeException

fun main() {
    execute(22) { Day22().solveA(it) }
    execute(22) { Day22().solveB(it) }
}

class Day22 : Day<Int, Int> {
    override fun solveA(input: Sequence<String>): Int {
        val board = Board()
        var steps: Steps? = null
        input.forEach {
            if (it.isNotEmpty())
                if (it[0].isDigit()) steps = Steps(it)
                else board.addRow(it)
        }

        while (!steps!!.finished()) {
            board.move(steps!!.distance())
            if (!steps!!.finished()) board.turn(steps!!.turn())
        }

        return (board.position.first + 1) * 1000 + (board.position.second + 1) * 4 + board.direction
    }

    override fun solveB(input: Sequence<String>): Int {
        return 0
    }

    class Steps(val steps: String) {
        var i = 0
        fun turn() : Char {
            if (steps[i].isDigit()) throw RuntimeException("Wanted a letter. Got ${steps[i]}")
            return steps[i++]
        }
        fun distance() : Int {
            if (!steps[i].isDigit()) throw RuntimeException("Wanted a digit. Got ${steps[i]}")
            val start = i
            do {
                i++
            } while(i < steps.length && steps[i].isDigit())
            return steps.substring(start, i).toInt()
        }
        fun finished() = i >= steps.length
    }

    class Board {
        val dx = listOf(1, 0, -1, 0)
        val dy = listOf(0, 1, 0, -1)

        val rows = mutableListOf<String>()
        val xminForRow = mutableListOf<Int>()
        val xmaxForRow = mutableListOf<Int>()
        val yminForCol = mutableListOf<Int>()
        val ymaxForCol = mutableListOf<Int>()
        var maxCol = 0

        var position = 0 to 0
        var direction = 0

        fun addRow(row: String) {
            val rowNum = rows.size
            val trimmedRow = row.trim()
            val xmin = row.length - trimmedRow.length
            val xmax = row.length - 1
            rows.add(trimmedRow)
            xminForRow.add(xmin)
            xmaxForRow.add(xmax)
            if (xmax > maxCol) maxCol = xmax

            if (yminForCol.size < row.length) repeat(row.length - yminForCol.size) {
                yminForCol.add(-1)
                ymaxForCol.add(-1)
            }
            trimmedRow.indices.forEach {
                val x = it + xmin
                if (yminForCol[x] == -1) yminForCol[x] = rowNum
                if (yminForCol[x] != -1) ymaxForCol[x] = rowNum
            }
            if (rowNum == 0) position = 0 to xmin + trimmedRow.indexOf('.')
        }



        fun openTileAt(pos: Pair<Int, Int>) = rows[pos.first][pos.second - xminForRow[pos.first]] == '.'
        fun move() : Boolean {
            var x = position.second + dx[direction]
            var y = position.first + dy[direction]
            if (dx[direction] != 0) {
                if (x > xmaxForRow[y]) x = xminForRow[y]
                if (x < xminForRow[y]) x = xmaxForRow[y]
            }
            if (dy[direction] != 0) {
                if (y > ymaxForCol[x]) y = yminForCol[x]
                if (y < yminForCol[x]) y = ymaxForCol[x]
            }
            val newpos = y to x
            if (openTileAt(newpos)) {
                position = newpos
                return true
            }
            return false
        }
        fun move(distance: Int) {
            var d = distance
            while (d-- > 0 && move()) {}
        }
        fun turn(rot: Char) {
            if (rot == 'L') direction = (direction - 1).mod(4)
            if (rot == 'R') direction = (direction + 1).mod(4)
        }

    }
}