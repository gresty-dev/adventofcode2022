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
    val topElfCalories = IntArray(elfCount)
    var currentCalories = 0

    val addCaloriesToList = {
        if (currentCalories > topElfCalories[0]) {
            topElfCalories[0] = currentCalories
            topElfCalories.sort()
        }
        currentCalories = 0
    }

    input.forEach {
        if (it.isNotEmpty()) currentCalories += it.toInt()
        else addCaloriesToList()
    }
    addCaloriesToList()

    return topElfCalories.sum()
}
