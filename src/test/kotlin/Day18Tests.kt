package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day18Tests : FunSpec () {

    private val example1 = """
        2,2,2
        1,2,2
        3,2,2
        2,1,2
        2,3,2
        2,2,1
        2,2,3
        2,2,4
        2,2,6
        1,2,5
        3,2,5
        2,1,5
        2,3,5
    """.trimIndent()

    init {
        test("Execute example A") {
            Day18().solveA(example1.lineSequence()).shouldBe(64)
        }

        test("Execute example B") {
            Day18().solveB(example1.lineSequence()).shouldBe(58)
        }
    }
}