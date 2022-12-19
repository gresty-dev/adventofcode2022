package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day17Tests : FunSpec () {

    private val example1 = """
        >>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>
    """.trimIndent()

    init {
        test("Execute example A") {
            Day17().solveA(example1.lineSequence()).shouldBe(3068L)
        }

        test("Execute example B") {
            Day17().solveB(example1.lineSequence()).shouldBe(1_514_285_714_288L)
        }
    }
}