package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day08Tests : FunSpec () {

    private val example1 = """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent()

    init {
        test("Execute example A") {
            Day08().solveA(example1.lineSequence()).shouldBe(21)
        }

        test("Execute example B") {
            Day08().solveB(example1.lineSequence()).shouldBe(8)
        }
    }
}