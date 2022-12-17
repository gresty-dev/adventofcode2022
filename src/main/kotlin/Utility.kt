package dev.gresty.aoc.adventofcode2022

import kotlin.math.absoluteValue
import kotlin.math.sign
import kotlin.system.measureNanoTime
import kotlin.time.Duration.Companion.nanoseconds

//private val sessionCookie = "53616c7465645f5f9a1ca60531a1acb0563aada66f028e7353ce654a2d9131278fbd6c564cc3ee01fb8fac31e5857d1fa7e68a8afbc50de649f6cd99c8468658"

typealias IntPair = Pair<Int, Int>
operator fun IntPair.plus(other: IntPair) = IntPair(first + other.first, second + other.second)
operator fun IntPair.minus(other: IntPair) = IntPair(first - other.first, second - other.second)
operator fun IntPair.div(other: IntPair) = maxOf(first / other.first, second / other.second)
fun IntPair.sign() = IntPair(first.sign, second.sign)
fun IntPair.manhattan(other: IntPair) = (first - other.first).absoluteValue + (second - other.second).absoluteValue
fun intPairOf(string: String) = IntPair(string.substringBefore(",").toInt(), string.substringAfter(",").toInt());

operator fun IntRange.plus(other: IntRange?) = other?.let { minOf(first, other.first)..maxOf(last, other.last) } ?: this
fun IntRange.intersects(other: IntRange?) = other?.let { !(last < other.first || start > other.last) } ?: false
fun IntRange.intersect(other: IntRange?) = other?.let {
    if (first > other.last || last < other.first) null
    else maxOf(first, other.first)..minOf(last, other.last)
} ?: null

//internal class IntPairIterator(start: IntPair, val endInclusive: IntPair) : Iterator<IntPair> {
//    val step = (endInclusive - start).sign()
//    var current = start
//
//    override fun hasNext(): Boolean {
//        current != endInclusive
//    }
//
//    override fun next(): IntPair {
//        val next = current
//        current += step
//        return next
//    }
//}

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