package dev.gresty.aoc.adventofcode2022

interface Day<T, U> {
    fun solveA(input: Sequence<String>): T
    fun solveB(input: Sequence<String>): U
}