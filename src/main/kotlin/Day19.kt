package dev.gresty.aoc.adventofcode2022

import dev.gresty.aoc.adventofcode2022.Day19.FactoryTask.*
import java.util.PriorityQueue

fun main() {
    execute(19) { Day19().solveA(it) }
    execute(19) { Day19().solveB(it) }
}

class Day19 : Day<Int, Int> {
    override fun solveA(input: Sequence<String>): Int {
        return 0
    }

    override fun solveB(input: Sequence<String>): Int {
        return 0
    }

    data class Blueprint(val oreForOrbBot: Int,
                         val oreForClaybot: Int,
                         val oreForObsidianbot: Int,
                         val clayForObsidianbot: Int,
                         val oreForGeodeBot: Int,
                         val obsidianForGeodebot: Int)

    private val blueprintRegex = """(\d+)\D+(\d+)\D+(\d+)\D+(\d+)\D+(\d+)\D+(\d+)\D+(\d+)""".toRegex()
    fun parseBlueprint(line: String) : Blueprint {
        val values = blueprintRegex.find(line)!!.groupValues
        return Blueprint(values[2].toInt(), values[3].toInt(), values[4].toInt(), values[5].toInt(), values[6].toInt(), values[7].toInt())
    }

    fun maximiseProduction(factory: Factory) : Int {
        val queue = PriorityQueue<Factory> { a, b -> a.geodes.compareTo(b.geodes) }
        queue.add(factory)
        var best = factory
        while (queue.isNotEmpty()) {
            val f = queue.remove()
            f.options()
                .map { f.execute(it) }
                .forEach {
                    if (it.geodes > best.geodes) {
                        best = it
                        println("Minute ${it.minutes}: Queue size is ${queue.size}, max geodes are ${it.geodes}")
                    }
                    if (it.minutes < 24 &&
                        it.maxPossibleGeodes() > best.geodes &&
                        it.geodes >= best.geodes - 1) {
                        queue.add(it)
                    }
                }
        }
        println("Best: ${best.history}")
        return best.geodes
    }

    class Factory(val blueprint: Blueprint) {
        val maxOreBots = maxOf(blueprint.oreForGeodeBot, blueprint.oreForClaybot, blueprint.oreForObsidianbot, blueprint.oreForOrbBot)
        val maxClayBots = blueprint.clayForObsidianbot
        val maxObsidianBots = blueprint.obsidianForGeodebot

        var orebots = 1
        var claybots = 0
        var obsidianbots = 0
        var geodebots = 0
        var ore = 0
        var clay = 0
        var obsidian = 0
        var geodes = 0
        var minutes = 0
        val history = mutableListOf<FactoryTask>()

        fun options() : List<FactoryTask> {
            val options = mutableListOf<FactoryTask>()
            if (obsidianbots > 0 &&
                ore >= blueprint.oreForGeodeBot &&
                obsidian >= blueprint.obsidianForGeodebot)
                options.add(GEODEBOT)
            if (claybots > 0 &&
                obsidianbots < maxObsidianBots &&
                ore >= blueprint.oreForObsidianbot &&
                clay >= blueprint.clayForObsidianbot)
                options.add(OBSIDIANBOT)
            if (claybots < maxClayBots &&
                ore >= blueprint.oreForClaybot)
                options.add(CLAYBOT)
            if (orebots < maxOreBots &&
                ore >= blueprint.oreForOrbBot)
                options.add(OREBOT)
            if (options.size <= 2 ||
                options[options.size - 1] != WAIT ||
                options[options.size - 2] != WAIT ||
                options[options.size - 3] != WAIT)
                options.add(WAIT)

            return options
        }

        fun execute(task: FactoryTask) : Factory {
            val copy = copy()
            copy.minutes++
            copy.ore += orebots
            copy.clay += claybots
            copy.obsidian += obsidianbots
            copy.geodes += geodebots
            when (task) {
                WAIT -> {}
                OREBOT -> {
                    copy.ore -= blueprint.oreForOrbBot
                    copy.orebots++
                }
                CLAYBOT -> {
                    copy.ore -= blueprint.oreForClaybot
                    copy.claybots++
                }
                OBSIDIANBOT -> {
                    copy.ore -= blueprint.oreForObsidianbot
                    copy.clay -= blueprint.clayForObsidianbot
                    copy.obsidianbots++
                }
                GEODEBOT -> {
                    copy.ore -= blueprint.oreForGeodeBot
                    copy.obsidian -= blueprint.obsidianForGeodebot
                    copy.geodebots++
                }
            }
            copy.history.add(task)
            return copy
        }

        fun maxPossibleGeodes() : Int {
            val time = 24 - minutes
            return geodes + geodebots * time + time * time / 2
        }

        private fun copy() : Factory {
            val copy = Factory(blueprint)
            copy.orebots = orebots
            copy.claybots = claybots
            copy.obsidianbots = obsidianbots
            copy.geodebots = geodebots
            copy.ore = ore
            copy.clay = clay
            copy.obsidian = obsidian
            copy.geodes = geodes
            copy.minutes = minutes
            copy.history.addAll(history)
            return copy
        }
    }

    enum class FactoryTask {
        WAIT, OREBOT, CLAYBOT, OBSIDIANBOT, GEODEBOT
    }
}