package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day19Tests : FunSpec () {

    private val example1 = """
        replaceme
    """.trimIndent()

    init {
        test("Execute example A") {
            Day19().solveA(example1.lineSequence()).shouldBe(0)
        }

        test("Execute example B") {
            Day19().solveB(example1.lineSequence()).shouldBe(0)
        }
    }
}