package dev.gresty.aoc.adventofcode2022

fun main() {
    execute(17) { Day17().solveA(it) }
    execute(17) { Day17().solveB(it) }
}

class Day17 : Day<Long, Long> {
    override fun solveA(input: Sequence<String>): Long {
        return solve(input, 2022L)
    }

    override fun solveB(input: Sequence<String>): Long {
        return solve(input, 1_000_000_000_000L)
    }

    private fun solve(input: Sequence<String>, blocks: Long): Long {
        val rocks = RockFactory()
        val jet = Jet(input.first())
        val cave = Cave(jet)
        val states = mutableMapOf<CaveState, CycleState>()

        val precycle = CycleState(-1, -1)
        var cycleStart = precycle
        var cycleEnd = precycle
        var index = 0
        while (cycleEnd == precycle) {
            cave.add(rocks.next())
            val caveState = CaveState(rocks.nextIndex(), jet.nextIndex(), cave.profile())
            val cycleState = CycleState(++index, cave.top)
            states[caveState]?.let {
                cycleStart = it
                cycleEnd = cycleState
            }
            states[caveState] = cycleState
        }

        val cycleLength = cycleEnd.index - cycleStart.index
        val (cycles, extras) = divmod(blocks - cycleStart.index, cycleLength)

        val cycleHeight = cycleEnd.height - cycleStart.height
        repeat(extras) { cave.add(rocks.next()) }
        val extrasHeight = cave.top - cycleEnd.height

        return cycleStart.height + cycles * cycleHeight + extrasHeight
    }

    private fun divmod(dividend: Long, divisor: Int) = Pair(dividend / divisor, (dividend % divisor).toInt())

    data class CaveState(val rock: Int, val jet: Int, val profile: List<Int>)
    data class CycleState(val index: Int, val height: Int)
    data class Rock(val shape: List<Int>)

    /**
     * 0: left wall
     * 1-7: space
     * 8: right wall
     * row 0: floor
     */
    class Cave(private val jet: Jet) {
        private val profileRows = 8
        private val width = 7
        private val topGap = 3 + 4
        private val rightWall = 1 shl (width + 1)
        private val cave = mutableListOf(rightWall - 1)
        private var rock = mutableListOf<Int>()

        var top = 0
        var x = 0
        var y = 0

        init { extendCaveSpace() }

        fun add(newRock: Rock) {
            x = 0
            y = top + 4
            rock.clear()
            rock.addAll(newRock.shape)
            repeat(3) { moveRight() }
            do {
                if (jet.next() == '<') moveLeft() else moveRight()
            } while (moveDown())
            embedRock()
            extendCaveSpace()
        }

        fun profile() = (top downTo top - profileRows).map { if (it > 0) cave[it] else 0 }.toList()

        private fun extendCaveSpace() {
            while (cave.size <= top + topGap) cave.add(0)
        }

        private fun embedRock() {
            rock.indices.forEach { i -> cave[y + i] = cave[y + i] or rock[i] }
            top = maxOf(top, y + rock.size - 1)
        }

        private fun moveLeft() {
            if (rock.withIndex().all { it.value % 4 == 0 && (it.value / 2 and cave[y + it.index] == 0) }) {
                rock.indices.forEach { i -> rock[i] /= 2 }
                x--
            }
        }

        private fun moveRight() {
            if (rock.withIndex().all { it.value < (rightWall / 2) && (2 * it.value and cave[y + it.index] == 0) }) {
                rock.indices.forEach { i -> rock[i] *= 2 }
                x++
            }
        }

        private fun moveDown() =
            if (rock.indices.all { i -> (rock[i] and cave[y + i - 1]) == 0 }) {
                y--
                true
            } else false
    }

    class RockFactory {
        private val rocks = listOf(
            Rock(listOf(15)),
            Rock(listOf(2, 7, 2)),
            Rock(listOf(7, 4, 4)),
            Rock(listOf(1, 1, 1, 1)),
            Rock(listOf(3, 3)))
        private var current = 0
        fun next() = rocks[current++ % 5]
        fun nextIndex() = current % 5
    }

    class Jet(private val directions: String) {
        private var current = 0
        fun next() = directions[current++ % directions.length]
        fun nextIndex() = current % directions.length
    }
}