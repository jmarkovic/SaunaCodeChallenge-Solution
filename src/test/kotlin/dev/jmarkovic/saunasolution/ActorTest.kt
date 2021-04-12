package dev.jmarkovic.saunasolution

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class ActorTest {

  private lateinit var actor: Actor

  @BeforeMethod
  fun setUp() {
    val stepper: Stepper = StepperImpl(CornerTurnImpl(), ForwardImpl())
    val startingTile: StartingTile = StartingTileImpl(TileNeighborDirectionComparatorImpl())
    actor = ActorController(stepper, startingTile)
  }

  @Test
  fun `map 1`() {
    val gameMap = buildGameMap("""
  @---A---+
          |
  x-B-+   C
      |   |
      +---+
    """.trimIndent())
    var inventory: Inventory? = null
    assertThatCode { inventory = actor.traverse(gameMap) }
      .doesNotThrowAnyException()

    with (inventory!!) {
      assertThat(letters).isEqualTo("ACB")
      assertThat(pathway).isEqualTo("@---A---+|C|+---+|+-B-x")
    }
  }

  @Test
  fun `map 2`() {
    val gameMap = buildGameMap("""
  @
  | +-C--+
  A |    |
  +---B--+
    |      x
    |      |
    +---D--+
    """.trimIndent())
    var inventory: Inventory? = null
    assertThatCode { inventory = actor.traverse(gameMap) }
      .doesNotThrowAnyException()

    with (inventory!!) {
      assertThat(letters).isEqualTo("ABCD")
      assertThat(pathway).isEqualTo("@|A+---B--+|+--C-+|-||+---D--+|x")
    }
  }

  @Test
  fun `map 3`() {
    val gameMap = buildGameMap("""
  @---A---+
          |
  x-B-+   |
      |   |
      +---C
    """.trimIndent())
    var inventory: Inventory? = null
    assertThatCode { inventory = actor.traverse(gameMap) }
      .doesNotThrowAnyException()

    with (inventory!!) {
      assertThat(letters).isEqualTo("ACB")
      assertThat(pathway).isEqualTo("@---A---+|||C---+|+-B-x")
    }
  }

  @Test
  fun `map 4`() {
    val gameMap = buildGameMap("""
    +--B--+
    |   +-C-+
 @--A-+ | | |
    | | +-+ D
    +-+     |
            x
    """.trimIndent())
    var inventory: Inventory? = null
    assertThatCode { inventory = actor.traverse(gameMap) }
      .doesNotThrowAnyException()

    with (inventory!!) {
      assertThat(letters).apply {
        isEqualTo("ABCD")
        isNotEqualTo("AABCCD")
      }
      assertThat(pathway).isEqualTo("@--A-+|+-+|A|+--B--+C|+-+|+-C-+|D|x")
    }
  }

  @Test
  fun `map 5`() {
    val gameMap = buildGameMap("""
 +-B-+
 |  +C-+
@A+ ++ D
 ++    x
    """.trimIndent())
    var inventory: Inventory? = null
    assertThatCode { inventory = actor.traverse(gameMap) }
      .doesNotThrowAnyException()

    with (inventory!!) {
      assertThat(letters).isEqualTo("ABCD")
      assertThat(pathway).isEqualTo("@A+++A|+-B-+C+++C-+Dx")
    }
  }

  @Test
  fun `T forks`() {
    val gameMap = buildGameMap("""
         -B
          |
   @--A---+
          |
     x+   C
      |   |
      +---+
    """.trimIndent())
    assertThatThrownBy { actor.traverse(gameMap) }
      .isExactlyInstanceOf(IllegalJunctionInMapException::class.java)
  }
}