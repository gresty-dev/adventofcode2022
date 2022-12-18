package dev.gresty.aoc.adventofcode2022

fun main() {
    execute(16) { Day16().solveA(it) }
    execute(16) { Day16().solveB(it) }
}

class Day16 : Day<Int, Int> {
    private val AA = toChar("AA")
    private var valveCount = 0

    override fun solveA(input: Sequence<String>): Int {
        return runSimulation(input, 30)
    }

    override fun solveB(input: Sequence<String>): Int {
        runSimulation(input, 26)
        return findLargesMatchingDistinctPair()
    }

    private fun runSimulation(input: Sequence<String>, time: Int): Int {
        val valves = input.map { parse(it) }.map { it.name to it }.toMap()
        valveCount = valves.size
        val distancesBetweenFlowValves = shortestDistancesToValvesWithNonZeroFlow(valves)
        val flowValves = valves.filter { it.value.flow > 0 || it.value.name == AA }.toMap()
        val start = valves[AA]!!
        return act(start, flowValves, distancesBetweenFlowValves, State(time, listOf(), 0), setOf())
    }

    private val allVisited = mutableMapOf<String, Int>()

    private fun findLargesMatchingDistinctPair() =
        allVisited.keys
            .flatMap { p1 -> allVisited.keys
                .filter { p2 -> areDistinct(p1, p2) }
                .map { p2 -> p1 to p2 }
        }.maxOf { allVisited[it.first]!! + allVisited[it.second]!! }


    private fun areDistinct(path1: String, path2: String) =
        path1.length + path2.length <= valveCount && path1.none { path2.contains(it) }

    private fun toChar(valveName: String) = ((valveName[0] - 'A') * 26 + (valveName[1] - 'A')).toChar()

    private fun shortestDistancesToValvesWithNonZeroFlow(valves: Map<Char, Valve>) : Map<Pair<Valve, Valve>, Int> {
        val flowValves = valves.values.filter { it.flow > 0 }.toList() + valves[AA]!!
        val flowValveDistances = mutableMapOf<Pair<Valve, Valve>, Int>()

        flowValves.forEach {fromValve ->
            val valveDistances = mutableMapOf<Char, Int>()
            val visited = mutableSetOf<Char>()

            ShortestPath (
                fromValve,
                { _ -> false },
                { v -> v.tunnels.filter { !visited.contains(it) }.map { t -> valves[t]!! }},
                { v -> valveDistances[v.name] ?: Int.MAX_VALUE },
                { v, d -> valveDistances[v.name] = d },
                { v -> visited.add(v.name)}
            ).find()

            flowValveDistances.putAll(
                valveDistances
                    .map { valves[it.key]!! to it.value }
                    .filter { it.first.flow > 0 }
                    .map { (fromValve to it.first) to it.second }
                    .toMap())
        }
        return flowValveDistances
    }

    private fun toNormalisedString(set: Set<Valve>) = set.map { it.name }.sorted().joinToString("")

    private fun act(current: Valve, valves: Map<Char, Valve>, distances: Map<Pair<Valve, Valve>, Int>, state: State, visited: Set<Valve>) : Int {
        var maxPressureReleased = state.pressureReleased
        if (state.canOpen(current)) {
            return maxOf(maxPressureReleased, act(current, valves, distances, state.open(current), visited))
        }

        for (toValve in valves.values) {
            val distance = distances[current to toValve] ?: Int.MAX_VALUE
            if (current != toValve && state.canMoveToAndOpen(distance, toValve)) {
                maxPressureReleased = maxOf(maxPressureReleased, act(toValve, valves, distances, state.tick(distance), visited + toValve))
            }
        }

        val myState = state.tickDown()

        maxPressureReleased = maxOf(maxPressureReleased, myState.pressureReleased)
        allVisited.compute(toNormalisedString(visited)) { _, v -> maxOf(v ?: 0, myState.pressureReleased) }
        return maxPressureReleased
    }

    data class Valve(val name: Char, val flow: Int, val tunnels: String)

    data class State(val timeRemaining: Int, val openValves: List<Valve>, val pressureReleased: Int) {

        fun open(valve: Valve) : State {
            val pressure = openValves.asSequence().map { it.flow }.sum() + pressureReleased
            val newOpenValves = openValves + valve
            return State(timeRemaining - 1, newOpenValves, pressure)
        }

        fun tick(duration: Int) : State {
            val pressure = duration * openValves.asSequence().map { it.flow }.sum() + pressureReleased
            return State(timeRemaining - duration, openValves, pressure)
        }

        fun tickDown() : State {
            val pressure = timeRemaining * openValves.asSequence().map { it.flow }.sum() + pressureReleased
            return State(0, openValves, pressure)
        }

        fun canMoveToAndOpen(distance: Int, valve: Valve) = timeRemaining > distance && valve.flow > 0 && !isOpen(valve)
        fun canOpen(valve: Valve) = timeRemaining > 0 && valve.flow > 0 && !isOpen(valve)
        private fun isOpen(valve: Valve) = openValves.contains(valve)
    }

    private val regex = """Valve (\w\w) has flow rate=(\d+); tunnels? leads? to valves? (.*)""".toRegex()
    private fun parse(line: String) : Valve {
        val values = regex.find(line)!!.groupValues
        val name = toChar(values[1])
        val flow = values[2].toInt()
        val tunnels = values[3].splitToSequence(",").map { toChar(it.trim()) }.joinToString("")
        return Valve(name, flow, tunnels)
    }
}