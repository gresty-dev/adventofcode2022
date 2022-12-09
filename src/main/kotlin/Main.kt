package dev.gresty.aoc.adventofcode2022

import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import java.util.concurrent.Callable
import kotlin.system.exitProcess

private val solutions = listOf<Day<*>>(
    Day01(), Day02(), Day03(), Day04(), Day05(), Day06(), Day07(), Day08(), Day09()
)

@Command(name = "aoc22",
    mixinStandardHelpOptions = true, // add --help and --version options
    description = ["Runner for Advent of Code 2022"])
class MyApp : Callable<Int> {

    @Parameters(paramLabel = "DAY", description = ["the day"])
    private var day: Int = 1

    override fun call(): Int {

        if (day == 0) {
            val totalTime = (1..solutions.size).asSequence().map { execute(it) }.onEach { println() }.sum()
            println("Total time: $totalTime 𝜇s")

        } else {
            execute(day)
        }

        return 0
    }

    fun execute(dayNumber: Int) : Long {
        val dayName = "%02d".format(dayNumber)
        val day = solutions[dayNumber - 1]
        val input = "day${dayName}.txt"

        println("Day $dayName")
        return execute(input) { day.solveA(it) } + execute(input) { day.solveB(it) }
    }
}

fun main(args: Array<String>) {
    exitProcess(CommandLine(MyApp()).execute(*args))
}