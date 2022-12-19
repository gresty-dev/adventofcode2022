package dev.gresty.aoc.adventofcode2022

fun main() {
    execute(17) { Day17().solveA(it) }
    execute(17) { Day17().solveB(it) }
}

class Day17 : Day<Int, Int> {
    override fun solveA(input: Sequence<String>): Int {
        val width = 7
        val cave = mutableListOf<Int>()
        return 0
    }

    override fun solveB(input: Sequence<String>): Int {
        return 0
    }

    /**
     * 8: left wall
     * 7-1: space
     * 0: right wall
     * row 0: floor
     */
    class Cave(val width: Int) {
        val rocks = mutableListOf<Int>()
        var top = 0

        fun add(rock: Rock) {
            var x = 5
            var y = top + 4

        }

        fun moveLeft() {

        }
    }

    data class Rock(val shape: List<Int>)

    class MovableRock(rock: Rock, ) {
        val shape = rock.shape.toMutableList()

    }

}