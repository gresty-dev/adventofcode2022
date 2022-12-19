package dev.gresty.aoc.adventofcode2022

/*
 * Part 2 here walks the external surface. A quicker approach is to find the 3D bounding box, expand it a bit, and flood fill it.
 * Then work out the total area of that, subtract the (known) external area of the box, and you are left with the internal
 * area of the box, which is the same as the external area of the lump of lava.
 * Hey ho. I like my solution anyway.
 */
fun main() {
    execute(18) { Day18().solveA(it) }
    execute(18) { Day18().solveB(it) }
}

class Day18 : Day<Int, Int> {
    override fun solveA(input: Sequence<String>): Int {
        val cubes = Cubes()
        var surface = 0
        input.map(::parse).forEach {cube ->
            surface += 6 - 2 * cube.neighbours().count { cubes.contains(it) }
            cubes.add(cube)
        }
        return surface
    }

    override fun solveB(input: Sequence<String>): Int {
        val cubes = mutableSetOf<Cube>()
        var leftmost: Cube? = null
        input.map(::parse).forEach {cube ->
            cubes.add(cube)
            leftmost = leftmost?.let { if (cube.x < it.x) cube else it } ?: cube
        }

        val queue = ArrayDeque<CubeEdge>()
        val visited = mutableSetOf<CubeEdge>()
        queue.add(CubeEdge(leftmost!!, Edge.LEFT))
        while (queue.isNotEmpty()) {
            val next = queue.removeFirst()
            visited.add(next)
            neighbourEdges(next, cubes)
                .filter { !visited.contains(it) }
                .forEach { queue.add(it) }
        }
        return visited.size
    }

    private fun neighbourEdges(edge: CubeEdge, cubes: Set<Cube>) =
        adjacent(edge.edge)
            .map { neighbourEdge(it, edge, cubes) }

    private fun neighbourEdge(direction: Edge, edge: CubeEdge, cubes: Set<Cube>) : CubeEdge {
        val cube1 = edge.cube + direction + edge.edge
        val cube2 = edge.cube + direction
        if (cubes.contains(cube1)) return CubeEdge(cube1, opposite(direction))
        if (cubes.contains(cube2)) return CubeEdge(cube2, edge.edge)
        return CubeEdge(edge.cube, direction)
    }

    private fun parse(input: String) = input.splitToSequence(",").map { it.toInt() }.chunked(3).map { Cube(it[0], it[1], it[2]) }.first()

    class Cubes {
        private val theCubes = mutableSetOf<Cube>()

        fun add(cube: Cube) {
            theCubes.add(cube)
        }

        fun contains(cube: Cube) = theCubes.contains(cube)

     }

    data class Cube(val x: Int, val y: Int, val z: Int) {
        fun neighbours() = listOf(
            Cube(x + 1, y, z),
            Cube(x - 1, y, z),
            Cube(x, y + 1, z),
            Cube(x, y - 1, z),
            Cube(x, y, z + 1),
            Cube(x, y, z -1 )
        )

        operator fun plus(edge: Edge) = Cube(x + edge.dx, y + edge.dy, z + edge.dz)
    }

    data class CubeEdge(val cube: Cube, val edge: Edge)

    enum class Edge(val dx: Int, val dy: Int, val dz: Int) {
        LEFT(-1, 0, 0),
        RIGHT(1, 0, 0),
        BOTTOM(0, -1, 0),
        TOP(0, 1, 0),
        FRONT(0, 0, -1),
        BACK(0, 0, 1);
    } // x: left to right, y: bottom to top, z: front to back

    private fun opposite(edge: Edge) =
        when(edge) {
            Edge.LEFT -> Edge.RIGHT
            Edge.RIGHT -> Edge.LEFT
            Edge.BOTTOM -> Edge.TOP
            Edge.TOP -> Edge.BOTTOM
            Edge.FRONT -> Edge.BACK
            Edge.BACK -> Edge.FRONT
        }

    private fun adjacent(edge: Edge) =
        when(edge) {
            Edge.LEFT -> setOf(Edge.TOP, Edge.FRONT, Edge.BOTTOM, Edge.BACK)
            Edge.RIGHT -> setOf(Edge.BOTTOM, Edge.FRONT, Edge.TOP, Edge.BACK)
            Edge.BOTTOM -> setOf(Edge.BACK, Edge.LEFT, Edge.FRONT, Edge.RIGHT)
            Edge.TOP -> setOf(Edge.FRONT, Edge.LEFT, Edge.BACK, Edge.RIGHT)
            Edge.FRONT -> setOf(Edge.LEFT, Edge.TOP, Edge.RIGHT, Edge.BOTTOM)
            Edge.BACK -> setOf(Edge.LEFT, Edge.BOTTOM, Edge.RIGHT, Edge.TOP)
        }
}