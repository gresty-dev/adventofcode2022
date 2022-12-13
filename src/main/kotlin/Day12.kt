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

    class ShortestPath(private val start: IntPair,
                       private val isEnd: (IntPair) -> Boolean,
                       val next: (IntPair) -> List<IntPair>,
                       val distance: (IntPair) -> Int,
                       val setDistance: (IntPair, Int) -> Unit,
                       val setVisited: (IntPair) -> Unit) {

        // No need for a priority queue, as all distances between adjacent nodes are 1
        // Which basically makes this a BFS
        private val queue = ArrayDeque<IntPair>()

        fun find() : Int {
            setDistance(start, 0)
            queue.addLast(start)
            var current = start

            while (queue.isNotEmpty()) {
                current = queue.removeFirst()
                if (isEnd(current)) break
                val distance = distance(current) + 1
                next(current).forEach {
                    if (distance < distance(it)) {
                        setDistance(it, distance)
                        queue.remove(it)
                        queue.addLast(it)
                    }
                }
                setVisited(current)
            }
            return distance(current)
        }
    }
}