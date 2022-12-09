package dev.gresty.aoc.adventofcode2022

fun main() {
    execute(6) { Day06().solveA(it) }
    execute(6) { Day06().solveB(it) }
}

class Day06 : Day<Int> {
    override fun solveA(input: Sequence<String>): Int {
        return input.first().asSequence().windowed(4)
            .withIndex()
            .filter { it.value.distinct().size == 4 }
            .first()
            .index + 4
    }

    override fun solveB(input: Sequence<String>): Int {
        return input.first().asSequence().windowed(14)
            .withIndex()
            .filter { it.value.distinct().size == 14 }
            .first()
            .index + 14
    }

}