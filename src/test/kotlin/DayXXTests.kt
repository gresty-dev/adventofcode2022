package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class DayXXTests : FunSpec () {

    private val example1 = """
        replaceme
    """.trimIndent()

    init {
        test("Execute example A") {
            DayXX().solveA(example1.lineSequence()).shouldBe(0)
        }

        test("Execute example B") {
            DayXX().solveB(example1.lineSequence()).shouldBe(0)
        }
    }
}