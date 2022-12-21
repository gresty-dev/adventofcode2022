package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day20Tests : FunSpec () {

    private val example1 = """
        1
        2
        -3
        3
        -2
        0
        4
    """.trimIndent()

    init {
        test("Execute example A") {
            Day20().solveA(example1.lineSequence()).shouldBe(3L)
        }

        test("Execute example B") {
            Day20().solveB(example1.lineSequence()).shouldBe(1623178306L)
        }
    }
}