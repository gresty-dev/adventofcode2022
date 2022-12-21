package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day21Tests : FunSpec () {

    private val example1 = """
        root: pppw + sjmn
        dbpl: 5
        cczh: sllz + lgvd
        zczc: 2
        ptdq: humn - dvpt
        dvpt: 3
        lfqf: 4
        humn: 5
        ljgn: 2
        sjmn: drzm * dbpl
        sllz: 4
        pppw: cczh / lfqf
        lgvd: ljgn * ptdq
        drzm: hmdt - zczc
        hmdt: 32
    """.trimIndent()

    init {
        test("Execute example A") {
            Day21().solveA(example1.lineSequence()).shouldBe(152)
        }

        test("Execute example B") {
            Day21().solveB(example1.lineSequence()).shouldBe(301)
        }
    }
}