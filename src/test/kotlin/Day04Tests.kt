package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day04Tests : FunSpec () {

    private val example1 = """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """.trimIndent()

    init {
        test("Execute example A") {
            Day04().solveA(example1.lineSequence()).shouldBe(2)
        }

        test("Execute example B") {
            Day04().solveB(example1.lineSequence()).shouldBe(4)
        }
    }
}