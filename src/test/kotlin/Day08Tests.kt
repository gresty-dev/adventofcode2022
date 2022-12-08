package dev.gresty.aoc.adventofcode2022

import dev.gresty.aoc.adventofcode2022.day08.solve08A
import dev.gresty.aoc.adventofcode2022.day08.solve08B
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
            solve08A(example1.lineSequence()).shouldBe(21)
        }

        test("Execute example B") {
            solve08B(example1.lineSequence()).shouldBe(8)
        }
    }
}