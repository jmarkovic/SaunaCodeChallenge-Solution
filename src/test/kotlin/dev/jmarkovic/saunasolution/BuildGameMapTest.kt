package dev.jmarkovic.saunasolution

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.testng.annotations.Test

class BuildGameMapTest {

  @Test
  fun `successfully create a 9x5 Map`() {
    val input = """
  @---A---+
          |
  x-B-+   C
      |   |
      +---+
    """.trimIndent()

    lateinit var gameMap: GameMap
    assertThatCode { gameMap = buildGameMap(input) }.doesNotThrowAnyException()
    with(gameMap) {
      assertThat(width).isEqualTo(9)
      assertThat(height).isEqualTo(5)
    }
  }

  @Test
  fun `fail creating map because of missing start`() {
    val input = """
     -A---+
          |
  x-B-+   C
      |   |
      +---+
    """.trimIndent()

    assertThatThrownBy { buildGameMap(input) }
      .isExactlyInstanceOf(IllegalNumberOfStartTilesException::class.java)
  }

  @Test
  fun `fail creating map because of too many starts`() {
    val input = """
   @--A-@-+
          |
  x-B-+   C
      |   |
      +---+
    """.trimIndent()

    assertThatThrownBy { buildGameMap(input) }
      .isExactlyInstanceOf(IllegalNumberOfStartTilesException::class.java)
  }

  @Test
  fun `fail creating map because of missing end`() {
    val input = """
   @--A---+
          |
    B-+   C
      |   |
      +---+
    """.trimIndent()

    assertThatThrownBy { buildGameMap(input) }
      .isExactlyInstanceOf(IllegalNumberOfEndTilesException::class.java)
  }

  @Test
  fun `fail creating map because of too many ends`() {
    val input = """
   @--A---+
          |
  x-Bx+   C
      |   |
      +---+
    """.trimIndent()

    assertThatThrownBy { buildGameMap(input) }
      .isExactlyInstanceOf(IllegalNumberOfEndTilesException::class.java)
  }

  @Test
  fun `fail creating map because of no pathway from start`() {
    val input = """
   @ -A---+
          |
  x-B-+   C
      |   |
      +---+
    """.trimIndent()

    assertThatThrownBy { buildGameMap(input) }
      .isExactlyInstanceOf(IllegalNumberOfPathwaysFromStartException::class.java)
  }

  @Test
  fun `fail creating map because of too many pathways from start`() {
    val input = """
   @--A---+
   |      |
  x-B-+   C
   |  |   |
   +------|-+
          | |
          +-+
    """.trimIndent()

    assertThatThrownBy { buildGameMap(input) }
      .isExactlyInstanceOf(IllegalNumberOfPathwaysFromStartException::class.java)
  }

  @Test
  fun `fail creating map because of no pathway to end`() {
    val input = """
   @--A---+
          |
  x B-+   C
      |   |
      +---+
    """.trimIndent()

    assertThatThrownBy { buildGameMap(input) }
      .isExactlyInstanceOf(IllegalNumberOfPathwaysToEndException::class.java)
  }

  @Test
  fun `fail creating map because of too many pathways to end`() {
    val input = """
   @--A---+
          |
  x-B-+   C
  |   |   |
  +-------|-+
          | |
          +-+
    """.trimIndent()

    assertThatThrownBy { buildGameMap(input) }
      .isExactlyInstanceOf(IllegalNumberOfPathwaysToEndException::class.java)
  }

}