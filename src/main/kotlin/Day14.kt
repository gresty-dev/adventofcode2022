package dev.gresty.aoc.adventofcode2022

fun main() {
    execute(14) { Day14().solveA(it) }
    execute(14) { Day14().solveB(it) }
}

class Day14 : Day<Int, Int> {
    override fun solveA(input: Sequence<String>) =
        input.map(::parse).fold(Cave()) { cave, path -> cave.rock(path) }.flood()

    override fun solveB(input: Sequence<String>) =
        input.map(::parse).fold(Cave(withFloor = true)) { cave, path -> cave.rock(path) }.flood()

    private fun parse(line: String) = line.splitToSequence(" -> ")
            .map(::intPairOf)
            .toList()

    class Cave(val withFloor: Boolean = false) {
        private val rock = mutableSetOf<IntPair>()
        private var maxDepth = 0
        private val origin = IntPair(500, 0)

        fun rock(vertices: List<IntPair>) : Cave {
            rock.add(vertices[0])
            var last = vertices[0]
            for (v in 1 until vertices.size) {
                if (vertices[v].second > maxDepth) maxDepth = vertices[v].second
                val step = (vertices[v] - last).sign()
                do {
                    last += step
                    rock.add(last)
                } while (last != vertices[v])
            }
            return this
        }

        private val directions = listOf(0 to 1, -1 to 1, 1 to 1)

        fun flood() : Int {
            val occupied = rock.toMutableSet()
            var grains = 0
            var added: Boolean
            do {
                added = addGrain(occupied)
                grains += if (added) 1 else 0
            } while (added)

            return grains
        }

        private fun addGrain(occupied: MutableSet<IntPair>) : Boolean {
            var grain = origin
            if (occupied.contains(grain)) {
                return false
            }
            var falling: Boolean
            do {
                falling = false
                for (i in 0..2) {
                    if (!occupied.contains(grain + directions[i])) {
                        grain += directions[i]
                        falling = true
                        break
                    }
                }
            } while(falling && grain.second < maxDepth + 1)
            if (!falling || withFloor) {
                occupied.add(grain)
                return true
            }
            return false
        }

    }
}