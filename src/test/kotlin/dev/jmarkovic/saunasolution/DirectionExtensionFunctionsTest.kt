package dev.jmarkovic.saunasolution

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class DirectionExtensionFunctionsTest {

  private lateinit var comparator: TileNeighborDirectionComparator

  @BeforeMethod
  fun setUp() {
    comparator = TileNeighborDirectionComparatorImpl()
  }

  @Test
  fun `first Tile is NORTH of the second Tile`() {
    val first = Tile(2, 3, '#')
    val second = Tile(2, 4, '#')

    var result: Boolean? = null
    assertThatCode { result = first.isNorthOf(second, comparator) }
      .doesNotThrowAnyException()

    assertThat(result).isTrue
  }

  @Test
  fun `first Tile is far too NORTH of the second Tile`() {
    val first = Tile(2, 2, '#')
    val second = Tile(2, 4, '#')

    var result: Boolean? = null
    assertThatCode { result = first.isNorthOf(second, comparator) }
      .doesNotThrowAnyException()

    assertThat(result).isFalse
  }

  @Test
  fun `first and second Tile are on the same location, NORTH is false`() {
    val first = Tile(2, 2, '#')
    val second = Tile(2, 2, '#')

    var result: Boolean? = null
    assertThatCode { result = first.isNorthOf(second, comparator) }
      .doesNotThrowAnyException()

    assertThat(result).isFalse
  }

  @Test
  fun `first Tile is WEST of the second Tile`() {
    val first = Tile(2, 3, '#')
    val second = Tile(3, 3, '#')

    var result: Boolean? = null
    assertThatCode { result = first.isWestOf(second, comparator) }
      .doesNotThrowAnyException()

    assertThat(result).isTrue
  }

  @Test
  fun `first Tile is far too WEST of the second Tile`() {
    val first = Tile(1, 3, '#')
    val second = Tile(3, 3, '#')

    var result: Boolean? = null
    assertThatCode { result = first.isWestOf(second, comparator) }
      .doesNotThrowAnyException()

    assertThat(result).isFalse
  }

  @Test
  fun `first and second Tile are on the same location, WEST is false`() {
    val first = Tile(2, 2, '#')
    val second = Tile(2, 2, '#')

    var result: Boolean? = null
    assertThatCode { result = first.isWestOf(second, comparator) }
      .doesNotThrowAnyException()

    assertThat(result).isFalse
  }

  @Test
  fun `first Tile is SOUTH of the second Tile`() {
    val first = Tile(2, 3, '#')
    val second = Tile(2, 2, '#')

    var result: Boolean? = null
    assertThatCode { result = first.isSouthOf(second, comparator) }
      .doesNotThrowAnyException()

    assertThat(result).isTrue
  }

  @Test
  fun `first Tile is far too SOUTH of the second Tile`() {
    val first = Tile(2, 4, '#')
    val second = Tile(2, 2, '#')

    var result: Boolean? = null
    assertThatCode { result = first.isSouthOf(second, comparator) }
      .doesNotThrowAnyException()

    assertThat(result).isFalse
  }

  @Test
  fun `first and second Tile are on the same location, SOUTH is false`() {
    val first = Tile(2, 2, '#')
    val second = Tile(2, 2, '#')

    var result: Boolean? = null
    assertThatCode { result = first.isSouthOf(second, comparator) }
      .doesNotThrowAnyException()

    assertThat(result).isFalse
  }

  @Test
  fun `first Tile is EAST of the second Tile`() {
    val first = Tile(2, 3, '#')
    val second = Tile(1, 3, '#')

    var result: Boolean? = null
    assertThatCode { result = first.isEastOf(second, comparator) }
      .doesNotThrowAnyException()

    assertThat(result).isTrue
  }

  @Test
  fun `first Tile is far too EAST of the second Tile`() {
    val first = Tile(3, 3, '#')
    val second = Tile(1, 3, '#')

    var result: Boolean? = null
    assertThatCode { result = first.isEastOf(second, comparator) }
      .doesNotThrowAnyException()

    assertThat(result).isFalse
  }

  @Test
  fun `first and second Tile are on the same location, EAST is false`() {
    val first = Tile(2, 2, '#')
    val second = Tile(2, 2, '#')

    var result: Boolean? = null
    assertThatCode { result = first.isEastOf(second, comparator) }
      .doesNotThrowAnyException()

    assertThat(result).isFalse
  }
}