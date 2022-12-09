package dev.gresty.aoc.adventofcode2022

import kotlin.math.sign

fun main() {
    execute("day09.txt") { Day09().solveA(it) }
    execute("day09.txt") { Day09().solveB(it) }
}

class Day09 : Day<Int> {
    override fun solveA(input: Sequence<String>): Int {
        return input.fold(Rope(2)) { acc, move -> acc.moveHead(move) }.visited()
    }

    override fun solveB(input: Sequence<String>): Int {
        return input.fold(Rope(10)) { acc, move -> acc.moveHead(move) }.visited()
    }

    class Rope(length: Int) {
        private var x = IntArray(length)
        private var y = IntArray(length)
        private val visited = mutableSetOf(x[0] to y[0])
        private val tailEnd = length - 1
        private val adjacent = -1 .. 1

        fun moveHead(instr: String) : Rope {
            val direction = instr[0]
            val steps = instr.substring(2).toInt()
            for (i in 1 ..steps) {
                moveHeadOneSpace(direction)
                for (j in 1 .. tailEnd) { moveTail(j) }
                visited.add(x[tailEnd] to y[tailEnd])
            }
            return this
        }

        fun visited() = visited.size

        private fun moveHeadOneSpace(direction: Char) {
            when (direction) {
                'U' -> y[0]++
                'D' -> y[0]--
                'L' -> x[0]--
                'R' -> x[0]++
                else -> throw RuntimeException("Unexpected direction $direction")
            }
        }

        private fun moveTail(i: Int) {
            val dx = x[i - 1] - x[i]
            val dy = y[i - 1] - y[i]
            if (dx in adjacent && dy in adjacent) return
            x[i] += dx.sign
            y[i] += dy.sign
        }
    }
}