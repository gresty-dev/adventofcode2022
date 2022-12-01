package dev.gresty.aoc.adventofcode2022.day01

import dev.gresty.aoc.adventofcode2022.execute

fun main() {
    var time = 0L
    time += execute(::solve01A, "day01.txt")
    time += execute(::solve01B, "day01.txt")
    println("Total time: $time ms")

}