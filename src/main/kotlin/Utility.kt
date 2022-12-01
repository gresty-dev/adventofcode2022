package dev.gresty.aoc.adventofcode2022

fun read(resourceName: String) : Sequence<String> {
    return object {}.javaClass.getResourceAsStream(resourceName)?.bufferedReader()?.lineSequence()!!
}