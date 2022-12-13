package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day12Tests : FunSpec () {

    private val example1 = """
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi
    """.trimIndent()

    init {
        test("Execute example A") {
            Day12().solveA(example1.lineSequence()).shouldBe(31)
        }

        test("Execute example B") {
            Day12().solveB(example1.lineSequence()).shouldBe(29)
        }
    }
}