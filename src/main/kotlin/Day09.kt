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
            moveHead(instr[0], instr.substring(2).toInt())
            return this
        }

        fun visited() = visited.size

        private fun moveHead(direction: Char, steps: Int) {
            when (direction) {
                'U' -> for (i in 1..steps) { y[0]++ ; moveTail() }
                'D' -> for (i in 1..steps) { y[0]-- ; moveTail() }
                'L' -> for (i in 1..steps) { x[0]-- ; moveTail() }
                'R' -> for (i in 1..steps) { x[0]++ ; moveTail() }
                else -> throw RuntimeException("Unexpected direction $direction")
            }
        }

        private fun moveTail() {
            val lastx = x[tailEnd]
            val lasty = y[tailEnd]
            for (i in 1 .. tailEnd) {
                val dx = x[i - 1] - x[i]
                val dy = y[i - 1] - y[i]
                if (dx in adjacent && dy in adjacent) return
                x[i] += dx.sign
                y[i] += dy.sign
            }
            if (x[tailEnd] != lastx || y[tailEnd] != lasty)
                visited.add(x[tailEnd] to y[tailEnd])
        }
    }
}