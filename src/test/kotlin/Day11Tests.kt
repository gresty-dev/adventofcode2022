package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class Day11Tests : FunSpec () {

    private val example1 = """
        Monkey 0:
          Starting items: 79, 98
          Operation: new = old * 19
          Test: divisible by 23
            If true: throw to monkey 2
            If false: throw to monkey 3
        
        Monkey 1:
          Starting items: 54, 65, 75, 74
          Operation: new = old + 6
          Test: divisible by 19
            If true: throw to monkey 2
            If false: throw to monkey 0
        
        Monkey 2:
          Starting items: 79, 60, 97
          Operation: new = old * old
          Test: divisible by 13
            If true: throw to monkey 1
            If false: throw to monkey 3
        
        Monkey 3:
          Starting items: 74
          Operation: new = old + 3
          Test: divisible by 17
            If true: throw to monkey 0
            If false: throw to monkey 1
    """.trimIndent()

    init {
        test("Execute example A") {
            Day11().solveA(example1.lineSequence()).shouldBe(10605)
        }

        test("Execute example B") {
            Day11().solveB(example1.lineSequence()).shouldBe(2713310158L)
        }

        test("Formula parsing 1") {
            val formula = Day11().parseFormula("x + y")
            formula.shouldBeInstanceOf<Day11.OperatorNode>()
            val context = mapOf("x" to 10L, "y" to 3L)
            formula.valid(context).shouldBe(true)
            formula.value(context).shouldBe(13L)
        }

        test("Formula parsing 2") {
            val formula = Day11().parseFormula("x * 22")
            formula.shouldBeInstanceOf<Day11.OperatorNode>()
            val context = mapOf("x" to 10L)
            formula.valid(context).shouldBe(true)
            formula.value(context).shouldBe(220L)
        }
    }
}