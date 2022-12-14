package dev.gresty.aoc.adventofcode2022

fun main() {
    execute(13) { Day13().solveA(it) }
    execute(13) { Day13().solveB(it) }
}

class Day13 : Day<Int, Int> {

    private val less = -1
    private val equal = 0
    private val greater = 1

    override fun solveA(input: Sequence<String>): Int {
        return input.chunked(3)
            .map { compareList(parsePacket(it[0]), parsePacket(it[1])) }
            .withIndex()
            .filter { it.value != greater }
            .map { it.index + 1 }
            .sum()
    }

    override fun solveB(input: Sequence<String>): Int {
        return (sequenceOf("[[2]]", "[[6]]") + input)
            .filter { it.isNotEmpty() }
            .map { parsePacket(it) }
            .sortedWith(::compareList)
            .withIndex()
            .filter { it.value.isDivider }
            .map { it.index + 1 }
            .reduce { a, b -> a*b }
    }

    private fun compareList(packet1: Packet, packet2: Packet, inList: Boolean = false) : Int {
        var finishedList: Boolean
        var result: Int
        do {
            val s1 = packet1.next()
            val s2 = packet2.next()
            result = when {
                s1 == "[" && s2 == "[" -> compareList(packet1, packet2, true)
                s1 == "]" && s2 != "]" -> less
                s1 != "]" && s2 == "]" -> greater
                s1 == "]" && s2 == "]" -> equal
                s1 == "[" -> compareList(packet1, packetize(s2), true)
                s2 == "[" -> compareList(packetize(s1), packet2, true)
                else -> compareInt(s1.toInt(), s2.toInt())
            }
            finishedList = when (result) {
                less, greater -> true
                equal -> !inList || s1 == "]"
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
        i1 < i2 -> less
        i1 > i2 -> greater
        else -> equal
    }

    private val packetRegex = """(\[|]|\d+)""".toRegex()

    private fun parsePacket(input: String) = Packet(packetRegex.findAll(input).map { it.value }.toList(), input == "[[2]]" || input == "[[6]]" )
    private fun packetize(intVal: String) = Packet(listOf(intVal, "]"))

    class Packet(private val elements: List<String>, val isDivider: Boolean = false) {
        private var index = 0
        fun next() = elements[index++]
        fun reset() {
            index = 0
        }
    }
}