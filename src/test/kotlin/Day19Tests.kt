package dev.gresty.aoc.adventofcode2022

import dev.gresty.aoc.adventofcode2022.Day19.FactoryTask.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day19Tests : FunSpec () {

    private val example1 = """
Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.
      """.trimIndent()

    private val blueprint1 = """
Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
      """.trimIndent()




    init {
        test("Execute example A") {
            Day19().solveA(example1.lineSequence()).shouldBe(12)
        }

        test("Execute example B") {
            Day19().solveB(example1.lineSequence()).shouldBe(0)
        }

        test("Execute blueprint 1") {
            Day19().maximiseProduction(Day19.Factory(Day19().parseBlueprint(blueprint1))) shouldBe 9
        }

        test("Blueprint is parsed correctly") {
            val blueprint = Day19().parseBlueprint(blueprint1)
            blueprint.oreForOrbBot shouldBe 4
            blueprint.oreForClaybot shouldBe 2
            blueprint.oreForObsidianbot shouldBe 3
            blueprint.clayForObsidianbot shouldBe 14
            blueprint.oreForGeodeBot shouldBe 2
            blueprint.obsidianForGeodebot shouldBe 7
        }

        test("Execute one path for blueprint 1") {
            var factory = Day19.Factory(Day19().parseBlueprint(blueprint1))
            factory = factory.execute(WAIT)
            factory = factory.execute(WAIT)
            factory = factory.execute(CLAYBOT)
            factory = factory.execute(WAIT)
            factory = factory.execute(CLAYBOT)
            factory = factory.execute(WAIT)
            factory = factory.execute(CLAYBOT)
            factory = factory.execute(WAIT)
            factory = factory.execute(WAIT)
            factory = factory.execute(WAIT)
            factory = factory.execute(OBSIDIANBOT)
            factory = factory.execute(CLAYBOT)
            factory = factory.execute(WAIT)
            factory = factory.execute(WAIT)
            factory = factory.execute(OBSIDIANBOT)
            factory = factory.execute(WAIT)
            factory = factory.execute(WAIT)
            factory = factory.execute(GEODEBOT)
            factory = factory.execute(WAIT)
            factory = factory.execute(WAIT)
            factory = factory.execute(GEODEBOT)
            factory = factory.execute(WAIT)
            factory = factory.execute(WAIT)
            factory = factory.execute(WAIT)
            factory.minutes shouldBe 24
            factory.geodes shouldBe 9
        }

    }
}