package dev.gresty.aoc.adventofcode2022.day01

import dev.gresty.aoc.adventofcode2022.execute

fun main() {
    execute(::solve01A, "day01.txt")
    execute(::solve01B, "day01.txt")
}

fun solve01A(input: Sequence<String>): Int {
    return solve(input, 1)
}

fun solve01B(input: Sequence<String>) : Int {
    return solve(input, 3)
}

fun solve(input: Sequence<String>, elfCount: Int) : Int {
    val elfCalories = mutableListOf<Int>()
    var currentCalories = 0

    input.forEach {
        if (it.isNotEmpty()) currentCalories += it.toInt()
        else {
            elfCalories.add(currentCalories)
            currentCalories = 0
        }
    }
    return elfCalories.sortedDescending().take(elfCount).sum()
}