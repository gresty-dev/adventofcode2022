package dev.gresty.aoc.adventofcode2022

fun main() {
    execute(25) { Day25().solveA(it) }
}

class Day25 : Day<String, Int> {
    override fun solveA(input: Sequence<String>): String {
        return Snafu(input.map { Snafu(it).value }.sum()).asString
    }

    override fun solveB(input: Sequence<String>): Int {
        return 0
    }

    class Snafu(val value: Long) {
        constructor(snafuDigits: String) : this(fromSnafuDigits(snafuDigits))

        val asString: String
            get() = toSnafu(value).asSequence()
                .map { digits[it + 2] }
                .joinToString("")
                .reversed()

        private fun toSnafu(value: Long) : List<Int> {
            val snafu = mutableListOf(0)
            var i = 0
            var quotient = value
            while (quotient != 0L) {
                addToDigit(snafu, i++, quotient.mod(5))
                quotient /= 5
            }
            return snafu
        }

        private fun addToDigit(snafu: MutableList<Int>, index: Int, addMe: Int) {
            if (snafu.size <= index) snafu.add(0)
            var newVal = snafu[index] + addMe
            var overflow = 0
            if (newVal > 2) {
                newVal -= 5
                overflow += 1
            }
            snafu[index] = newVal
            if (overflow != 0) addToDigit(snafu, index + 1, overflow)
        }

        companion object {
            private const val digits = "=-012"

            private fun fromSnafuDigits(snafuDigits: String) = snafuDigits.asSequence()
                .map { (digits.indexOf(it) - 2).toLong() }
                .reduce { acc, digit -> acc * 5 + digit}
        }
    }
}