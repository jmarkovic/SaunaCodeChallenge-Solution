package dev.jmarkovic.saunasolution

import org.assertj.core.api.Assertions.assertThat
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class StartingTileTest {


  private lateinit var startingTile: StartingTile

  @BeforeMethod
  fun setUp() {
    startingTile = StartingTileImpl(TileNeighborDirectionComparatorImpl())
  }

  @Test
  fun `map has a starting tile facing NORTH`() {
    val mapInput = """
      +-+
      | x
      @
    """.trimIndent()
    val result = startingTile.findStartingTile(buildGameMap(mapInput))

    with (result) {
      assertThat(tile.x).isEqualTo(0)
      assertThat(tile.y).isEqualTo(2)
      assertThat(tile.char).isEqualTo('@')
      assertThat(direction).isEqualTo(Direction.NORTH)
    }
  }

  @Test
  fun `map has a starting tile facing WEST`() {
    val mapInput = """
      +-@
      |  
      +-x
    """.trimIndent()
    val result = startingTile.findStartingTile(buildGameMap(mapInput))

    with (result) {
      assertThat(tile.x).isEqualTo(2)
      assertThat(tile.y).isEqualTo(0)
      assertThat(tile.char).isEqualTo('@')
      assertThat(direction).isEqualTo(Direction.WEST)
    }
  }

  @Test
  fun `map has a starting tile facing SOUTH`() {
    val mapInput = """
      x @
      | | 
      +-+
    """.trimIndent()
    val result = startingTile.findStartingTile(buildGameMap(mapInput))

    with (result) {
      assertThat(tile.x).isEqualTo(2)
      assertThat(tile.y).isEqualTo(0)
      assertThat(tile.char).isEqualTo('@')
      assertThat(direction).isEqualTo(Direction.SOUTH)
    }
  }

  @Test
  fun `map has a starting tile facing EAST`() {
    val mapInput = """
      x-+
        |
      @-+  
    """.trimIndent()
    val result = startingTile.findStartingTile(buildGameMap(mapInput))

    with (result) {
      assertThat(tile.x).isEqualTo(0)
      assertThat(tile.y).isEqualTo(2)
      assertThat(tile.char).isEqualTo('@')
      assertThat(direction).isEqualTo(Direction.EAST)
    }
  }

}