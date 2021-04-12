package dev.jmarkovic.saunasolution

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.testng.annotations.Test

class SolutionTest {

  @Test
  fun `Map 1 - a basic example`() {
    val input = """
  @---A---+
          |
  x-B-+   C
      |   |
      +---+
    """.trimIndent()

    var inventory: Inventory? = null
    assertThatCode { inventory = play(input) }
      .doesNotThrowAnyException()

    with(inventory!!) {
      assertThat(letters).isEqualTo("ACB")
      assertThat(pathway).isEqualTo("@---A---+|C|+---+|+-B-x")
    }
  }

  @Test
  fun `Map 2 - go straight through intersections`() {
    val input = """
  @
  | +-C--+
  A |    |
  +---B--+
    |      x
    |      |
    +---D--+
    """.trimIndent()

    var inventory: Inventory? = null
    assertThatCode { inventory = play(input) }
      .doesNotThrowAnyException()

    with(inventory!!) {
      assertThat(letters).isEqualTo("ABCD")
      assertThat(pathway).isEqualTo("@|A+---B--+|+--C-+|-||+---D--+|x")
    }
  }

  @Test
  fun `Map 3 - letters may be found on turns`() {
    val input = """
  @---A---+
          |
  x-B-+   |
      |   |
      +---C
    """.trimIndent()

    var inventory: Inventory? = null
    assertThatCode { inventory = play(input) }
      .doesNotThrowAnyException()

    with(inventory!!) {
      assertThat(letters).isEqualTo("ACB")
      assertThat(pathway).isEqualTo("@---A---+|||C---+|+-B-x")
    }
  }

  @Test
  fun `Map 4 - do not collect letters twice`() {
    val input = """
    +--B--+
    |   +-C-+
 @--A-+ | | |
    | | +-+ D
    +-+     |
            x
    """.trimIndent()

    var inventory: Inventory? = null
    assertThatCode { inventory = play(input) }
      .doesNotThrowAnyException()

    with(inventory!!) {
      assertThat(letters).isEqualTo("ABCD")
      assertThat(pathway).isEqualTo("@--A-+|+-+|A|+--B--+C|+-+|+-C-+|D|x")
    }
  }

  @Test
  fun `Map 5 - keep direction, even in a compact space`() {
    val input = """
 +-B-+
 |  +C-+
@A+ ++ D
 ++    x
    """.trimIndent()

    var inventory: Inventory? = null
    assertThatCode { inventory = play(input) }
      .doesNotThrowAnyException()

    with(inventory!!) {
      assertThat(letters).isEqualTo("ABCD")
      assertThat(pathway).isEqualTo("@A+++A|+-B-+C+++C-+Dx")
    }
  }

  @Test
  fun `Map 6 - no start`() {
    val input = """
     -A---+
          |
  x-B-+   C
      |   |
      +---+
    """.trimIndent()

    assertThatThrownBy { play(input) }
      .isExactlyInstanceOf(IllegalNumberOfStartTilesException::class.java)
  }

  @Test
  fun `Map 7 - no end`() {
    val input = """
   @--A---+
          |
    B-+   C
      |   |
      +---+
    """.trimIndent()

    assertThatThrownBy { play(input) }
      .isExactlyInstanceOf(IllegalNumberOfEndTilesException::class.java)
  }

  @Test
  fun `Map 8 - multiple starts`() {
    val input = """
   @--A-@-+
          |
  x-B-+   C
      |   |
      +---+
    """.trimIndent()

    assertThatThrownBy { play(input) }
      .isExactlyInstanceOf(IllegalNumberOfStartTilesException::class.java)
  }

  @Test
  fun `Map 9 - multiple ends`() {
    val input = """
   @--A---+
          |
  x-Bx+   C
      |   |
      +---+
    """.trimIndent()

    assertThatThrownBy { play(input) }
      .isExactlyInstanceOf(IllegalNumberOfEndTilesException::class.java)
  }

  @Test
  fun `Map 10 - T forks`() {
    val input = """
         -B
          |
   @--A---+
          |
     x+   C
      |   |
      +---+
    """.trimIndent()

    assertThatThrownBy { play(input) }
      .isExactlyInstanceOf(IllegalJunctionInMapException::class.java)
  }

}