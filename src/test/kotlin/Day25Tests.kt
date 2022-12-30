package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day25Tests : FunSpec () {

    private val example1 = """
        1=-0-2
        12111
        2=0=
        21
        2=01
        111
        20012
        112
        1=-1=
        1-12
        12
        1=
        122
    """.trimIndent()

    init {
        test("Execute example A") {
            Day25().solveA(example1.lineSequence()).shouldBe("2=-1=0")
        }

        test("SNAFU string round trip") {
            example1.lineSequence()
                .forEach { Day25.Snafu(it).asString shouldBe it }
        }
    }
}