package dev.gresty.aoc.adventofcode2022

import java.lang.RuntimeException

fun main() {
    execute(11) { Day11().solveA(it) }
    execute(11) { Day11().solveB(it) }
}

class Day11 : Day<Long, Long> {
    private var modulator = 1
    private var reducer = 3

    override fun solveA(input: Sequence<String>): Long {
        val monkeys = makeMonkeys(input)
        repeat(20) { playOneRound(monkeys) }
        return monkeys.values.map(Monkey::inspections).sortedDescending().take(2).reduce{ a, b -> a * b }
    }

    override fun solveB(input: Sequence<String>): Long {
        val monkeys = makeMonkeys(input)
        reducer = 1
        repeat(10000) { playOneRound(monkeys) }
        return monkeys.values.map(Monkey::inspections).sortedDescending().take(2).reduce{ a, b -> a * b }
    }

    private fun playOneRound(monkeys: Map<Int, Monkey>) {
        monkeys.keys.sorted().forEach { monkeys[it]!!.play(monkeys) }
    }

    private val monkeyRegex = """Monkey (\d+):""".toRegex()
    private val itemsRegex = """Starting items: (\d+[, \d+]*)""".toRegex()
    private val operationRegex = """Operation: new = (.+)""".toRegex()
    private val testRegex = """Test: divisible by (\d+)""".toRegex()
    private val ifTrueRegex = """If true: throw to monkey (\d+)""".toRegex()
    private val ifFalseRegex = """If false: throw to monkey (\d+)""".toRegex()

    private fun makeMonkeys(input: Sequence<String>) : Map<Int, Monkey> {

        val monkeys = mutableMapOf<Int, Monkey>()
        var num = -1
        input.forEach {
            monkeyRegex.find(it)?.let { match ->
                num = match.groupValues[1].toInt()
                monkeys[num] = Monkey(::modulator, ::reducer)
            }
            itemsRegex.find(it)?.let { match -> monkeys[num]!!.parseItems(match.groupValues[1]) }
            operationRegex.find(it)?.let { match -> monkeys[num]!!.operation = parseOperation(match.groupValues[1]) }
            testRegex.find(it)?.let { match ->
                val divisor = match.groupValues[1].toInt()
                monkeys[num]!!.testDivisor = divisor
                modulator *= divisor
            }
            ifTrueRegex.find(it)?.let { match -> monkeys[num]!!.ifTrue = match.groupValues[1].toInt() }
            ifFalseRegex.find(it)?.let { match -> monkeys[num]!!.ifFalse = match.groupValues[1].toInt() }
        }
        return monkeys
    }

    class Monkey(val modulator: () -> Int, val worryReducer: () -> Int) { // use a builder...
        private val items = mutableListOf<Long>()
        var testDivisor: Int? = null
        var operation: ((Long) -> Long)? = null
        var ifTrue: Int? = null
        var ifFalse: Int? = null
        var inspections = 0L


        fun parseItems(input: String) {
            input.splitToSequence(',')
                .map { it.trim().toLong() }
                .forEach (items::add)
        }

        fun play(monkeys: Map<Int, Monkey>) {
            while (items.size > 0) {
                val worry = inspect(items.removeAt(0)) % modulator()
                if (worry % testDivisor!! == 0L) {
                    monkeys[ifTrue!!]!!.add(worry)
                } else {
                    monkeys[ifFalse!!]!!.add(worry)
                }
            }
        }

        private fun inspect(item: Long) : Long {
            inspections++
            return operation!!(item) / worryReducer()
        }

        private fun add(item: Long) {
            items.add(item)
        }

        override fun toString(): String {
            return "inspected: $inspections"
        }
    }

    fun parseOperation(input: String) : (Long) -> Long {
        val op = input[4]
        val other = input.substring(6)
        if (other == "old") {
            return { x -> x * x }
        }
        val otherLong = other.toLong()
        if (op == '*') {
            return { x -> x * otherLong }
        } else {
            return { x -> x + otherLong }
        }
    }

    fun parseFormula(input: String) : FormulaNode {
        var root: FormulaNode? = null
        var current: FormulaNode? = null
        input.splitToSequence(' ')
            .forEach {
                val newNode = getNode(it)
                if (root == null) {
                    root = newNode
                } else if (newNode is OperatorNode) {
                    newNode.left = current
                    root = newNode
                } else if (current is OperatorNode) {
                    (current as OperatorNode).right = newNode
                } else {
                    throw RuntimeException("Don't know how to attach this node: $it")
                }
                current = newNode
            }
        return root!!
    }

    private fun getNode(token: String) : FormulaNode {
        return when {
            Operator.isSymbol(token) -> OperatorNode(Operator.fromSymbol(token)!!)
            isNumber(token) -> LiteralNode(token.toLong())
            else -> VariableNode(token)
        }
    }

    abstract class FormulaNode {
        abstract fun value(context: Map<String, Long>): Long
        abstract fun valid(context: Map<String, Long>): Boolean
    }

    private val numberRegex = """\d+""".toRegex()
    private fun isNumber(s: String) = numberRegex.matches(s)

    class LiteralNode(private val value: Long) : FormulaNode() {
        override fun value(context: Map<String, Long>) = value
        override fun valid(context: Map<String, Long>) = true
    }

    class VariableNode(private val name: String) : FormulaNode() {
        override fun value(context: Map<String, Long>) = context.getValue(name)
        override fun valid(context: Map<String, Long>) = context.containsKey(name)
    }

    class OperatorNode(private val op: Operator) : FormulaNode() {
        var left: FormulaNode? = null
        var right: FormulaNode? = null

        override fun value(context: Map<String, Long>) = op.apply(left!!.value(context), right!!.value(context))
        override fun valid(context: Map<String, Long>) = left != null && left!!.valid(context )&& right != null && right!!.valid(context)
    }

    enum class Operator(val symbol: Char) {
        MULTIPLY('*') { override fun apply(x: Long, y: Long) = x * y },
        ADD('+') { override fun apply(x: Long, y: Long) = x + y };

        abstract fun apply(x: Long, y: Long) : Long

        companion object {
            fun fromSymbol(s: String) = when(s[0]) {
                MULTIPLY.symbol -> MULTIPLY
                ADD.symbol -> ADD
                else -> null
            }

            fun isSymbol(s: String) = s.length == 1 && s[0] == MULTIPLY.symbol || s[0] == ADD.symbol
        }
    }
}