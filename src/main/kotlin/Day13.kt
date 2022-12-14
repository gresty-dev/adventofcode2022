package dev.gresty.aoc.adventofcode2022

fun main() {
    execute(13) { Day13().solveA(it) }
    execute(13) { Day13().solveB(it) }
}

private const val DIVIDER_1 = "[[2]]"
private const val DIVIDER_2 = "[[6]]"

private const val LESS = -1
private const val EQUAL = 0
private const val GREATER = 1

class Day13 : Day<Int, Int> {

    override fun solveA(input: Sequence<String>) =
        input.chunked(3)
            .map { compareList(parsePacket(it[0]), parsePacket(it[1])) }
            .withIndex()
            .filter { it.value != GREATER }
            .map { it.index + 1 }
            .sum()

    override fun solveB(input: Sequence<String>) =
        input.plus(sequenceOf(DIVIDER_1, DIVIDER_2))
            .filter { it.isNotEmpty() }
            .map { parsePacket(it) }
            .sortedWith(::compareList)
            .withIndex()
            .filter { it.value.isDivider }
            .map { it.index + 1 }
            .reduce { a, b -> a*b }

    private fun compareList(packet1: Packet, packet2: Packet, inList: Boolean = false) : Int {
        var finishedList: Boolean
        var result: Int
        do {
            val s1 = packet1.next()
            val s2 = packet2.next()
            result = when {
                s1 == "[" && s2 == "[" -> compareList(packet1, packet2, true)
                s1 == "]" && s2 != "]" -> LESS
                s1 != "]" && s2 == "]" -> GREATER
                s1 == "]" && s2 == "]" -> EQUAL
                s1 == "[" -> compareList(packet1, packetize(s2), true)
                s2 == "[" -> compareList(packetize(s1), packet2, true)
                else -> compareInt(s1.toInt(), s2.toInt())
            }
            finishedList = when (result) {
                LESS, GREATER -> true
                EQUAL -> !inList || s1 == "]"
                else -> false
            }
        } while (!finishedList)
        if (!inList) {
            packet1.reset()
            packet2.reset()
        }
        return result
    }

    private fun compareInt(i1: Int, i2: Int) = when {
        i1 < i2 -> LESS
        i1 > i2 -> GREATER
        else -> EQUAL
    }

    private val packetRegex = """(\[|]|\d+)""".toRegex()

    private fun parsePacket(input: String) = Packet(packetRegex.findAll(input).map { it.value }.toList(), input == DIVIDER_1 || input == DIVIDER_2)
    private fun packetize(intVal: String) = Packet(listOf(intVal, "]"))

    class Packet(private val elements: List<String>, val isDivider: Boolean = false) {
        private var index = 0
        fun next() = elements[index++]
        fun reset() {
            index = 0
        }
    }
}