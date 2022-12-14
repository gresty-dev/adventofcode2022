package dev.gresty.aoc.adventofcode2022

fun main() {
    execute(13) { Day13().solveA(it) }
    execute(13) { Day13().solveB(it) }
}

class Day13 : Day<Int, Int> {

    private val LESS = -1
    private val EQUAL = 0
    private val GREATER = 1

    override fun solveA(input: Sequence<String>): Int {
        return input.chunked(3)
            .map { compareList(parsePacket(it[0]), parsePacket(it[1])) }
            .withIndex()
            .onEach { println("${it.index + 1}: ${it.value}") }
            .filter { it.value != GREATER }
            .map { it.index + 1 }
            .sum()
    }

    override fun solveB(input: Sequence<String>): Int {
        return 0
    }

    fun compareList(packet1: Packet, packet2: Packet, inList: Boolean = false) : Int {
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
        return result
    }

    fun compareInt(i1: Int, i2: Int) = when {
        i1 < i2 -> LESS
        i1 > i2 -> GREATER
        else -> EQUAL
    }

    val packetRegex = """(\[|]|\d+)""".toRegex()

    fun parsePacket(input: String) = Packet(packetRegex.findAll(input).map { it.value }.toList())
    fun packetize(intVal: String) = Packet(listOf(intVal, "]"))

    class Packet(val elements: List<String>) {
        var index = 0
        fun next() = elements[index++]
    }
}