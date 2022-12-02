package dev.gresty.aoc.adventofcode2022.day02

import dev.gresty.aoc.adventofcode2022.execute

fun main() {
    execute(::solve02A, "day02.txt")
    execute(::solve02B, "day02.txt")
}

fun solve02A(input: Sequence<String>) : Int {
    return input.map { move(it[2]).plays(move(it[0])) }.sum()
}

fun solve02B(input: Sequence<String>) : Int {
    return input.map { move(it[0]).resultsIn(it[2]) }.sum()
}

sealed class Move(private val score: Int) {
    abstract val beats: Move

    object ROCK: Move(1) {
        override val beats = SCISSORS
    }

    object PAPER: Move(2) {
        override val beats = ROCK
    }

    object SCISSORS: Move(3) {
        override val beats = PAPER
    }

    fun plays(other: Move) : Int {
        return score + if (beats == other) {
            6
        } else if (this == other) {
            3
        } else {
            0
        }
    }

    fun resultsIn(outcome: Char) : Int {
        return when (outcome) {
            'X' -> 0 + beats.score // lose
            'Y' -> 3 + score // draw
            else -> 6 + (score % 3 + 1)  // win
        }
    }
}

fun move(symbol: Char) : Move {
    return when (symbol) {
        'A', 'X' -> Move.ROCK
        'B', 'Y' -> Move.PAPER
        else -> Move.SCISSORS
    }
}