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

    private val decimalToSnafu = """
          Decimal          SNAFU
                1              1
                2              2
                3             1=
                4             1-
                5             10
                6             11
                7             12
                8             2=
                9             2-
               10             20
               15            1=0
               20            1-0
             2022         1=11-2
            12345        1-0---0
        314159265  1121-1110-1=0
    """.trimIndent()

    private val snafuToDecimal = """
         SNAFU  Decimal
        1=-0-2     1747
         12111      906
          2=0=      198
            21       11
          2=01      201
           111       31
         20012     1257
           112       32
         1=-1=      353
          1-12      107
            12        7
            1=        3
           122       37
    """.trimIndent()

    init {
        test("Execute example A") {
            Day25().solveA(example1.lineSequence()).shouldBe("2=-1=0")
        }

        test("Decimal to SNAFU") {
            decimalToSnafu.lineSequence().drop(1)
                .map { it.trim() }
                .map { it.substringBefore(' ').toLong() to it.substringAfterLast(' ') }
                .forEach { Day25.Snafu(it.first).asString shouldBe it.second }
        }

        test("SNAFU to Decimal") {
            snafuToDecimal.lineSequence().drop(1)
                .map { it.trim() }
                .map { it.substringBefore(' ') to it.substringAfterLast(' ').toLong() }
                .forEach { Day25.Snafu(it.first).value shouldBe it.second }
        }
    }
}