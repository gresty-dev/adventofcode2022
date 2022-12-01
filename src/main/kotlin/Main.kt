package dev.gresty.aoc.adventofcode2022

import dev.gresty.aoc.adventofcode2022.day01.solve01A
import dev.gresty.aoc.adventofcode2022.day01.solve01B
import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import java.util.concurrent.Callable
import kotlin.system.exitProcess

private val commandsByName = listOf(::solve01A, ::solve01B).associateBy { it.name }

@Command(name = "aoc22",
    mixinStandardHelpOptions = true, // add --help and --version options
    description = ["Runner for Advent of Code 2022"])
class MyApp : Callable<Int> {

    @Parameters(paramLabel = "DAY", description = ["the day"])
    private var day: Int = 1

    override fun call(): Int {
        val dayName = "%02d".format(day)
        val solveA = commandsByName["solve${dayName}A"]
        val solveB = commandsByName["solve${dayName}B"]
        val input = "day${dayName}.txt"

        execute(solveA!!, input)
        execute(solveB!!, input)

        return 0
    }
}

fun main(args: Array<String>) {
    exitProcess(CommandLine(MyApp()).execute(*args))
}