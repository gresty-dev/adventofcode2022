package dev.gresty.aoc.adventofcode2022

interface Day<T> {
    fun solveA(input: Sequence<String>): T
    fun solveB(input: Sequence<String>): T
}