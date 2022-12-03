package dev.gresty.aoc.adventofcode2022

import dev.gresty.aoc.adventofcode2022.day03.solve03A
import dev.gresty.aoc.adventofcode2022.day03.solve03B
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
            solve03A(example1.lineSequence()).shouldBe(157)
        }

        test("Execute example B") {
            solve03B(example1.lineSequence()).shouldBe(70)
        }
    }
}