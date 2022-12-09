package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day09Tests : FunSpec () {

    private val example1 = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent()

    private val example2 = """
        R 5
        U 8
        L 8
        D 3
        R 17
        D 10
        L 25
        U 20
    """.trimIndent()

    init {
        test("Execute example A") {
            Day09().solveA(example1.lineSequence()).shouldBe(13)
        }

        test("Execute example B") {
            Day09().solveB(example1.lineSequence()).shouldBe(1)
            Day09().solveB(example2.lineSequence()).shouldBe(36)
        }
    }
}