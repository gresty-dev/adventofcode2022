package dev.gresty.aoc.adventofcode2022

import kotlin.system.measureNanoTime
import kotlin.time.Duration.Companion.nanoseconds

fun read(resourceName: String) : Sequence<String> {
    return object {}.javaClass.getResourceAsStream(resourceName)?.bufferedReader()?.lineSequence()!!
}

val cache = mutableMapOf<String, List<String>>()

fun cache(name: String) = cache.getOrPut(name) { read(name).toList() }

fun <T> execute(resourceName: String, task: (Sequence<String>) -> T) : Long {
    val resource = cache(resourceName).asSequence()
    val result: T
    val time = measureNanoTime { result = task.invoke(resource) }.nanoseconds.inWholeMicroseconds
    println("$result ($time 𝜇s)")
    return time
}
