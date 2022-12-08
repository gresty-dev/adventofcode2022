package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day03Tests : FunSpec () {

    private val example1 = """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent()

    init {
        test("Execute example A") {
            Day03().solveA(example1.lineSequence()).shouldBe(157)
        }

        test("Execute example B") {
            Day03().solveB(example1.lineSequence()).shouldBe(70)
        }
    }
}