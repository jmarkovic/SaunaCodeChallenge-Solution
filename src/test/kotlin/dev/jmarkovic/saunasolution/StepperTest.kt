package dev.jmarkovic.saunasolution

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class StepperTest {

  private val gameMap = buildGameMap("""
      @-A-+
          |  x
       +--C  |
       B     |
       +-----+
    """.trimIndent())

  private lateinit var stepper: Stepper

  @BeforeMethod
  fun setUp() {
    stepper = StepperImpl(CornerTurnImpl(), ForwardImpl())
  }

  @Test
  fun `next step from (2,0) Tile is (3,0) horizontal path Tile`() {
    val current = CurrentTile(Tile(2, 0, 'A'), Direction.EAST)
    var next: NextTile? = null

    assertThatCode { next = stepper.nextStep(current, gameMap) }
      .doesNotThrowAnyException()

    with (next!!) {
      assertThat(tile.x).isEqualTo(3)
      assertThat(tile.y).isEqualTo(0)
      assertThat(tile.char).isEqualTo('-')
      assertThat(direction).isEqualTo(Direction.EAST)
    }

  }

  @Test
  fun `next step from (1,0) Tile is (2,0) horizontal path as a letter Tile`() {
    val current = CurrentTile(Tile(1, 0, '-'), Direction.EAST)
    var next: NextTile? = null

    assertThatCode { next = stepper.nextStep(current, gameMap) }
      .doesNotThrowAnyException()

    with (next!!) {
      assertThat(tile.x).isEqualTo(2)
      assertThat(tile.y).isEqualTo(0)
      assertThat(tile.char).isEqualTo('A')
      assertThat(direction).isEqualTo(Direction.EAST)
    }

  }

  @Test
  fun `next step from (0,0) start tile Tile is (1,0) horizontal path Tile`() {
    val current = CurrentTile(Tile(0, 0, '@'), Direction.EAST)
    var next: NextTile? = null

    assertThatCode { next = stepper.nextStep(current, gameMap) }
      .doesNotThrowAnyException()

    with (next!!) {
      assertThat(tile.x).isEqualTo(1)
      assertThat(tile.y).isEqualTo(0)
      assertThat(tile.char).isEqualTo('-')
      assertThat(direction).isEqualTo(Direction.EAST)
    }

  }

  @Test
  fun `next step from (4,0) corner Tile is (4,1) vertical path Tile`() {
    val current = CurrentTile(Tile(4, 0, '+'), Direction.EAST)
    var next: NextTile? = null

    assertThatCode { next = stepper.nextStep(current, gameMap) }
      .doesNotThrowAnyException()

    with (next!!) {
      assertThat(tile.x).isEqualTo(4)
      assertThat(tile.y).isEqualTo(1)
      assertThat(tile.char).isEqualTo('|')
      assertThat(direction).isEqualTo(Direction.SOUTH)
    }

  }

  @Test
  fun `next step from (1,2) corner Tile is (1,3) vertical path as letter Tile`() {
    val current = CurrentTile(Tile(1, 2, '+'), Direction.WEST)
    var next: NextTile? = null

    assertThatCode { next = stepper.nextStep(current, gameMap) }
      .doesNotThrowAnyException()

    with (next!!) {
      assertThat(tile.x).isEqualTo(1)
      assertThat(tile.y).isEqualTo(3)
      assertThat(tile.char).isEqualTo('B')
      assertThat(direction).isEqualTo(Direction.SOUTH)
    }

  }

  @Test
  fun `next step from (1,3) vertical path as letter Tile is (1,4) corner Tile`() {
    val current = CurrentTile(Tile(1, 3, 'B'), Direction.SOUTH)
    var next: NextTile? = null

    assertThatCode { next = stepper.nextStep(current, gameMap) }
      .doesNotThrowAnyException()

    with (next!!) {
      assertThat(tile.x).isEqualTo(1)
      assertThat(tile.y).isEqualTo(4)
      assertThat(tile.char).isEqualTo('+')
      assertThat(direction).isEqualTo(Direction.SOUTH)
    }

  }

  @Test
  fun `next step from (7,2) vertical path Tile is (7,1) end Tile`() {
    val current = CurrentTile(Tile(7, 2, '|'), Direction.NORTH)
    var next: NextTile? = null

    assertThatCode { next = stepper.nextStep(current, gameMap) }
      .doesNotThrowAnyException()

    with (next!!) {
      assertThat(tile.x).isEqualTo(7)
      assertThat(tile.y).isEqualTo(1)
      assertThat(tile.char).isEqualTo('x')
      assertThat(direction).isEqualTo(Direction.NORTH)
    }

  }

  @Test
  fun `next step from (7,1) end Tile is (7,1) end Tile`() {
    val current = CurrentTile(Tile(7, 1, 'x'), Direction.NORTH)
    var next: NextTile? = null

    assertThatCode { next = stepper.nextStep(current, gameMap) }
      .doesNotThrowAnyException()

    with (next!!) {
      assertThat(tile.x).isEqualTo(7)
      assertThat(tile.y).isEqualTo(1)
      assertThat(tile.char).isEqualTo('x')
      assertThat(direction).isEqualTo(Direction.NORTH)
    }

    assertThat(current).isEqualTo(next)

  }

  @Test
  fun `next step from (4,2) letter Tile is (3,2) path Tile`() {
    val current = CurrentTile(Tile(4, 2, 'C'), Direction.SOUTH)
    var next: NextTile? = null

    assertThatCode { next = stepper.nextStep(current, gameMap) }
      .doesNotThrowAnyException()

    with (next!!) {
      assertThat(tile.x).isEqualTo(3)
      assertThat(tile.y).isEqualTo(2)
      assertThat(tile.char).isEqualTo('-')
      assertThat(direction).isEqualTo(Direction.WEST)
    }
  }
}
