package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe

class Day24Tests : FunSpec () {

    private val example1 = """
        #.######
        #>>.<^<#
        #.<..<<#
        #>v.><>#
        #<^v^^>#
        ######.#
    """.trimIndent()

    private val example2 = """
        #.#####
        #.....#
        #>....#
        #.....#
        #...v.#
        #.....#
        #####.#
    """.trimIndent()

    init {
        test("Execute example A") {
            Day24().solveA(example1.lineSequence()).shouldBe(18)
        }

        test("Execute example B") {
            Day24().solveB(example1.lineSequence()).shouldBe(54)
        }

        test("Valley start and end correctly set") {
            val valley = Day24().load(example2.lineSequence()).updateCombined()
            valley.start shouldBe (0 to -1)
            valley.end shouldBe (4 to 5)
        }

        test("Legal moves from a point") {
            val valley = Day24().load(example2.lineSequence()).updateCombined()
            valley.legalMovesFrom(valley.start) shouldContainExactlyInAnyOrder listOf(valley.start, 0 to 0)
            valley.legalMovesFrom(0 to 0) shouldContainExactlyInAnyOrder listOf(0 to -1, 0 to 0, 1 to 0)
            valley.legalMovesFrom(4 to 0) shouldContainExactlyInAnyOrder listOf(4 to 0, 3 to 0, 4 to 1)
            valley.legalMovesFrom(0 to 4) shouldContainExactlyInAnyOrder listOf(0 to 4, 0 to 3, 1 to 4)
            valley.legalMovesFrom(4 to 4) shouldContainExactlyInAnyOrder listOf(4 to 4, 4 to 3, 3 to 4, valley.end)
        }

        test("WideBitSet get/set") {
            val bitset = Day24.WideBitSet(96)
            bitset[12] = true
            bitset[63] = true
            bitset[92] = true
            bitset[0] shouldBe false
            bitset[11] shouldBe false
            bitset[12] shouldBe true
            bitset[13] shouldBe false
            bitset[62] shouldBe false
            bitset[63] shouldBe true
            bitset[64] shouldBe false
            bitset[91] shouldBe false
            bitset[92] shouldBe true
            bitset[93] shouldBe false
            bitset[95] shouldBe false
        }

        test("WideBitSet shl") {
            val bitset = Day24.WideBitSet(96)
            bitset[12] = true
            bitset[63] = true
            bitset[92] = true

            bitset.shl()
            bitset[0] shouldBe false
            bitset[12] shouldBe false
            bitset[13] shouldBe true
            bitset[14] shouldBe false
            bitset[63] shouldBe false
            bitset[64] shouldBe true
            bitset[65] shouldBe false
            bitset[92] shouldBe false
            bitset[93] shouldBe true
            bitset[94] shouldBe false
            bitset[95] shouldBe false

            repeat(3) { bitset.shl() }
            bitset[0] shouldBe true
            bitset[1] shouldBe false
            bitset[15] shouldBe false
            bitset[16] shouldBe true
            bitset[17] shouldBe false
            bitset[66] shouldBe false
            bitset[67] shouldBe true
            bitset[68] shouldBe false
            bitset[95] shouldBe false
        }

        test("WideBitSet shr") {
            val bitset = Day24.WideBitSet(96)
            bitset[2] = true
            bitset[12] = true
            bitset[63] = true
            bitset[92] = true

            bitset.shr()
            bitset[0] shouldBe false
            bitset[1] shouldBe true
            bitset[2] shouldBe false
            bitset[10] shouldBe false
            bitset[11] shouldBe true
            bitset[12] shouldBe false
            bitset[61] shouldBe false
            bitset[62] shouldBe true
            bitset[63] shouldBe false
            bitset[90] shouldBe false
            bitset[91] shouldBe true
            bitset[92] shouldBe false
            bitset[95] shouldBe false

            repeat(3) { bitset.shr() }
            bitset[0] shouldBe false
            bitset[7] shouldBe false
            bitset[8] shouldBe true
            bitset[9] shouldBe false
            bitset[58] shouldBe false
            bitset[59] shouldBe true
            bitset[60] shouldBe false
            bitset[87] shouldBe false
            bitset[88] shouldBe true
            bitset[89] shouldBe false
            bitset[93] shouldBe false
            bitset[94] shouldBe true
            bitset[95] shouldBe false
        }
    }
}