package dev.gresty.aoc.adventofcode2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day06Tests : FunSpec () {

    init {
        test("Execute example A") {
            Day06().solveA("mjqjpqmgbljsphdztnvjfqwrcgsmlb".lineSequence()).shouldBe(7)
            Day06().solveA("bvwbjplbgvbhsrlpgdmjqwftvncz".lineSequence()).shouldBe(5)
            Day06().solveA("nppdvjthqldpwncqszvftbrmjlhg".lineSequence()).shouldBe(6)
            Day06().solveA("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg".lineSequence()).shouldBe(10)
            Day06().solveA("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw".lineSequence()).shouldBe(11)
        }

        test("Execute example B") {
            Day06().solveB("mjqjpqmgbljsphdztnvjfqwrcgsmlb".lineSequence()).shouldBe(19)
            Day06().solveB("bvwbjplbgvbhsrlpgdmjqwftvncz".lineSequence()).shouldBe(23)
            Day06().solveB("nppdvjthqldpwncqszvftbrmjlhg".lineSequence()).shouldBe(23)
            Day06().solveB("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg".lineSequence()).shouldBe(29)
            Day06().solveB("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw".lineSequence()).shouldBe(26)
        }
    }
}