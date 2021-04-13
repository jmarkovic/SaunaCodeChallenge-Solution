@file:JvmName("Main")

package dev.jmarkovic.saunasolution

fun main() {

  // When pasting, make sure the map is properly aligned. Do not worry
  // about the extra spacing on the left side of the map - the `trimIndent`
  // function removes it.
  val input = """
  @---A---+
          |
  x-B-+   C
      |   |
      +---+
  """.trimIndent()

  println("Playing a map:")
  println(input.replace(' ', '░'))

  val inventory = play(input)

  println("letters: `${inventory.letters}`; pathway: `${inventory.pathway}`")
}

fun play(input: String): Inventory {
  val gameMap = buildGameMap(input)
  val actor = buildActor()

  return actor.traverse(gameMap)
}