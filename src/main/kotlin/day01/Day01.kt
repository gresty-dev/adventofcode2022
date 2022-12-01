package dev.gresty.aoc.adventofcode2022.day01

import dev.gresty.aoc.adventofcode2022.read

fun main() {
    println(solveA(read("day01.txt")))
    println(solveB(read("day01.txt")))
}

fun solveA(input: Sequence<String>): Int {
    return solve(input, 1)
}

fun solveB(input: Sequence<String>) : Int {
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