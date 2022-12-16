package dev.gresty.aoc.adventofcode2022

import kotlin.math.absoluteValue

fun main() {
    execute(15) { Day15().solveA(it) }
    execute(15) { Day15().solveB(it) }
}

class Day15(val part1RowToCheck: Int = 2_000_000) : Day<Int, Int> {

    override fun solveA(input: Sequence<String>): Int {
        val beaconDistances = input.map(::parse).map { it.first to it.first.manhattan(it.second) }.toMap()
        return countIntersectionsWithRow(beaconDistances, part1RowToCheck)
    }

    override fun solveB(input: Sequence<String>): Int {
        return 0
    }

    val inputRegex = """Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""".toRegex()
    fun parse(line: String) : Pair<IntPair, IntPair> {
        val values = inputRegex.find(line)!!.groupValues
        return IntPair(values[1].toInt(), values[2].toInt()) to IntPair(values[3].toInt(), values[4].toInt())
    }

    fun countIntersectionsWithRow(beaconDistances: Map<IntPair, Int>, yIntersect: Int) =
        beaconDistances.map { intersect(it.key, it.value, yIntersect) }
            .fold(mutableSetOf<IntRange>()) { set, range -> addRange(set, range) }
            .map { it.count() }
            .sum()

    fun intersect(point: IntPair, radius: Int, yIntersect: Int) : IntRange? {
        if (yIntersect < point.second - radius || yIntersect > point.second + radius) return null
        val offset = radius - (point.second - yIntersect).absoluteValue
        return IntRange(point.first - offset, point.first + offset)
    }

    fun addRange(setOfDistinctRanges: MutableSet<IntRange>, range: IntRange?) : MutableSet<IntRange> {
        var combinedRange = range
        setOfDistinctRanges.filter { it.intersects(combinedRange) }
            .forEach {
            combinedRange = it + combinedRange
            setOfDistinctRanges.remove(it)
        }
        combinedRange?.let { setOfDistinctRanges.add(it) }
        return setOfDistinctRanges
    }

}

operator fun IntPair.minus(other: IntPair) = IntPair(first - other.first, second - other.second)
fun IntPair.manhattan(other: IntPair) = (first - other.first).absoluteValue + (second - other.second).absoluteValue

fun IntRange.intersects(other: IntRange?) = other?.let { !(last < other.first || start > other.last) } ?: false
operator fun IntRange.plus(other: IntRange?) = other?.let { minOf(first, other.first)..maxOf(last, other.last) } ?: this
