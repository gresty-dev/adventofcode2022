package dev.gresty.aoc.adventofcode2022

fun main() {
    execute(5) { Day05().solveA(it) }
    execute(5) { Day05().solveB(it) }
}

class Day05 : Day<String> {
    override fun solveA(input: Sequence<String>): String {
        return solve(input) { dock, move -> dock.move1(move) }
    }

    override fun solveB(input: Sequence<String>): String {
        return solve(input) { dock, move -> dock.move2(move) }
    }

    fun solve(input: Sequence<String>, moveStrategy: (Dock, String) -> Unit): String {
        val dock = Dock()
        input.forEach {
            when {
                it.contains('[') -> dock.addStackLevel(it)
                it.contains("move") -> moveStrategy(dock, it)
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

        fun move1(instr: String) {
            decodeMove(instr) { crates, from, to ->
                for (i in 1..crates) {
                    to.add(from.removeLast())
                }
            }
        }

        fun move2(instr: String) {
            decodeMove(instr) { crates, from, to ->
                val removalIndex = from.size - crates
                for (i in 1..crates) {
                    to.add(from.removeAt(removalIndex))
                }
            }
        }

        private fun decodeMove(instr: String, action: (Int, MutableList<Char>, MutableList<Char>) -> Unit) {
            val crates = instr.substring(5, 7).trim().toInt()
            val from = instr.substring(12, 14).trim().toInt()
            val to = instr.substring(17).trim().toInt()
            action(crates, stacks[from]!!, stacks[to]!!)
        }

        fun stackTops() = (1..stacks.size).map { stacks[it]!!.last() }.joinToString(separator = "")
    }
}