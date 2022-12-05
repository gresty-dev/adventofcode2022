package dev.gresty.aoc.adventofcode2022.day05

import dev.gresty.aoc.adventofcode2022.execute

fun main() {
    execute("day05.txt") { solve05A(it) }
    execute("day05.txt") { solve05B(it) }
}

fun solve05A(input: Sequence<String>): String {
    val dock = Dock()
    input.forEach {
        when {
            it.contains('[') -> dock.addStackLevel(it)
            it.contains("move") -> dock.move(it)
            it.contains('1') -> dock.addStackNumbers(it)
        }
    }
    return dock.stackTops()
}

fun solve05B(input: Sequence<String>): String {
    val dock = Dock()
    input.forEach {
        when {
            it.contains('[') -> dock.addStackLevel(it)
            it.contains("move") -> dock.move2(it)
            it.contains('1') -> dock.addStackNumbers(it)
        }
    }
    return dock.stackTops()
}

class Dock {
    private val stacks = mutableMapOf<Int, MutableList<Char>>()

    fun addStackLevel(level: String) {
        for (i in 1..level.length step 4) {
            if (level[i] != ' ') stacks.getOrPut((i + 3) / 4) { mutableListOf() }.add(0, level[i])
        }
    }

    fun addStackNumbers(numbers: String) {
        for (i in 1..numbers.length step 4) {
            stacks.getOrPut((i + 3) / 4) { mutableListOf() }
        }
    }

    fun move(instr: String) {
        val crates = instr.substring(5, 7).trim().toInt()
        val from = instr.substring(12, 14).trim().toInt()
        val to = instr.substring(17).trim().toInt()
        for (i in 1..crates) {
            stacks[to]!!.add(stacks[from]!!.removeLast())
        }
    }

    fun move2(instr: String) {
        val crates = instr.substring(5, 7).trim().toInt()
        val from = instr.substring(12, 14).trim().toInt()
        val to = instr.substring(17).trim().toInt()
        val removalIndex = stacks[from]!!.size - crates
        for (i in 1..crates) {
            stacks[to]!!.add(stacks[from]!!.removeAt(removalIndex))
        }
    }

    fun stackTops() = (1..stacks.size).map { stacks[it]!!.last() }.joinToString(separator = "")
}
