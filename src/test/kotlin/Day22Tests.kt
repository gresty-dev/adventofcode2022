package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day22Tests : FunSpec () {

    private val example1 = """
        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R5L5R10L4R5L5
    """.trimIndent()

    init {
        test("Execute example A") {
            Day22().solveA(example1.lineSequence()).shouldBe(6032)
        }

        test("Execute example B") {
            Day22().solveB(example1.lineSequence()).shouldBe(0)
        }
    }
}