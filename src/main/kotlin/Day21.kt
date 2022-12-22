package dev.gresty.aoc.adventofcode2022

import java.lang.RuntimeException

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
        val jobs = input.map { parse(it, true) }.toMap()
        val root = jobs["root"]!!
        val humn = jobs["humn"]!!
        root.setValue(root.value(jobs), jobs)
        return humn.value(jobs)
    }

    private fun parse(line: String, part2: Boolean = false) : Pair<String, Job> =
        if (line.startsWith("humn")) {
            line.substring(0, 4) to MutableJob(line.substring(6).toLong(), !part2)
        } else if (part2 && line.startsWith("root")) {
            line.substring(0, 4) to EqualsJob(line.substring(6, 10), line.substring(13))
        } else if (line.length == 17) {
            line.substring(0, 4) to OperationJob(line.substring(6, 10), line.substring(13), operation(line[11]))
        } else {
            line.substring(0, 4) to LiteralJob(line.substring(6).toLong())
        }

    interface Job {
        fun isSet(): Boolean
        fun value(context: MonkeyJobs) : Long
        fun setValue(value: Long, context: MonkeyJobs)
    }

    class LiteralJob(private val value: Long) : Job {
        override fun isSet() = true
        override fun value(context: MonkeyJobs) = value
        override fun setValue(value: Long, context: MonkeyJobs) {
            if (value != this.value) throw RuntimeException("Unexpectedly updating literal value ${this.value} to $value")
        }
    }

    class MutableJob(private var value: Long, private var isSet: Boolean) : Job {
        override fun isSet() = isSet
        override fun value(context: MonkeyJobs) = value
        override fun setValue(value: Long, context: MonkeyJobs) {
            this.value = value
            isSet = true
        }
    }

    class OperationJob(private val leftMonkey: String, private val rightMonkey: String, private val operation: Operation) : Job {
        private var isSet = false
        private var value = 0L
        override fun isSet() = isSet
        override fun value(context: MonkeyJobs) =
            if (isSet) value
            else {
                val leftJob = context[leftMonkey]!!
                val rightJob = context[rightMonkey]!!
                val leftValue = leftJob.value(context)
                val rightValue = rightJob.value(context)
                if (leftJob.isSet() && rightJob.isSet()) {
                    isSet = true
                    value = operation.apply(leftValue, rightValue)
                }
                value
            }

        override fun setValue(value: Long, context: MonkeyJobs) {
            if (isSet && value != this.value)
                throw RuntimeException("Unexpectedly updating set operation value ${this.value} to $value")
            val leftJob = context[leftMonkey]!!
            val rightJob = context[rightMonkey]!!
            if (!leftJob.isSet() && !rightJob.isSet()) {
                throw RuntimeException("Both left and right jobs are unset.")
            }
            if (!leftJob.isSet()) {
                val rightJobValue = rightJob.value(context)
                val leftJobValue = operation.applyLeft(value, rightJobValue)
                leftJob.setValue(leftJobValue, context)
            } else {
                val leftJobValue = leftJob.value(context)
                val rightJobValue = operation.applyRight(value, leftJobValue)
                rightJob.setValue(rightJobValue, context)
            }
        }
    }

    class EqualsJob(private val leftMonkey: String, private val rightMonkey: String) : Job {
        private var isSet = false
        private var value = 0L

        override fun isSet() = isSet
        override fun value(context: Map<String, Job>): Long =
            if (isSet) value
            else {
                val leftJob = context[leftMonkey]!!
                val rightJob = context[rightMonkey]!!
                val leftValue = leftJob.value(context)
                val rightValue = rightJob.value(context)
                if (leftJob.isSet() && rightJob.isSet()) {
                    if (leftValue != rightValue) {
                        throw RuntimeException("Equals job has left and right values unequal.")
                    }
                    isSet = true
                    value = leftValue
                } else if (leftJob.isSet()) {
                    value = leftValue
                } else if (rightJob.isSet()) {
                    value = rightValue
                } else {
                    throw RuntimeException("Both left and right jobs are unset.")
                }
                value
            }

        override fun setValue(value: Long, context: MonkeyJobs) {
            if (isSet && value != this.value)
                throw RuntimeException("Unexpectedly updating set equals value ${this.value} to $value")
            val leftJob = context[leftMonkey]!!
            val rightJob = context[rightMonkey]!!
            if (!leftJob.isSet() && !rightJob.isSet()) {
                throw RuntimeException("Both left and right jobs are unset.")
            }
            if (!leftJob.isSet()) {
                leftJob.setValue(rightJob.value(context), context)
            } else {
                rightJob.setValue(leftJob.value(context), context)
            }
        }
    }

    private fun operation(op: Char) : Operation {
        return when (op) {
            '+' -> Operation.PLUS
            '-' -> Operation.MINUS
            '/' -> Operation.DIVIDE
            '*' -> Operation.MULTIPLY
            else -> throw UnsupportedOperationException("Unsupported operation: $op")
        }
    }

    enum class Operation(val forward: LongOperation, val reverseLeft: LongOperation, val reverseRight: LongOperation) {
        PLUS({ a, b -> a + b }, { x, b -> x - b }, { x, a -> x - a }),
        MINUS({ a, b -> a - b }, { x, b -> x + b }, { x, a -> a - x }),
        MULTIPLY({ a, b -> a * b }, { x, b -> x / b }, { x, a -> x / a }),
        DIVIDE({ a, b -> a / b }, { x, b -> x * b }, { x, a -> a / x });

        fun apply(a: Long, b: Long) = forward(a, b)
        fun applyLeft(x: Long, b: Long) = reverseLeft(x, b)
        fun applyRight(x: Long, a: Long) = reverseRight(x, a)
    }
}

typealias MonkeyJobs = Map<String, Day21.Job>
typealias LongOperation = (Long, Long) -> Long