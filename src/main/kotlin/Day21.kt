package dev.gresty.aoc.adventofcode2022

fun main() {
    execute(21) { Day21().solveA(it) }
    execute(21) { Day21().solveB(it) }
}

class Day21 : Day<Long, Long> {
    override fun solveA(input: Sequence<String>): Long {
        val jobs = input.map { parse(it) }.toMap()
        return jobs["root"]!!.value(jobs)
    }

    override fun solveB(input: Sequence<String>): Long {
        val jobs = input.map { parse(it) }.toMap()
        var triggered = false
        (jobs["humn"] as MutableJob).valueSupplier = { triggered = true ; 0L}
        val temp = jobs[(jobs["root"] as OperationJob).monkey1]!!.value(jobs)
        val targetValue: Long
        val monkey: String
        if (triggered) {
            targetValue = jobs[(jobs["root"] as OperationJob).monkey2]!!.value(jobs)
            monkey = (jobs["root"] as OperationJob).monkey1
        } else {
            targetValue = temp
            monkey = (jobs["root"] as OperationJob).monkey2
        }



        return 0
    }

    fun parse(line: String) : Pair<String, Job> =
        if (line.startsWith("humn")) {
            line.substring(0, 4) to MutableJob { line.substring(6).toLong() }
        } else if (line.length == 17) {
            line.substring(0, 4) to OperationJob(line.substring(6, 10), line.substring(13), operation(line[11]))
        } else {
            line.substring(0, 4) to LiteralJob(line.substring(6).toLong())
        }

    interface Job {
        fun value(context: Map<String, Job>) : Long
    }

    class LiteralJob(private val value: Long) : Job {
        override fun value(context: MonkeyJobs) = value
    }

    class MutableJob(var valueSupplier: () -> Long) : Job {
        override fun value(context: MonkeyJobs) = valueSupplier.invoke()
    }

    class OperationJob(val monkey1: String, val monkey2: String, val operation: (Long, Long) -> Long) : Job {
        override fun value(context: MonkeyJobs) = operation(context[monkey1]!!.value(context), context[monkey2]!!.value(context))
    }

    fun operation(op: Char) : (Long, Long) -> Long {
        return when (op) {
            '+' -> { a, b -> a + b }
            '-' -> { a, b -> a - b }
            '/' -> { a, b -> a / b }
            '*' -> { a, b -> a * b }
            else -> throw UnsupportedOperationException("Unsupported operation: $op")
        }
    }
}

typealias MonkeyJobs = Map<String, Day21.Job>