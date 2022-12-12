package dev.gresty.aoc.adventofcode2022

import kotlin.system.measureNanoTime
import kotlin.time.Duration.Companion.nanoseconds

//private val sessionCookie = "53616c7465645f5f9a1ca60531a1acb0563aada66f028e7353ce654a2d9131278fbd6c564cc3ee01fb8fac31e5857d1fa7e68a8afbc50de649f6cd99c8468658"

typealias IntPair = Pair<Int, Int>
operator fun IntPair.plus(other: IntPair) = IntPair(first + other.first, second + other.second)

fun read(resourceName: String) : Sequence<String> {
    return object {}.javaClass.getResourceAsStream(resourceName)?.bufferedReader()?.lineSequence()!!
}

val cache = mutableMapOf<Int, List<String>>()

fun cache(day: Int) = cache.getOrPut(day) { read(resourceName(day)).toList() }

fun <T> execute(day: Int, task: (Sequence<String>) -> T) : Long {
    val resource = cache(day).asSequence()
    val result: T
    val time = measureNanoTime { result = task.invoke(resource) }.nanoseconds.inWholeMicroseconds
    println("$result ($time 𝜇s)")
    return time
}

fun resourceName(day: Int) = "day%02d.txt".format(day)

//fun cache(day: Int) = cache.getOrPut(day) { download(day).toList() }
//fun download(day: Int) : Sequence<String> {
//    val request = Request(Method.GET, "https://adventofcode.com/2022/day/$day/input")
//        .header("cookie", "session=$sessionCookie")
//    val client: HttpHandler = JavaHttpClient()
//    val response = client(request)
//    return response.bodyString().lineSequence()
//}