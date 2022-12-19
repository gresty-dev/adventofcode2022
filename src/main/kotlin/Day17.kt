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
        return solve(input, 1000000000000L)
    }

    fun solve(input: Sequence<String>, blocks: Long): Long {
        val rocks = RockFactory()
        val jet = Jet(input.first())
        val cave = Cave(7, jet)
        val states = mutableMapOf(CycleState(0, 0, cave.profile(8)) to (0 to 0))
        var repeatStart: Pair<Int, Int>? = null
        var repeatEnd: Pair<Int, Int>? = null
        var index = 0
        do {
            index++
            cave.add(rocks.next())
            val newState = CycleState(rocks.nextIndex(), jet.nextIndex(), cave.profile(8))
            states[newState]?.let {
                repeatStart = it
                repeatEnd = index to cave.top
            }
            states[newState] = index to cave.top
        } while (repeatEnd == null)

        val cycleStart = repeatStart!!.first
        val cycleLength = repeatEnd!!.first - repeatStart!!.first

        val cycles = (blocks - cycleStart) / cycleLength
        val extras = ((blocks - cycleStart) % cycleLength).toInt()

        val preludeHeight = repeatStart!!.second
        val cycleHeight = repeatEnd!!.second - repeatStart!!.second
        repeat(extras) { cave.add(rocks.next()) }
        val extrasHeight = cave.top - repeatEnd!!.second

        return preludeHeight + cycles * cycleHeight + extrasHeight
    }

    data class CycleState(val rock: Int, val jet: Int, val profile: List<Int>)

    /**
     * 0: left wall
     * 1-7: space
     * 8: right wall
     * row 0: floor
     */
    class Cave(width: Int, val jet: Jet) {
        val topGap = 3 + 4
        val rightWall = 1 shl (width + 1)
        val cave = mutableListOf(rightWall - 1, 0, 0, 0, 0, 0, 0, 0)

        var rock = mutableListOf<Int>()
        var top = 0
        var x = 0
        var y = 0

        fun add(newRock: Rock) : Int {
            x = 0
            y = top + 4
            rock.clear()
            rock.addAll(newRock.shape)
            moveRight()
            moveRight()
            moveRight()
            do {
                if (jet.next() == '<') moveLeft() else moveRight()
            } while (moveDown())
            rock.indices.forEach { i -> cave[y + i] = cave[y + i] or rock[i] }
            top = maxOf(top, y + rock.size - 1)
            while (cave.size <= top + topGap) cave.add(0)
            return top
        }

        fun profile(rows: Int) = (top downTo top - 8).map { if (it > 0) cave[it] else 0 }.toList()

        fun moveLeft() =
            if (rock.withIndex().all { it.value % 4 == 0 && (it.value / 2 and cave[y + it.index] == 0) }) {
                rock.indices.forEach { i -> rock[i] /= 2 }
                x--
                true
            } else false


        fun moveRight() =
            if (rock.withIndex().all { it.value < (rightWall / 2) && (2 * it.value and cave[y + it.index] == 0) }) {
                rock.indices.forEach { i -> rock[i] *= 2 }
                x++
                true
            } else false


        fun moveDown() =
            if (rock.indices.all { i -> (rock[i] and cave[y + i - 1]) == 0 }) {
                y--
                true
            } else false
    }

    data class Rock(val shape: List<Int>)

    class RockFactory {
        val rocks = listOf(
            Rock(listOf(15)),
            Rock(listOf(2, 7, 2)),
            Rock(listOf(7, 4, 4)),
            Rock(listOf(1, 1, 1, 1)),
            Rock(listOf(3, 3)))
        var current = 0
        fun next() = rocks[current++ % 5]
        fun nextIndex() = current % 5
    }

    class Jet(val directions: String) {
        var current = 0
        fun next() = directions[current++ % directions.length]
        fun nextIndex() = current % directions.length
    }
}