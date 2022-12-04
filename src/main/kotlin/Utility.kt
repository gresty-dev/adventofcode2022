package dev.gresty.aoc.adventofcode2022

import kotlin.system.measureNanoTime
import kotlin.time.Duration.Companion.nanoseconds

fun read(resourceName: String) : Sequence<String> {
    return object {}.javaClass.getResourceAsStream(resourceName)?.bufferedReader()?.lineSequence()!!
}

fun execute(resourceName: String, task: (Sequence<String>) -> Int) : Long {
    val resource = read(resourceName)
    val result: Int
    val time = measureNanoTime { result = task.invoke(resource) }.nanoseconds.inWholeMicroseconds
    println("$result ($time us)")
    return time
}
