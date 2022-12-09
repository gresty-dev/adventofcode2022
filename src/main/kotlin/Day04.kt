package dev.gresty.aoc.adventofcode2022

fun main() {
    execute(4) { Day04().solveA(it) }
    execute(4) { Day04().solveB(it) }
}

class Day04 : Day<Int> {
    override fun solveA(input: Sequence<String>): Int {
        return input.map { it.asIntRangePair() }
            .filter { it.first wraps it.second || it.second wraps it.first }
            .count()
    }

    override fun solveB(input: Sequence<String>): Int {
        return input.map { it.asIntRangePair() }
            .filter { it.first overlaps it.second }
            .count()
    }

    fun String.asIntRangePair() =
        substringBefore(',').asIntRange() to substringAfter(',').asIntRange()

    fun String.asIntRange() =
        substringBefore('-').toInt()..substringAfter('-').toInt()

    infix fun IntRange.wraps(other: IntRange) =
        first <= other.first && last >= other.last

    infix fun IntRange.overlaps(other: IntRange) =
        first <= other.last && last >= other.first

}