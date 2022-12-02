package dev.gresty.aoc.adventofcode2022

import dev.gresty.aoc.adventofcode2022.day02.solve02A
import dev.gresty.aoc.adventofcode2022.day02.solve02B
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
            solve02A(example1.lineSequence()).shouldBe(15)
        }

        test("Execute example 2") {
            solve02B(example1.lineSequence()).shouldBe(12)
        }
    }
}