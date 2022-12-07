package dev.gresty.aoc.adventofcode2022.day07

import dev.gresty.aoc.adventofcode2022.execute

fun main() {
    execute("day07.txt") { solve07A(it) }
    execute("day07.txt") { solve07A(it) }
    execute("day07.txt") { solve07A(it) }
    execute("day07.txt") { solve07A(it) }
    execute("day07.txt") { solve07B(it) }
}

fun solve07A(input: Sequence<String>): Long {
    val fileSystem = input.fold(FileSystem()) { fs, line -> fs.interpret(line) }

    var largeDirs = 0L
    fileSystem.sizes(fileSystem.root) { node, size ->
        if (node.isDirectory && size <= 100000) largeDirs += size
    }
    return largeDirs
}

fun solve07B(input: Sequence<String>): Long {
    val fileSystem = input.fold(FileSystem()) { fs, line -> fs.interpret(line) }

    val sizes = mutableListOf<Long>()
    val total = fileSystem.sizes(fileSystem.root) { _, size ->
        sizes.add(size)
    }

    val needed = 30000000 - (70000000 - total)
    var smallestBigEnough = Long.MAX_VALUE
    sizes.forEach { if (it in needed until smallestBigEnough) smallestBigEnough = it }
    return smallestBigEnough
}

class FileSystem {
    val root = Node("/", true, 0)
    private var cwd = root

    private fun addFile(name: String, size: Long) = cwd.addChild(Node(name, false, size))
    private fun addDirectory(name: String) = cwd.addChild(Node(name, true, 0))

    private fun cd(name: String) {
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

    fun interpret(line: String) : FileSystem {
        when {
            line.startsWith("$ cd") -> cd(line.substring(5))
            line.startsWith("$ ls") -> {}
            line.startsWith("dir")  -> addDirectory(line.substring(4))
            else -> addFile(line.substringAfter(" "), line.substringBefore(" ").toLong())
        }
        return this
    }
}

class Node(private val name: String, val isDirectory: Boolean, val size: Long) {
    var parent = this
    val children = mutableListOf<Node>()

    fun addChild(child: Node) {
        children.add(child)
        child.parent = this
    }

    fun child(name: String) = children.firstOrNull { it.name == name }
}