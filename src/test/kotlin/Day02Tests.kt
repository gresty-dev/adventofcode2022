package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day02Tests : FunSpec () {

    private val example1 = """
        A Y
        B X
        C Z
    """.trimIndent()

    init {
        test("Execute example 1") {
            Day02().solveA(example1.lineSequence()).shouldBe(15)
        }

        test("Execute example 2") {
            Day02().solveB(example1.lineSequence()).shouldBe(12)
        }
    }
}