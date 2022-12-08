package dev.gresty.aoc.adventofcode2022

fun main() {
    execute("dayXX.txt") { DayXX().solveA(it) }
    execute("dayXX.txt") { DayXX().solveB(it) }
}

class DayXX : Day<Int> {
    override fun solveA(input: Sequence<String>): Int {
        return 0
    }

    override fun solveB(input: Sequence<String>): Int {
        return 0
    }
}