package dev.jmarkovic.saunasolution

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.testng.annotations.Test

class CurrentTileTest {

  private val mapInput = """
    @--+   x
       |   |
       |   |
       +---+
  """.trimIndent()

  private val gameMap = buildGameMap(mapInput)

  @Test
  fun `turning left provides a valid NextTile`() {
    val currentTile = CurrentTile(Tile(3, 3, '+'), Direction.SOUTH)
    var next: NextTile? = null
    assertThatCode { next = currentTile.leftNeighbor(gameMap) }
      .doesNotThrowAnyException()

    with(next!!) {
      assertThat(tile.x).isEqualTo(4)
      assertThat(tile.y).isEqualTo(3)
      assertThat(tile.char).isEqualTo('-')
      assertThat(direction).isEqualTo(Direction.EAST)
    }

  }

  @Test
  fun `turning left provides an error`() {
    val currentTile = CurrentTile(Tile(3, 0, '+'), Direction.EAST)
    assertThatThrownBy { currentTile.leftNeighbor(gameMap) }
      .isExactlyInstanceOf(NotAValidNextTileException::class.java)
  }

  @Test
  fun `turning left when not on a corner provides an error`() {
    val currentTile = CurrentTile(Tile(0, 0, '@'), Direction.EAST)
    assertThatThrownBy { currentTile.leftNeighbor(gameMap) }
      .isExactlyInstanceOf(NotAValidNextTileException::class.java)
  }

  @Test
  fun `turning right provides a valid NextTile`() {
    val currentTile = CurrentTile(Tile(3, 0, '+'), Direction.EAST)
    var next: NextTile? = null
    assertThatCode { next = currentTile.rightNeighbor(gameMap) }
      .doesNotThrowAnyException()

    with(next!!) {
      assertThat(tile.x).isEqualTo(3)
      assertThat(tile.y).isEqualTo(1)
      assertThat(tile.char).isEqualTo('|')
      assertThat(direction).isEqualTo(Direction.SOUTH)
    }

  }

  @Test
  fun `turning right provides an error`() {
    val currentTile = CurrentTile(Tile(3, 3, '+'), Direction.SOUTH)
    assertThatThrownBy { currentTile.rightNeighbor(gameMap) }
      .isExactlyInstanceOf(NotAValidNextTileException::class.java)
  }

  @Test
  fun `turning right when not on a corner provides an error`() {
    val currentTile = CurrentTile(Tile(0, 0, '@'), Direction.EAST)
    assertThatThrownBy { currentTile.rightNeighbor(gameMap) }
      .isExactlyInstanceOf(NotAValidNextTileException::class.java)
  }

  @Test
  fun `going forward provides a valid NextTile`() {
    val currentTile = CurrentTile(Tile(1, 0, '-'), Direction.EAST)
    var next: NextTile? = null
    assertThatCode { next = currentTile.forwardNeighbor(gameMap) }
      .doesNotThrowAnyException()

    with(next!!) {
      assertThat(tile.x).isEqualTo(2)
      assertThat(tile.y).isEqualTo(0)
      assertThat(tile.char).isEqualTo('-')
      assertThat(direction).isEqualTo(Direction.EAST)
    }
  }

  @Test
  fun `going forward in a corner provides an error`() {
    val currentTile = CurrentTile(Tile(3, 0, '+'), Direction.EAST)
    assertThatThrownBy { currentTile.forwardNeighbor(gameMap) }
      .isExactlyInstanceOf(NotAValidNextTileException::class.java)
  }

}