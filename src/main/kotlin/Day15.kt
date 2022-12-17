package dev.gresty.aoc.adventofcode2022

import kotlin.math.absoluteValue

fun main() {
    execute(15) { Day15().solveA(it) }
    execute(15) { Day15().solveB(it) }
}

class Day15(val part1RowToCheck: Int = 2_000_000, val maxSize: Int = 4_000_000) : Day<Int, Long> {

    override fun solveA(input: Sequence<String>): Int {
        val sensorBeacons = input.map(::parse).toMap()
        return countIntersectionsWithRow(sensorBeacons, part1RowToCheck)
    }

    override fun solveB(input: Sequence<String>): Long {
        val sensorBeacons = input.map { parse(it) }
            .map { it.first to it.first.manhattan(it.second) }
            .toMap()
        for (y in 0..maxSize) {
            val intersections = intersectionsWithRow(sensorBeacons, y)
            if (intersections.size == 2) {
                val asList = intersections.toList()
                val x = if (asList[0].first < asList[1].first) asList[0].last + 1 else asList[1].last + 1
                return x.toLong() * 4_000_000L + y.toLong()
            }
        }

        return 0
    }

    val inputRegex = """Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""".toRegex()
    fun parse(line: String) : Pair<IntPair, IntPair> {
        val values = inputRegex.find(line)!!.groupValues
        return IntPair(values[1].toInt(), values[2].toInt()) to IntPair(values[3].toInt(), values[4].toInt())
    }

    fun countIntersectionsWithRow(sensorBeacons: Map<IntPair, IntPair>, yIntersect: Int) : Int {
        val beaconsOnRow = sensorBeacons.values.filter { it.second == yIntersect }.map { it.first }.toSet()
        return sensorBeacons.map { intersect(it.key, it.key.manhattan(it.value), yIntersect) }
            .fold(mutableSetOf<IntRange>()) { set, range -> addRange(set, range) }
            .map { count(it, beaconsOnRow) }
            .sum()
    }

    fun intersectionsWithRow(sensorDistances: Map<IntPair, Int>, yIntersect: Int) : Set<IntRange> {
        return sensorDistances.map { intersect(it.key, it.value, yIntersect) }
            .map { (0..maxSize).intersect(it) }
            .fold(mutableSetOf()) { set, range -> addRange(set, range) }
    }

    fun count(range: IntRange, beacons: Set<Int>) : Int {
        return range.count() - beacons.filter { range.contains(it) }.count()
    }

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

