package dev.jmarkovic.saunasolution

fun main(args: Array<String>) {

  val input = """
  @---A---+
          |
  x-B-+   C
      |   |
      +---+
  """.trimIndent()

  println("Playing a map:")
  println(input)

  val inventory = play(input)

  println("letters: `${inventory.letters}`; pathway: `${inventory.pathway}`")
}

fun play(input: String): Inventory {
  val gameMap = buildGameMap(input)
  val actor = buildActor()

  return actor.traverse(gameMap)
}