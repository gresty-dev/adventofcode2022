package dev.gresty.aoc.adventofcode2022

import dev.gresty.aoc.adventofcode2022.dayX.solveXXA
import dev.gresty.aoc.adventofcode2022.dayX.solveXXB
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class DayXXTests : FunSpec () {

    private val example1 = """
        replaceme
    """.trimIndent()

    init {
        test("Execute example A") {
            solveXXA(example1.lineSequence()).shouldBe(0)
        }

        test("Execute example B") {
            solveXXB(example1.lineSequence()).shouldBe(0)
        }
    }
}