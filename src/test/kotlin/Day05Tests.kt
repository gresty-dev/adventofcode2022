package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day05Tests : FunSpec () {

    private val example1 = """
            [D]    
        [N] [C]    
        [Z] [M] [P]
         1   2   3 
        
        move 1 from 2 to 1
        move 3 from 1 to 3
        move 2 from 2 to 1
        move 1 from 1 to 2        
    """.trimIndent()

    init {
        test("Execute example A") {
            Day05().solveA(example1.lineSequence()).shouldBe("CMZ")
        }

        test("Execute example B") {
            Day05().solveB(example1.lineSequence()).shouldBe("MCD")
        }
    }
}