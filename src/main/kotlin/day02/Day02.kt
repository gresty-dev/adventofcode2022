package dev.gresty.aoc.adventofcode2022.day02

import dev.gresty.aoc.adventofcode2022.execute

fun main() {
    execute(::solve02A, "day02.txt")
    execute(::solve02B, "day02.txt")
}

fun solve02A(input: Sequence<String>) : Int {
    return input.map { handOf(it[2]).plays(handOf(it[0])) }.sum()
}

fun solve02B(input: Sequence<String>) : Int {
    return input.map { handOf(it[0]).playsForOutcome(it[2]) }.sum()
}

enum class Hand {
    ROCK, PAPER, SCISSORS;

    private fun beats() = values()[(ordinal + 2) % 3] // +2 instead of -1 avoids dealing with negative modulus things
    private fun isBeatenBy() = values()[(ordinal + 1) % 3]
    private fun score() = ordinal + 1

    fun plays(other: Hand) = score() +
            when(other) {
                beats() -> 6
                this -> 3
                else -> 0
            }

    fun playsForOutcome(outcome: Char) =
        when (outcome) {
            'X' ->  beats()
            'Y' ->  this
            else -> isBeatenBy()
        }.plays(this)
}

fun handOf(symbol: Char) = when (symbol) {
    'A', 'X' -> Hand.ROCK
    'B', 'Y' -> Hand.PAPER
    else -> Hand.SCISSORS
}