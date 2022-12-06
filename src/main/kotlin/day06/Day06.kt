package dev.gresty.aoc.adventofcode2022.day06

import dev.gresty.aoc.adventofcode2022.execute

fun main() {
    execute("day06.txt") { solve06A(it.flatMap { s -> s.asSequence() }) }
    execute("day06.txt") { solve06B(it.flatMap { s -> s.asSequence() }) }
}

fun solve06A(input: Sequence<Char>): Int {
    return input.windowed(4)
        .withIndex()
        .filter { it.value.distinct().size == 4 }
        .first()
        .index + 4
}

fun solve06B(input: Sequence<Char>): Int {
    return input.windowed(14)
        .withIndex()
        .filter { it.value.distinct().size == 14 }
        .first()
        .index + 14
}

