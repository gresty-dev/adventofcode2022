package dev.gresty.aoc.adventofcode2022.day07

import dev.gresty.aoc.adventofcode2022.execute

fun main() {
    execute("day07.txt") { solve07A(it) }
    execute("day07.txt") { solve07B(it) }
}

fun solve07A(input: Sequence<String>): Long {
    val fileSystem = FileSystem()
    val terminal = Terminal(fileSystem)
    input.forEach { terminal.interpret(it) }

    var largeDirs = 0L
    fileSystem.sizes(fileSystem.root) { node, size ->
        if (node.isDirectory && size <= 100000) largeDirs += size
    }
    return largeDirs
}

fun solve07B(input: Sequence<String>): Int {
    return 0
}

class Terminal(private val fileSystem: FileSystem) {
    fun interpret(line: String) {
        if (line.startsWith("$ cd")) {
            fileSystem.cd(line.substring(5))
        } else if (line.startsWith("$ ls")) {
            // no-op
        } else if (line.startsWith("dir")) {
            fileSystem.addNode(line.substring(4), true, 0)
        } else {
            fileSystem.addNode(line.substringAfter(" "), false, line.substringBefore(" ").toLong())
        }
    }
}

class FileSystem {
    val root = Node("/", true, 0)
    private var cwd = root

    fun addNode(name: String, isDirectory: Boolean, size: Long) {
        cwd.addChild(Node(name, isDirectory, size))
    }

    fun cd(name: String) {
        cwd = when (name) {
            "/" -> root
            ".." -> cwd.parent
            else -> cwd.child(name)!!
        }
    }

    fun sizes(node: Node, action: (Node, Long) -> Unit) : Long {
        var size = 0L
        node.children.map {
            size += sizes(it, action)
        }
        size += node.size
        action(node, size)
        return size
    }
}

class Node(val name: String, val isDirectory: Boolean, val size: Long) {
    var parent = this
    val children = mutableListOf<Node>()

    fun addChild(child: Node) {
        children.add(child)
        child.parent = this
    }

    fun child(name: String) = children.firstOrNull { it.name == name }
}