package dev.gresty.aoc.adventofcode2022

fun main() {
    execute(20) { Day20().solveA(it) }
    execute(20) { Day20().solveB(it) }
}

class Day20 : Day<Long, Long> {

    override fun solveA(input: Sequence<String>): Long {
        return solve(input)
    }

    override fun solveB(input: Sequence<String>): Long {
        return solve(input, 811_589_153L, 10)
    }

    private fun solve(input: Sequence<String>, decryptionKey: Long = 1, iterations: Int = 1): Long {
        val ring = input.map { it.toLong() * decryptionKey }
            .fold(IndexedRing()) { ring, value -> ring.add(value) }
            .ringComplete()

        repeat(iterations) {
            ring.forEachByIndex { it.move() }
        }

        val x = ring.zero!!.skip(1000)
        val y = x.skip(1000)
        val z = y.skip(1000)

        return x.value + y.value + z.value
    }

    class IndexedRing {
        private val index = mutableListOf<Node>()
        private var ring: Node? = null
        var zero: Node? = null

        fun add(value: Long) : IndexedRing {
            val node = Node(value)
            index.add(node)
            ring = ring ?.let { node.insertAfter(it) } ?: node
            if (value == 0L) zero = node
            return this
        }

        fun ringComplete() : IndexedRing {
            val modby = index.size - 1
            index.forEach {
                val mod = it.value.mod(modby)
                it.moveBy = if (mod > modby / 2) mod - modby else mod
            }
            return this
        }

        fun forEachByIndex(action: (Node) -> Unit) {
            index.forEach(action)
        }
    }

    class Node(val value: Long) {
        var moveBy = 0
        private var prev: Node = this
        var next: Node = this

        private fun insertBefore(other: Node) : Node {
            val before = other.prev
            next = other
            other.prev = this
            prev = before
            before.next = this
            return this
        }

        fun insertAfter(other: Node) : Node {
            val after = other.next
            prev = other
            other.next = this
            next = after
            after.prev = this
            return this
        }

        fun move() {
            if (moveBy == 0) return
            remove()
            val target = skip(moveBy)
            if (moveBy > 0) {
                insertAfter(target)
            } else {
                insertBefore(target)
            }
        }

        private fun remove() {
            prev.next = next
            next.prev = prev
        }

        fun skip(distance: Int) : Node {
            var node = this
            if (distance > 0) {
                repeat(distance) { node = node.next }
            } else if (distance < 0) {
                repeat(-distance) { node = node.prev }
            }
            return node
        }
    }
}