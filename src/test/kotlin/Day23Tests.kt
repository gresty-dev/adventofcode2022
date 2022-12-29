package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe

class Day23Tests : FunSpec () {

    private val example1 = """
        ....#..
        ..###.#
        #...#.#
        .#...##
        #.###..
        ##.#.##
        .#..#..
    """.trimIndent()

    private val example2 = """
        .....
        ..##.
        ..#..
        .....
        ..##.
        .....
    """.trimIndent()

    init {
        test("Execute example A") {
            Day23().solveA(example1.lineSequence()).shouldBe(110)
        }

        test("Execute example B") {
            Day23().solveB(example1.lineSequence()).shouldBe(20)
        }

        test("Elves are loaded correctly") {
            val crater = Day23().load(example2.lineSequence())
            crater.elves.size shouldBe 5
            crater.elves shouldContain (2 to 1)
            crater.elves shouldContain (3 to 1)
            crater.elves shouldContain (2 to 2)
            crater.elves shouldContain (2 to 4)
            crater.elves shouldContain (3 to 4)
        }

        test("Elf neighbours are correct") {
            val crater = Day23().load(example2.lineSequence())
            crater.neighbours(2 to 1) shouldBe 20
            crater.neighbours(3 to 1) shouldBe 3
            crater.neighbours(2 to 2) shouldBe 96
            crater.neighbours(2 to 4) shouldBe 16
            crater.neighbours(3 to 4) shouldBe 1
        }

        test("Elf proposals correct") {
            val crater = Day23().load(example2.lineSequence())
            crater.proposalForElf(2 to 1) shouldBe (2 to 0)
        }
    }
}