package dev.gresty.aoc.adventofcode2022.day04

import dev.gresty.aoc.adventofcode2022.execute

val regex = """^(\d+)-(\d+),(\d+)-(\d+)$""".toRegex()

fun main() {
    execute("day04.txt") { solve04A(it) }
    execute("day04.txt") { solve04B(it) }
}

fun solve04A(input: Sequence<String>): Int {
    return input.map { parse(it) }
        .map { asIntPair(it[1], it[2]) to asIntPair(it[3], it[4]) }
        .filter { wraps(it.first, it.second) || wraps(it.second, it.first) }
        .count()
}

fun solve04B(input: Sequence<String>): Int {
    return input.map { parse(it) }
        .map { asIntPair(it[1], it[2]) to asIntPair(it[3], it[4]) }
        .filter { !(isBefore(it.first, it.second) || isBefore(it.second, it.first)) }
        .count()
}

private fun parse(it: String) = regex.find(it)!!.groupValues

fun asIntPair(first: String, second: String) =
    first.toInt() to second.toInt()

fun wraps(first: Pair<Int, Int>, second: Pair<Int, Int>) =
    first.first <= second.first && first.second >= second.second

fun isBefore(first: Pair<Int, Int>, second: Pair<Int, Int>) =
    first.first < second.first && first.second < second.first

