package dev.gresty.aoc.adventofcode2022

fun main() {
    execute(25) { Day25().solveA(it) }
}

class Day25 : Day<String, Int> {
    override fun solveA(input: Sequence<String>): String {
        return input.map { Snafu(it) }.reduce { acc, snafu -> acc.add(snafu) }.asString
    }

    override fun solveB(input: Sequence<String>): Int {
        return 0
    }

    class Snafu(asDigits: List<Int>) {
        constructor(asString: String) : this(fromString(asString))

        private val snafu = asDigits

        val asString: String
            get() = snafu.reversed().asSequence()
                .map { digits[it + 2] }
                .joinToString("")

        fun add(other: Snafu) : Snafu {
            val result = snafu.toMutableList()
            other.snafu.withIndex().forEach { addToDigit(result, it.index, it.value) }
            return Snafu(result)
        }

        private fun addToDigit(snafu: MutableList<Int>, index: Int, addMe: Int) {
            if (snafu.size <= index) snafu.add(0)
            var newVal = snafu[index] + addMe
            var overflow = 0
            if (newVal > 2) {
                newVal -= 5
                overflow += 1
            } else if (newVal < -2) {
                newVal += 5
                overflow -= 1
            }
            snafu[index] = newVal
            if (overflow != 0) addToDigit(snafu, index + 1, overflow)
        }

        companion object {
            private const val digits = "=-012"

            private fun fromString(snafuDigits: String) = snafuDigits.asSequence()
                .map { (digits.indexOf(it) - 2) }
                .toList()
                .reversed()

        }
    }
}