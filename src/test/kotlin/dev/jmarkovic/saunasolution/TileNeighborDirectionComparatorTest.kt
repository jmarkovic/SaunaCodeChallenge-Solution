package dev.jmarkovic.saunasolution

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class TileNeighborDirectionComparatorTest {

  private lateinit var comparator: TileNeighborDirectionComparator

  @BeforeMethod
  fun setUp() {
    comparator = TileNeighborDirectionComparatorImpl()
  }

  @Test
  fun `second Tile is NORTH from the first Tile`() {
    val first = Tile(1, 1, '@')
    val second = Tile(1, 0, '@')

    var result: Direction? = null
    assertThatCode { result = comparator.compare(first, second) }
      .doesNotThrowAnyException()

    assertThat(result).isEqualTo(Direction.NORTH)
  }

  @Test
  fun `second Tile is SOUTH from the first Tile`() {
    val first = Tile(1, 1, '@')
    val second = Tile(1, 2, '@')

    var result: Direction? = null
    assertThatCode { result = comparator.compare(first, second) }
      .doesNotThrowAnyException()

    assertThat(result).isEqualTo(Direction.SOUTH)
  }

  @Test
  fun `second Tile is EAST from the first Tile`() {
    val first = Tile(1, 1, '@')
    val second = Tile(2, 1, '@')

    var result: Direction? = null
    assertThatCode { result = comparator.compare(first, second) }
      .doesNotThrowAnyException()

    assertThat(result).isEqualTo(Direction.EAST)
  }

  @Test
  fun `second Tile is WEST from the first Tile`() {
    val first = Tile(1, 1, '@')
    val second = Tile(0, 1, '@')

    var result: Direction? = null
    assertThatCode { result = comparator.compare(first, second) }
      .doesNotThrowAnyException()

    assertThat(result).isEqualTo(Direction.WEST)
  }

  @Test
  fun `cannot compare two Tiles with distance of 2`() {
    val first = Tile(1, 1, '@')
    val second = Tile(1, 3, '@')

    assertThatThrownBy { comparator.compare(first, second) }
      .isExactlyInstanceOf(NotAValidNeighborException::class.java)
  }

  @Test
  fun `cannot compare two Tiles pointing to the same location`() {
    val first = Tile(1, 1, '@')
    val second = Tile(1, 1, '@')

    assertThatThrownBy { comparator.compare(first, second) }
      .isExactlyInstanceOf(NotAValidNeighborException::class.java)
  }

  @Test
  fun `cannot compare two Tiles that neighbor diagonally`() {
    val first = Tile(1, 1, '@')
    val second = Tile(2, 2, '@')

    assertThatThrownBy { comparator.compare(first, second) }
      .isExactlyInstanceOf(NotAValidNeighborException::class.java)
  }

}