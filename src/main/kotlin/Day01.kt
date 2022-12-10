package dev.gresty.aoc.adventofcode2022

fun main() {
    val today = Day01()
    execute(1) { today.solveA(it) }
    execute(1) { today.solveB(it) }
}

class Day01 : Day<Int, Int> {

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