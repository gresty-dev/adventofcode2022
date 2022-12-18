package dev.gresty.aoc.adventofcode2022

import kotlin.collections.ArrayDeque

fun main() {
    execute(12) { Day12().solveA(it) }
    execute(12) { Day12().solveB(it) }
}

class Day12 : Day<Int, Int> {
    override fun solveA(input: Sequence<String>): Int {
        val heights = HeightMap(input.toList())
        return ShortestPath(heights.start, { x -> x == heights.end }, heights::pathsFrom, heights::distance, heights::setDistance, heights::setVisited).find()
    }

    override fun solveB(input: Sequence<String>): Int {
        val heights = HeightMap(input.toList())
        return ShortestPath(heights.end, { x -> heights.height(x) == 0 }, heights::pathsTo, heights::distance, heights::setDistance, heights::setVisited).find()
    }

    class HeightMap(private val heights: List<String>) {
        val start = find('S')
        val end = find('E')
        private val rows = heights.size
        private val cols = heights[0].length
        private val distances = Array(rows) { Array(cols) { Int.MAX_VALUE } }
        private val visited = Array(rows) { Array(cols) { false } }
        private val paths = listOf(IntPair(-1, 0), IntPair(1, 0), IntPair(0, -1), IntPair(0, 1))

        fun height(loc: IntPair) = if (loc == start) 0 else if (loc == end) 25 else heights[loc.first][loc.second] - 'a'
        fun distance(loc: IntPair) = distances[loc.first][loc.second]
        fun setDistance(loc: IntPair, distance: Int) {
            distances[loc.first][loc.second] = distance
        }
        fun setVisited(loc: IntPair) {
            visited[loc.first][loc.second] = true
        }

        fun pathsFrom(loc: IntPair) = paths.map { loc + it }
            .filter { it.first in 0 until rows && it.second in 0 until cols }
            .filter { (height(it) - height(loc)) <= 1 }
            .toList()

        fun pathsTo(loc: IntPair) = paths.map { loc + it }
            .filter { it.first in 0 until rows && it.second in 0 until cols }
            .filter { (height(it) - height(loc)) >= -1 }
            .toList()

        private fun find(char: Char) = heights.asSequence().withIndex().filter { it.value.contains(char) }.map { IntPair(it.index, it.value.indexOf(char)) }.first()
    }
}