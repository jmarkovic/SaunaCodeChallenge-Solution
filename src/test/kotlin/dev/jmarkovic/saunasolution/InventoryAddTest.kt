package dev.jmarkovic.saunasolution

import org.assertj.core.api.Assertions.assertThat
import org.testng.annotations.Test

class InventoryAddTest {

  @Test
  fun `add path tile to empty inventory adds it to the pathway`() {
    val inventory = emptyInventory()
    val actorTile = ActorTile(Tile(0, 0, '-'), Direction.NORTH)
    val result = inventory.addTile(actorTile)

    with (result) {
      assertThat(pathway).contains("-")
      assertThat(letters).isEmpty()
    }
  }

  @Test
  fun `add letter tile to empty inventory adds it to the pathway and letters`() {
    val inventory = emptyInventory()
    val actorTile = ActorTile(Tile(0, 0, 'A'), Direction.NORTH)
    val result = inventory.addTile(actorTile)

    with (result) {
      assertThat(pathway).contains("A")
      assertThat(letters).contains("A")
    }
  }

  @Test
  fun `add path tile to non-empty inventory adds it to the pathway`() {
    val inventory = Inventory("ABC", "@-A-+|B|+C")
    val actorTile = ActorTile(Tile(0, 0, '-'), Direction.NORTH)
    val result = inventory.addTile(actorTile)

    with (result) {
      assertThat(letters).isEqualTo("ABC")
      assertThat(pathway).isEqualTo("@-A-+|B|+C-")
    }
  }

  @Test
  fun `add letter tile to non-empty inventory adds it to the pathway and letters`() {
    val inventory = Inventory("ABC", "@-A-+|B|+C-")
    val actorTile = ActorTile(Tile(0, 0, 'D'), Direction.NORTH)
    val result = inventory.addTile(actorTile)

    with (result) {
      assertThat(letters).isEqualTo("ABCD")
      assertThat(pathway).isEqualTo("@-A-+|B|+C-D")
    }
  }

  @Test
  fun `add existing letter tile to non-empty inventory adds it to the pathway, but not to letters`() {
    val inventory = Inventory("ABC", "@-A-+|B|+C-")
    val actorTile = ActorTile(Tile(0, 0, 'C'), Direction.NORTH)
    val result = inventory.addTile(actorTile)

    with (result) {
      assertThat(letters).isEqualTo("ABC")
      assertThat(pathway).isEqualTo("@-A-+|B|+C-C")
    }
  }

}