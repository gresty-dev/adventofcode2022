package dev.gresty.aoc.adventofcode2022

import dev.gresty.aoc.adventofcode2022.day06.solve06A
import dev.gresty.aoc.adventofcode2022.day06.solve06B
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day06Tests : FunSpec () {

    init {
        test("Execute example A") {
            solve06A("mjqjpqmgbljsphdztnvjfqwrcgsmlb".lineSequence()).shouldBe(7)
            solve06A("bvwbjplbgvbhsrlpgdmjqwftvncz".lineSequence()).shouldBe(5)
            solve06A("nppdvjthqldpwncqszvftbrmjlhg".lineSequence()).shouldBe(6)
            solve06A("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg".lineSequence()).shouldBe(10)
            solve06A("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw".lineSequence()).shouldBe(11)
        }

        test("Execute example B") {
            solve06B("mjqjpqmgbljsphdztnvjfqwrcgsmlb".lineSequence()).shouldBe(19)
            solve06B("bvwbjplbgvbhsrlpgdmjqwftvncz".lineSequence()).shouldBe(23)
            solve06B("nppdvjthqldpwncqszvftbrmjlhg".lineSequence()).shouldBe(23)
            solve06B("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg".lineSequence()).shouldBe(29)
            solve06B("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw".lineSequence()).shouldBe(26)
        }
    }
}