package dev.gresty.aoc.adventofcode2022.day04

import dev.gresty.aoc.adventofcode2022.execute

fun main() {
    execute("day04.txt") { solve04A(it) }
    execute("day04.txt") { solve04B(it) }
}

fun solve04A(input: Sequence<String>): Int {
    return input.map { it.asIntRangePair() }
        .filter { it.first wraps it.second || it.second wraps it.first }
        .count()
}

fun solve04B(input: Sequence<String>): Int {
    return input.map { it.asIntRangePair() }
        .filter { !(it.first isBefore it.second || it.second isBefore it.first ) }
        .count()
}

fun String.asIntRangePair() =
    substringBefore(',').asIntRange() to substringAfter(',').asIntRange()

fun String.asIntRange() =
    substringBefore('-').toInt() .. substringAfter('-').toInt()

infix fun IntRange.wraps(other: IntRange) =
    first <= other.first && last >= other.last

infix fun IntRange.isBefore(other: IntRange) =
    first < other.first && last < other.first

