package dev.gresty.aoc.adventofcode2022.day01

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day01Tests : FunSpec () {

    private val example1 = """
        1000
        2000
        3000

        4000

        5000
        6000

        7000
        8000
        9000

        10000
    """.trimIndent()

    init {
        test("Execute example 1") {
            solve01A(example1.lineSequence()).shouldBe(24000)
        }

        test("Execute example 2") {
            solve01B(example1.lineSequence()).shouldBe(45000)
        }
    }
}