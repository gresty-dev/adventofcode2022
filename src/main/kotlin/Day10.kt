package dev.gresty.aoc.adventofcode2022

import kotlin.math.absoluteValue

fun main() {
    execute(10) { Day10().solveA(it) }
    execute(10) { Day10().solveB(it) }
}

class Day10 : Day<Int, String> {
    override fun solveA(input: Sequence<String>): Int {
        val program = input.map(::parseInstruction).toList()
        var signalSum = 0
        val clock = Clock()
        CPU(program, clock) { cycle, signal ->
            if (cycle % 40 == 20) {
                signalSum += cycle * signal
            }
        }
        clock.runToCompletion()
        return signalSum
    }

    override fun solveB(input: Sequence<String>): String {
        val program = input.map(::parseInstruction).toList()
        val clock = Clock()
        val vdu = VDU(clock)
        CPU(program, clock, vdu::signal)
        clock.runToCompletion()
        return vdu.display()
    }

    class CPU(private val program: List<Instruction>, clock: Clock, val signalHandler: (Int, Int) -> Unit) {
        private var x = 1
        private var pc = 0
        private var readyIn = 0
        private var instr = Instruction(OpCode.NOOP, null)

        init {
            clock.register(this::tick)
        }

        private fun tick(cycle: Int) : Boolean {
            if (readyIn == 0) {
                instr = program[pc]
                readyIn = instr.opcode.cycles
            }
            readyIn--

            signalHandler(cycle, x)

            if (readyIn == 0) {
                apply(instr)
                pc++
                if (pc >= program.size) {
                    return false
                }
            }
            return true
        }

        private fun apply(instr: Instruction) {
            if (instr.opcode == OpCode.ADDX) {
                x += instr.param!!
            }
        }
    }

    enum class OpCode(val cycles: Int) {
        NOOP(1),
        ADDX(2)
    }

    class Instruction(val opcode: OpCode, val param: Int?)

    private fun parseInstruction(line: String) : Instruction {
        val opcode = OpCode.valueOf(line.substring(0 until 4).uppercase())
        val param = if (line.length > 5) line.substring(5).toInt() else null
        return Instruction(opcode, param)
    }

    class Clock {
        private var cycle = 0
        private val devices = mutableListOf<(Int) -> Boolean>()

        fun register(device: (Int) -> Boolean) = devices.add(device)

        fun runToCompletion() {
            var running = true
            while (running) {
                cycle++
                running = devices
                    .map { it(cycle) }
                    .all { it }
            }
        }
    }

    class VDU(clock: Clock) {
        private val length = 40
        private val rows = 6
        private val screen = Array(rows) { CharArray(length) { '.' } }
        private var cycle = 0

        init {
            clock.register(this::tick)
        }

        private fun tick(cycle: Int) : Boolean {
            this.cycle = cycle
            return true
        }

        fun signal(cycle: Int, x: Int) {
            val row = (cycle - 1) / length
            val col = (cycle - 1) % length
            if ((x - col).absoluteValue <= 1) screen[row][col] = '#'
        }

        fun display() = screen.joinToString("\n") { String(it) }
    }
}