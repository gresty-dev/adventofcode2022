package dev.gresty.aoc.adventofcode2022

import dev.gresty.aoc.adventofcode2022.day01.solve01A
import dev.gresty.aoc.adventofcode2022.day01.solve01B
import dev.gresty.aoc.adventofcode2022.day02.solve02A
import dev.gresty.aoc.adventofcode2022.day02.solve02B
import dev.gresty.aoc.adventofcode2022.day03.solve03A
import dev.gresty.aoc.adventofcode2022.day03.solve03B
import dev.gresty.aoc.adventofcode2022.day04.solve04A
import dev.gresty.aoc.adventofcode2022.day04.solve04B
import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import java.util.concurrent.Callable
import kotlin.system.exitProcess

private val commandsByName = listOf(
    ::solve01A, ::solve01B,
    ::solve02A, ::solve02B,
    ::solve03A, ::solve03B,
    ::solve04A, ::solve04B,
).associateBy { it.name }

@Command(name = "aoc22",
    mixinStandardHelpOptions = true, // add --help and --version options
    description = ["Runner for Advent of Code 2022"])
class MyApp : Callable<Int> {

    @Parameters(paramLabel = "DAY", description = ["the day"])
    private var day: Int = 1

    override fun call(): Int {

        if (day == 0) {
            for (d in 1..(commandsByName.size / 2)) execute(d)
        } else {
            execute(day)
        }

        return 0
    }

    fun execute(day: Int) {
        val dayName = "%02d".format(day)
        val solveA = commandsByName["solve${dayName}A"]
        val solveB = commandsByName["solve${dayName}B"]
        val input = "day${dayName}.txt"

        println("Day $day")
        execute(input) { solveA!!(it) }
        execute(input) { solveB!!(it) }
    }
}

fun main(args: Array<String>) {
    exitProcess(CommandLine(MyApp()).execute(*args))
}