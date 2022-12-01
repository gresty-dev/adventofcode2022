package dev.gresty.aoc.adventofcode2022

import kotlin.system.measureNanoTime
import kotlin.time.Duration.Companion.nanoseconds

fun read(resourceName: String) : Sequence<String> {
    return object {}.javaClass.getResourceAsStream(resourceName)?.bufferedReader()?.lineSequence()!!
}

fun execute(task: (Sequence<String>) -> Int, resourceName: String) : Long {
    val resource = read(resourceName)
    val result: Int
    val time = measureNanoTime { result = task.invoke(resource) }.nanoseconds.inWholeMilliseconds
    println("$result - $time ms")
    return time
}