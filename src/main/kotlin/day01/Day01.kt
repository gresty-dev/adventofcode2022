package dev.gresty.aoc.adventofcode2022.day01

import dev.gresty.aoc.adventofcode2022.execute

fun main() {
    execute("day01.txt") { solve01A(it) }
    execute("day01.txt") { solve01B(it) }
}

fun solve01A(input: Sequence<String>): Int = solve(input, 1)
fun solve01B(input: Sequence<String>) : Int = solve(input, 3)

private fun solve(input: Sequence<String>, elfCount: Int) : Int {
    val calories = IntArray(elfCount + 1)
    val sortAndReset = {
        calories.sort()
        calories[0] = 0
    }

    input.forEach {
        if (it.isNotEmpty()) calories[0] += it.toInt()
        else sortAndReset()
    }
    sortAndReset()

    return calories.sum()
}
