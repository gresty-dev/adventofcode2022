package dev.gresty.aoc.adventofcode2022

fun main() {
    val today = Day01()
    execute("day01.txt") { today.solveA(it) }
    execute("day01.txt") { today.solveB(it) }
}

class Day01 : Day<Int> {

    override fun solveA(input: Sequence<String>): Int = solve(input, 1)
    override fun solveB(input: Sequence<String>): Int = solve(input, 3)

    private fun solve(input: Sequence<String>, elfCount: Int): Int {
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
}