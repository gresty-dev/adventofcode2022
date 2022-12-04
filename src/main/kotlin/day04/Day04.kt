package dev.gresty.aoc.adventofcode2022.day04

import dev.gresty.aoc.adventofcode2022.execute

val regex = """^(\d+)-(\d+),(\d+)-(\d+)$""".toRegex()

fun main() {
    execute(::solve04A, "day04.txt")
    execute(::solve04B, "day04.txt")
}

fun solve04A(input: Sequence<String>): Int {
    return input.map { regex.find(it)!!.groupValues }
        .map { asIntPair(it[1], it[2]) to asIntPair(it[3], it[4]) }
        .filter { wraps(it.first, it.second) || wraps(it.second, it.first) }
        .count()
}

fun solve04B(input: Sequence<String>): Int {
    return input.map { regex.find(it)!!.groupValues }
        .map { asIntPair(it[1], it[2]) to asIntPair(it[3], it[4]) }
        .filter { !(isBefore(it.first, it.second) || isBefore(it.second, it.first)) }
        .count()
}

fun asIntPair(first: String, second: String) =
    first.toInt() to second.toInt()

fun wraps(first: Pair<Int, Int>, second: Pair<Int, Int>) =
    first.first <= second.first && first.second >= second.second

fun isBefore(first: Pair<Int, Int>, second: Pair<Int, Int>) =
    first.first < second.first && first.second < second.first
