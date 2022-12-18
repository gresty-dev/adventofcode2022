package dev.gresty.aoc.adventofcode2022

fun main() {
    execute(16) { Day16().solveA(it) }
    execute(16) { Day16().solveB(it) }
}

class Day16 : Day<Int, Int> {
    override fun solveA(input: Sequence<String>): Int {
        val valves = input.map { parse(it) }.map { it.name to it }.toMap()
        val distancesBetweenFlowValves = shortestDistancesToValvesWithNonZeroFlow(valves)
        val flowValves = valves.filter { it.value.flow > 0 || it.value.name == "AA" }.toMap()

        return act(valves["AA"]!!, flowValves, distancesBetweenFlowValves, State(30, listOf(), 0), "AA")
    }

    override fun solveB(input: Sequence<String>): Int {
        return 0
    }

    fun shortestDistancesToValvesWithNonZeroFlow(valves: Map<String, Valve>) : Map<Pair<Valve, Valve>, Int> {
        val flowValves = valves.values.filter { it.flow > 0 }.toList() + valves["AA"]!!
        val flowValveDistances = mutableMapOf<Pair<Valve, Valve>, Int>()

        flowValves.forEach {fromValve ->
            val valveDistances = mutableMapOf<Valve, Int>()
            val visited = mutableMapOf<Valve, Boolean>()

            ShortestPath (
                fromValve,
                { _ -> false },
                { v -> v.tunnels.map { t -> valves[t]!! }.filter { !visited.contains(it) }},
                { v -> valveDistances[v] ?: Int.MAX_VALUE },
                { v, d -> valveDistances[v] = d },
                { v -> visited[v] = true}
            ).find()

            flowValveDistances.putAll(
                valveDistances
                    .filter { it.key.flow > 0 }
                    .map { (fromValve to it.key) to it.value }
                    .toMap())
        }
        return flowValveDistances
    }

    fun act(current: Valve, valves: Map<String, Valve>, distances: Map<Pair<Valve, Valve>, Int>, state: State, trace: String) : Int {
        var maxPressureReleased = state.pressureReleased
        if (state.canOpen(current)) {
            return maxOf(maxPressureReleased, act(current, valves, distances, state.open(current), "$trace open(${current.flow})"))
        }

        for (toValve in valves.values) {
            val distance = distances[current to toValve] ?: Int.MAX_VALUE
            if (current != toValve && state.canMoveToAndOpen(distance, toValve)) {
                maxPressureReleased = maxOf(maxPressureReleased, act(toValve, valves, distances, state.tick(distance), "$trace ${toValve.name}(${distance})"))
            }
        }

        var remaining = state.timeRemaining
        var myState = state.tickDown()

        maxPressureReleased = maxOf(maxPressureReleased, myState.pressureReleased)
//        println("$trace wait($remaining) ${myState.pressureReleased} max:${maxPressureReleased}")
        return maxPressureReleased
    }

    val regex = """Valve (\w\w) has flow rate=(\d+); tunnels? leads? to valves? (.*)""".toRegex()
    fun parse(line: String) : Valve {
        val values = regex.find(line)!!.groupValues
        val name = values[1]
        val flow = values[2].toInt()
        val tunnels = values[3].splitToSequence(",").map { it.trim() }.toList()
        return Valve(name, flow, tunnels)
    }

    data class Valve(val name: String, val flow: Int, val tunnels: List<String>) {
    }

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
        fun canMove() = timeRemaining > 0
        fun canWait() = timeRemaining > 0
        fun isOpen(valve: Valve) = openValves.contains(valve)
    }
}