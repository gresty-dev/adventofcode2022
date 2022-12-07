package dev.gresty.aoc.adventofcode2022.day06

import dev.gresty.aoc.adventofcode2022.execute

fun main() {
    execute("day06.txt") { solve06A(it) }
    execute("day06.txt") { solve06B(it) }
}

fun solve06A(input: Sequence<String>): Int {
    return input.first().asSequence().windowed(4)
        .withIndex()
        .filter { it.value.distinct().size == 4 }
        .first()
        .index + 4
}

fun solve06B(input: Sequence<String>): Int {
    return input.first().asSequence().windowed(14)
        .withIndex()
        .filter { it.value.distinct().size == 14 }
        .first()
        .index + 14
}

