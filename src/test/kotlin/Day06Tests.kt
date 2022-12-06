package dev.gresty.aoc.adventofcode2022

import dev.gresty.aoc.adventofcode2022.day06.solve06A
import dev.gresty.aoc.adventofcode2022.day06.solve06B
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day06Tests : FunSpec () {

    private val example1 = """
        replaceme
    """.trimIndent()

    init {
        test("Execute example A") {
            solve06A("mjqjpqmgbljsphdztnvjfqwrcgsmlb".asSequence()).shouldBe(7)
            solve06A("bvwbjplbgvbhsrlpgdmjqwftvncz".asSequence()).shouldBe(5)
            solve06A("nppdvjthqldpwncqszvftbrmjlhg".asSequence()).shouldBe(6)
            solve06A("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg".asSequence()).shouldBe(10)
            solve06A("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw".asSequence()).shouldBe(11)
        }

        test("Execute example B") {
            solve06B("mjqjpqmgbljsphdztnvjfqwrcgsmlb".asSequence()).shouldBe(19)
            solve06B("bvwbjplbgvbhsrlpgdmjqwftvncz".asSequence()).shouldBe(23)
            solve06B("nppdvjthqldpwncqszvftbrmjlhg".asSequence()).shouldBe(23)
            solve06B("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg".asSequence()).shouldBe(29)
            solve06B("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw".asSequence()).shouldBe(26)
        }
    }
}