package dev.gresty.aoc.adventofcode2022.day03

import dev.gresty.aoc.adventofcode2022.execute

fun main() {
    execute(::solve03A, "day03.txt")
    execute(::solve03B, "day03.txt")
}

fun solve03A(input: Sequence<String>): Int {
    return input.map { divide(it) }
        .map { findCommonItem(it) }
        .map { priority(it) }
        .sum()
}

fun solve03B(input: Sequence<String>): Int {
    return input.runningFold(listOf<String>()) { acc, s -> if (acc.size < 3) acc + s else listOf(s) }
        .filter { it.size == 3 }
        .map { Triple(it[0], it[1], it[2]) }
        .map { findCommonItem(it) }
        .map { priority(it) }
        .sum()
}

fun divide(source: String) =
    source.substring(0, source.length / 2) to source.substring(source.length / 2)

fun findCommonItem(items: Pair<String, String>) =
    items.first.asSequence().filter { items.second.contains(it) }.first()

fun findCommonItem(items: Triple<String, String, String>) =
    items.first.asSequence().filter { items.second.contains(it) && items.third.contains(it) }.first()

fun priority(item: Char) =
    if (item >= 'a') item - 'a' + 1 else item - 'A' + 27