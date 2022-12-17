package dev.gresty.aoc.adventofcode2022

fun main() {
    execute(14) { Day14().solveA(it) }
    execute(14) { Day14().solveB(it) }
}

class Day14 : Day<Int, Int> {
    override fun solveA(input: Sequence<String>) =
        input.map(::parse).fold(Cave()) { cave, path -> cave.rock(path) }.flood()

    override fun solveB(input: Sequence<String>): Int {
        return 0
    }

    private fun parse(line: String) = line.splitToSequence(" -> ")
            .map(::intPairOf)
            .toList()

    class Cave {
        val rock = mutableSetOf<IntPair>()
        val origin = IntPair(500, 0)

        fun rock(vertices: List<IntPair>) : Cave {
            rock.add(vertices[0])
            var last = vertices[0]
            for (v in 1 until vertices.size) {
                val diff = vertices[v] - last
                val step = diff.sign()
                val count = diff / step
            }
            return this
        }

        fun flood() : Int {
            val occupied = rock.toMutableSet()
            var grains = 0
            var done = false
            do {

            } while (!done)

            return 0
        }


    }
}