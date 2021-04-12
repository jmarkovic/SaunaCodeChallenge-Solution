package dev.jmarkovic.saunasolution

import org.assertj.core.api.Assertions.assertThat
import org.testng.annotations.Test

class GameMapTest {

  companion object {
    private val mapInput = """
      @-+
        |
        x
    """.trimIndent()
  }

  @Test
  fun `center tile has 4 valid neighbors`() {
    val map = buildGameMap(mapInput)
    val tile = Tile(1, 1, ' ')

    val result = map.validTileNeighbors(tile)

    assertThat(result).apply {
      hasSize(4)
      contains(Tile(1, 0, '-'))
      contains(Tile(0, 1, ' '))
      contains(Tile(1, 2, ' '))
      contains(Tile(2, 1, '|'))
    }
  }

  @Test
  fun `edge tile has 3 valid neighbors`() {
    val map = buildGameMap(mapInput)
    val tile = Tile(2, 1, '|')

    val result = map.validTileNeighbors(tile)

    assertThat(result).apply {
      hasSize(3)
      contains(Tile(2, 0, '+'))
      contains(Tile(2, 2, 'x'))
      contains(Tile(1, 1, ' '))
    }
  }

  @Test
  fun `corner tile has 2 valid neighbors`() {
    val map = buildGameMap(mapInput)
    val tile = Tile(2, 0, '+')

    val result = map.validTileNeighbors(tile)

    assertThat(result).apply {
      hasSize(2)
      contains(Tile(1, 0, '-'))
      contains(Tile(2, 1, '|'))
    }
  }

}