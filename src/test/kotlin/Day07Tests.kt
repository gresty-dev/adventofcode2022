package dev.gresty.aoc.adventofcode2022

import dev.gresty.aoc.adventofcode2022.day07.solve07A
import dev.gresty.aoc.adventofcode2022.day07.solve07B
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day07Tests : FunSpec () {

    private val example1 = """
        ${'$'} cd /
        ${'$'} ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        ${'$'} cd a
        ${'$'} ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        ${'$'} cd e
        ${'$'} ls
        584 i
        ${'$'} cd ..
        ${'$'} cd ..
        ${'$'} cd d
        ${'$'} ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent()

    init {
        test("Execute example A") {
            solve07A(example1.lineSequence()).shouldBe(95437L)
        }

        test("Execute example B") {
            solve07B(example1.lineSequence()).shouldBe(0)
        }
    }
}