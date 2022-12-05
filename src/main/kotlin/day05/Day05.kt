package dev.gresty.aoc.adventofcode2022.day05

import dev.gresty.aoc.adventofcode2022.execute

fun main() {
    execute("day05.txt") { solve05A(it) }
    execute("day05.txt") { solve05B(it) }
}

fun solve05A(input: Sequence<String>): String {
    val dock = Dock()
    input.forEach {
        if (it.contains('[')) {
            dock.addStackLevel(it)
        } else if (it.contains("move")) {
            dock.move(it)
        } else if (it.contains('1')) {
            dock.addStackNumbers(it)
        }
    }
    return dock.stackTops()
}

fun solve05B(input: Sequence<String>): Int {
    return 0
}

class Dock {
    val numbersRegex = """(\d+)""".toRegex()
    val stacks = mutableMapOf<Int, MutableList<Char>>()

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

    fun move(crates: Int, from: Int, to: Int) {
        for (i in 1..crates) {
            stacks[to]!!.add(stacks[from]!!.removeLast())
        }
    }

    fun move(instr: String) {
        val params = numbersRegex.findAll(instr).map { it.groupValues.get(1).toInt() }.toList()
        move(params[0], params[1], params[2])
    }

    fun stackTops() = (1..stacks.size).map { stacks[it]!!.last() }.joinToString(separator = "")
}
