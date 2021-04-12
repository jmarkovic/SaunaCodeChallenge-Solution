package dev.jmarkovic.saunasolution

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class ForwardAndCorneringTest {

  private val gameMap = buildGameMap("""
      @-A-+
          | x
       +--C |
       B    |
       +----+
    """.trimIndent())

  private lateinit var cornerTurn: CornerTurn
  private lateinit var forward: Forward

  @BeforeMethod
  fun setUp() {

    cornerTurn = CornerTurnImpl()
    forward = ForwardImpl()
  }

  @Test
  fun `cornerTurn provides (2,2) SOUTH for (4,0) EAST`() {
    val current = CurrentTile(Tile(4, 0, '+'), Direction.EAST)
    var next: NextTile? = null
    assertThatCode { next = cornerTurn.cornerTurn(current, gameMap) }
      .doesNotThrowAnyException()

    with (next!!) {
      assertThat(tile.x).isEqualTo(4)
      assertThat(tile.y).isEqualTo(1)
      assertThat(tile.char).isEqualTo('|')
      assertThat(direction).isEqualTo(Direction.SOUTH)
    }
  }

  @Test
  fun `cornerTurn provides (6,3) NORTH for (6,4) EAST`() {
    val current = CurrentTile(Tile(6, 4, '+'), Direction.EAST)
    var next: NextTile? = null
    assertThatCode { next = cornerTurn.cornerTurn(current, gameMap) }
      .doesNotThrowAnyException()

    with (next!!) {
      assertThat(tile.x).isEqualTo(6)
      assertThat(tile.y).isEqualTo(3)
      assertThat(tile.char).isEqualTo('|')
      assertThat(direction).isEqualTo(Direction.NORTH)
    }
  }

  @Test
  fun `cornerTurn provides (3,2) WEST for (4,2) SOUTH`() {
    val current = CurrentTile(Tile(4, 2, 'C'), Direction.SOUTH)
    var next: NextTile? = null
    assertThatCode { next = cornerTurn.cornerTurn(current, gameMap) }
      .doesNotThrowAnyException()

    with (next!!) {
      assertThat(tile.x).isEqualTo(3)
      assertThat(tile.y).isEqualTo(2)
      assertThat(tile.char).isEqualTo('-')
      assertThat(direction).isEqualTo(Direction.WEST)
    }
  }

  @Test
  fun `cornerTurn provides an error for (2,0) EAST`() {
    val current = CurrentTile(Tile(2, 0, 'A'), Direction.EAST)
    assertThatThrownBy { cornerTurn.cornerTurn(current, gameMap) }
      .isExactlyInstanceOf(NotAValidNextTileException::class.java)
  }

  @Test
  fun `cornerTurn provides an error for (3,4) EAST`() {
    val current = CurrentTile(Tile(3, 4, '-'), Direction.EAST)
    assertThatThrownBy { cornerTurn.cornerTurn(current, gameMap) }
      .isExactlyInstanceOf(NotAValidCurrentTileException::class.java)
  }

  @Test
  fun `forward provides (1,0) EAST for (0,0) EAST start tile`() {
    val current = CurrentTile(Tile(0, 0, '@'), Direction.EAST)
    var next: NextTile? = null
    assertThatCode { next = forward.forward(current, gameMap) }
      .doesNotThrowAnyException()

    with (next!!) {
      assertThat(tile.x).isEqualTo(1)
      assertThat(tile.y).isEqualTo(0)
      assertThat(tile.char).isEqualTo('-')
      assertThat(direction).isEqualTo(Direction.EAST)
    }
  }

  @Test
  fun `forward provides (2,0) EAST for (1,0) EAST tile`() {
    val current = CurrentTile(Tile(1, 0, '-'), Direction.EAST)
    var next: NextTile? = null
    assertThatCode { next = forward.forward(current, gameMap) }
      .doesNotThrowAnyException()

    with (next!!) {
      assertThat(tile.x).isEqualTo(2)
      assertThat(tile.y).isEqualTo(0)
      assertThat(tile.char).isEqualTo('A')
      assertThat(direction).isEqualTo(Direction.EAST)
    }
  }

  @Test
  fun `forward provides (3,0) EAST for (2,0) EAST letter tile`() {
    val current = CurrentTile(Tile(2, 0, 'A'), Direction.EAST)
    var next: NextTile? = null
    assertThatCode { next = forward.forward(current, gameMap) }
      .doesNotThrowAnyException()

    with (next!!) {
      assertThat(tile.x).isEqualTo(3)
      assertThat(tile.y).isEqualTo(0)
      assertThat(tile.char).isEqualTo('-')
      assertThat(direction).isEqualTo(Direction.EAST)
    }
  }

  @Test
  fun `forward provides (6,2) NORTH for (6,3) NORTH tile`() {
    val current = CurrentTile(Tile(6, 3, '|'), Direction.NORTH)
    var next: NextTile? = null
    assertThatCode { next = forward.forward(current, gameMap) }
      .doesNotThrowAnyException()

    with (next!!) {
      assertThat(tile.x).isEqualTo(6)
      assertThat(tile.y).isEqualTo(2)
      assertThat(tile.char).isEqualTo('|')
      assertThat(direction).isEqualTo(Direction.NORTH)
    }
  }

  @Test
  fun `forward provides (6,1) NORTH for (6,2) NORTH tile`() {
    val current = CurrentTile(Tile(6, 2, '|'), Direction.NORTH)
    var next: NextTile? = null
    assertThatCode { next = forward.forward(current, gameMap) }
      .doesNotThrowAnyException()

    with (next!!) {
      assertThat(tile.x).isEqualTo(6)
      assertThat(tile.y).isEqualTo(1)
      assertThat(tile.char).isEqualTo('x')
      assertThat(direction).isEqualTo(Direction.NORTH)
    }
  }

  @Test
  fun `forward provides (1,4) SOUTH for (1,3) SOUTH letter tile`() {
    val current = CurrentTile(Tile(1, 3, 'B'), Direction.SOUTH)
    var next: NextTile? = null
    assertThatCode { next = forward.forward(current, gameMap) }
      .doesNotThrowAnyException()

    with (next!!) {
      assertThat(tile.x).isEqualTo(1)
      assertThat(tile.y).isEqualTo(4)
      assertThat(tile.char).isEqualTo('+')
      assertThat(direction).isEqualTo(Direction.SOUTH)
    }
  }

  @Test
  fun `forward provides an error for (1,2) SOUTH corner tile`() {
    val current = CurrentTile(Tile(1, 2, 'x'), Direction.WEST)
    assertThatThrownBy { forward.forward(current, gameMap) }
      .isExactlyInstanceOf(NotAValidCurrentTileException::class.java)
  }

  @Test
  fun `forward provides an error for (6,1) NORTH end tile`() {
    val current = CurrentTile(Tile(6, 1, 'x'), Direction.NORTH)
    assertThatThrownBy { forward.forward(current, gameMap) }
      .isExactlyInstanceOf(NotAValidCurrentTileException::class.java)
  }

}