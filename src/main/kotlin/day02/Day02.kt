package dev.gresty.aoc.adventofcode2022.day02

import dev.gresty.aoc.adventofcode2022.execute

fun main() {
    execute("day02.txt") { solve02A(it) }
    execute("day02.txt") { solve02B(it) }
}

fun solve02A(input: Sequence<String>) : Int {
    return input.map { handOf(it[0]).plays(handOf(it[2])) }.sum()
}

fun solve02B(input: Sequence<String>) : Int {
    return input.map { handOf(it[0]).playsForOutcome(it[2]) }.sum()
}

enum class Hand {
    ROCK, PAPER, SCISSORS;

    private fun losesToMe() = values()[(ordinal + 2) % 3] // +2 instead of -1 avoids dealing with negative modulus things
    private fun beatsMe() = values()[(ordinal + 1) % 3]
    private fun score() = ordinal + 1

    fun plays(other: Hand) = other.score() +
            when(other) {
                beatsMe() -> 6
                this -> 3
                else -> 0
            }

    fun playsForOutcome(outcome: Char) =
        plays(when (outcome) {
            'X' ->  losesToMe()
            'Y' ->  this
            else -> beatsMe()
        })
}

fun handOf(symbol: Char) = when (symbol) {
    'A', 'X' -> Hand.ROCK
    'B', 'Y' -> Hand.PAPER
    else -> Hand.SCISSORS
}