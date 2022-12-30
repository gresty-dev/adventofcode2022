package dev.gresty.aoc.adventofcode2022

import java.lang.RuntimeException

fun main() {
    execute(24) { Day24().solveA(it) }
    execute(24) { Day24().solveB(it) }
}

class Day24 : Day<Int, Int> {
    override fun solveA(input: Sequence<String>): Int {
        val valley = load(input)
        return search(valley, valley.start, valley.end)!!.step
    }

    override fun solveB(input: Sequence<String>): Int {
        val valley = load(input)
        var total = search(valley, valley.start, valley.end)!!.step
        total += search(valley, valley.end, valley.start)!!.step
        total += search(valley, valley.start, valley.end)!!.step
        return total
    }

    fun load(input: Sequence<String>) = input.fold(Valley()) { v, r -> v.addRow(r) }

    private fun search(valley: Valley, start: IntPair, end: IntPair) : Move? {
        val queue = ArrayDeque<Move>()
        queue.add(Move(0, start))
        var step = -1
        while (queue.isNotEmpty()) {
            val move = queue.removeFirst()
            if (move.step > step) {
                valley.advanceBlizzard()
                step++
            }
            valley.legalMovesFrom(move.position).forEach {
                val nextMove = Move(move.step + 1, it)
                if (it == end)
                    return nextMove
                else if (!queue.contains(nextMove))
                    queue.add(nextMove)
            }
        }
        return null
    }

    data class Move(val step: Int, val position: IntPair)

    class Valley {
        private val blizzards = listOf(Blizzard(Direction.UP), Blizzard(Direction.DOWN), Blizzard(Direction.LEFT), Blizzard(Direction.RIGHT))
        private val height: Int
            get() = blizzards[0].height
        private val width: Int
            get() = blizzards[0].width

        val start = 0 to -1
        var end = 0 to 0


        fun addRow(row: String) : Valley {
            val trimmed = row.substring(1 until row.length - 1)
            if (trimmed.contains("##")) {
                if (trimmed[0] != '.') {
                    end = trimmed.indexOf('.') to height
                }
            } else {
                blizzards.forEach { it.addRow(trimmed) }
            }
            return this
        }

        fun advanceBlizzard() {
            blizzards.forEach { it.move() }
        }

        fun legalMovesFrom(position: IntPair) =
            Movement.values().map { position + it.m }
                .filter { it == start || it == end ||
                        (it.first in 0 until width &&
                                it.second in 0 until height)}
                .filter { !blizzardAt(it) }
                .toList()

        private fun blizzardAt(position: IntPair) =
            if (position.second == -1 || position.second == height) false
            else {
                blizzards[0].blizzardAt(position) ||
                        blizzards[1].blizzardAt(position) ||
                        blizzards[2].blizzardAt(position) ||
                        blizzards[3].blizzardAt(position)
            }
    }

    class Blizzard(private val direction: Direction) {
        private val grid = mutableListOf<WideBitSet>()
        val height: Int
            get() = grid.size
        val width: Int
            get() = grid[0].length

        fun addRow(row: String) {
            val bitset = WideBitSet(row.length)
            row.withIndex().forEach {
                if (it.value == direction.char) {
                    bitset[it.index] = true
                }
            }
            grid.add(bitset)
        }

        fun blizzardAt(position: IntPair) = grid[position.second][position.first]

        fun move() =
            when(direction) {
                Direction.UP -> moveUp()
                Direction.DOWN -> moveDown()
                Direction.LEFT -> moveLeft()
                Direction.RIGHT -> moveRight()
            }

        private fun moveUp() {
            grid.add(grid.removeFirst())
        }

        private fun moveDown() {
            grid.add(0, grid.removeLast())
        }

        private fun moveLeft() {
            for (bitSet in grid) {
                bitSet.shr()
            }
        }

        private fun moveRight() {
            for (bitSet in grid) {
                bitSet.shl()
            }
        }
    }

    enum class Direction(val char: Char) {
        UP('^'),
        DOWN('v'),
        LEFT('<'),
        RIGHT('>');
    }

    enum class Movement(val m: IntPair) {
        STAY(0 to 0),
        UP(0 to -1),
        DOWN(0 to 1),
        LEFT(-1 to 0),
        RIGHT(1 to 0);
    }

    class WideBitSet(val length: Int) {
        private val useUpper = length > 64
        private val upperlen = if (useUpper) length - 64 else 0
        private val lowerlen = if (useUpper) 64 else length
        private val uppermsb = 1UL shl (if (useUpper) upperlen - 1 else 0)
        private val lowermsb = 1UL shl (lowerlen - 1)
        private val uppermask = if (useUpper) ((uppermsb - 1UL) shl 1) + 1UL else 0UL
        private val lowermask = if (useUpper) ULong.MAX_VALUE else ((lowermsb - 1UL) shl 1) + 1UL
        private val lsb = 1UL
        private val zero = 0UL
        private var upper = 0UL
        private var lower = 0UL

        operator fun set(index: Int, state: Boolean) {
            if (index !in 0 until length) throw RuntimeException("Index out of bounds: $index")
            if (index < 64) {
                lower = setULongWithMask(lower, 1UL shl index, state)
            } else {
                upper = setULongWithMask(upper, 1UL shl (index - 64), state)
            }
        }

        private fun setULongWithMask(bits: ULong, mask: ULong, state: Boolean) =
            if (state) bits or mask else bits or invertULong(mask) and mask

        private fun invertULong(bits: ULong) : ULong {
            return ULong.MAX_VALUE - bits
        }

        operator fun get(index: Int) : Boolean {
            if (index !in 0 until length) throw RuntimeException("Index out of bounds: $index")
            return if (index < 64) {
                lower and (1UL shl index) > 0UL
            } else {
                upper and (1UL shl (index - 64)) > 0UL
            }
        }

        fun or(other: WideBitSet) : WideBitSet {
            val result = WideBitSet(length)
            result.upper = upper or other.upper
            result.lower = lower or other.lower
            return result
        }

        fun shl() {
            val carryUpper = upper and uppermsb > 0UL
            val carryLower = lower and lowermsb > 0UL
            if (useUpper) {
                upper = ((upper shl 1) + if (carryLower) lsb else zero) and uppermask
                lower = (lower shl 1) + if (carryUpper) lsb else zero
            } else {
                lower = ((lower shl 1) + if (carryLower) lsb else zero) and lowermask
            }
        }

        fun shr() {
            val carryUpper = upper and lsb > 0UL
            val carryLower = lower and lsb > 0UL
            if (useUpper) {
                upper = (upper shr 1) + if (carryLower) uppermsb else zero
                lower = (lower shr 1) + if (carryUpper) lowermsb else zero
            } else {
                lower = (lower shr 1) + if (carryLower) lowermsb else zero
            }
        }
    }

}