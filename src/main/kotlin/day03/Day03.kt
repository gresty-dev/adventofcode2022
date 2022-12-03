package dev.gresty.aoc.adventofcode2022.day03

import dev.gresty.aoc.adventofcode2022.execute
import java.lang.IllegalStateException

fun main() {
    execute(::solve03A, "day03.txt")
    execute(::solve03B, "day03.txt")
}

fun solve03A(input: Sequence<String>): Int {
    return input.map { divide(it) }
        .map { findCommonItem(it[0], it[1]) }
        .map { priority(it) }
        .sum()
}

fun solve03B(input: Sequence<String>): Int {
    return input.chunked(3)
        .map { findCommonItem(it[0], it[1], it[2]) }
        .map { priority(it) }
        .sum()
}

fun divide(source: String) =
    listOf(source.substring(0, source.length / 2), source.substring(source.length / 2))

fun findCommonItem(items1: String, items2: String) : Char {
    items1.forEach {
        if (items2.contains(it)) return it
    }
    throw IllegalStateException("No common items!")
}

fun findCommonItem(items1: String, items2: String, items3: String) : Char {
    items1.forEach {
        if (items2.contains(it) && items3.contains(it)) return it
    }
    throw IllegalStateException("No common items!")
}

fun priority(item: Char) =
    if (item >= 'a') item - 'a' + 1 else item - 'A' + 27