package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day14Tests : FunSpec () {

    private val example1 = """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent()

    init {
        test("Execute example A") {
            Day14().solveA(example1.lineSequence()).shouldBe(0)
        }

        test("Execute example B") {
            Day14().solveB(example1.lineSequence()).shouldBe(0)
        }
    }
}