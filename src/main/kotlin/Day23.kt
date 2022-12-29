package dev.gresty.aoc.adventofcode2022

fun main() {
    execute(23) { Day23().solveA(it) }
    execute(23) { Day23().solveB(it) }
}

class Day23 : Day<Int, Int> {
    override fun solveA(input: Sequence<String>): Int {
        val crater = load(input)
        repeat(10) { crater.doRound() }
        return crater.emptyTilesInMinimumRectangle()
    }

    override fun solveB(input: Sequence<String>): Int {
        val crater = load(input)
        return crater.simulate()
    }

    fun load(input: Sequence<String>) = input.withIndex().fold(Crater()) { crater, row -> crater.addRow(row.index, row.value)}

    class Crater {
        val neighbours = listOf(
            IntPair(-1, -1), IntPair(0, -1), IntPair(1, -1),
            IntPair(1, 0), IntPair(1, 1), IntPair(0, 1), IntPair(-1, 1), IntPair(-1, 0)
        )
        val checks = listOf(
            IntPair(0, -1) to 224,
            IntPair(0, 1) to 14,
            IntPair(-1, 0) to 131,
            IntPair(1, 0) to 56,
        )
        val elves = mutableSetOf<IntPair>()

        var checkNum = 0

        fun addRow(num: Int, content: String): Crater {
            content.asSequence().withIndex().filter { it.value.equals('#') }.forEach { addElf(it.index, num) }
            return this
        }

        fun addElf(x: Int, y: Int) {
            elves.add(IntPair(x, y))
        }

        fun emptyTilesInMinimumRectangle(): Int {
            var xmin = Int.MAX_VALUE
            var ymin = Int.MAX_VALUE
            var xmax = Int.MIN_VALUE
            var ymax = Int.MIN_VALUE
            elves.forEach {
                xmin = minOf(it.first, xmin)
                ymin = minOf(it.second, ymin)
                xmax = maxOf(it.first, xmax)
                ymax = maxOf(it.second, ymax)

            }
            return (xmax - xmin + 1) * (ymax - ymin + 1) - elves.size
        }

        fun doRound(): Int {
            val movements = propose()
            move(movements)
            checkNum++
            return movements.size
        }

        fun simulate(): Int {
            while (doRound() > 0) {}
            return checkNum
        }

        fun propose(): Map<IntPair, IntPair> {
            val proposals = mutableMapOf<IntPair, IntPair>()
            val duplicates = mutableSetOf<IntPair>()
            elves.forEach { elf ->
                proposalForElf(elf)?.let { p ->
                    proposals.put(p, elf)?.let { duplicates.add(p) }
                }
            }
            duplicates.forEach { proposals.remove(it) }
            return proposals
        }

        fun move(movements: Map<IntPair, IntPair>) {
            movements.forEach {
                elves.remove(it.value)
                elves.add(it.key)
            }
        }

        fun proposalForElf(elf: IntPair): IntPair? {
            val n = neighbours(elf)
            if (n == 0) return null
            return elf + (checkNum..checkNum + 3)
                .map { checks[it.mod(4)] }
                .firstOrNull { it.second and n == 0 }
                ?.first
        }

        fun neighbours(elf: IntPair): Int {
            var n = 0
            neighbours.forEach {
                n = n shl 1
                if (elves.contains(elf + it)) n++
            }
            return n
        }
    }
}