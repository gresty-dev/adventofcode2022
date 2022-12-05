package dev.gresty.aoc.adventofcode2022

import kotlin.system.measureNanoTime
import kotlin.time.Duration.Companion.nanoseconds

fun read(resourceName: String) : Sequence<String> {
    return object {}.javaClass.getResourceAsStream(resourceName)?.bufferedReader()?.lineSequence()!!
}

fun <T> execute(resourceName: String, task: (Sequence<String>) -> T) : Long {
    val resource = read(resourceName)
    val result: T
    val time = measureNanoTime { result = task.invoke(resource) }.nanoseconds.inWholeMicroseconds
    println("$result ($time us)")
    return time
}
