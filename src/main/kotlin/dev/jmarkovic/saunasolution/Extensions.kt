package dev.jmarkovic.saunasolution

// All extension functions and properties are defined in this file.
// Ideally, extensions would be organized per file where they are most commonly used.
// For the solution this small, having a file for the whole project is acceptable.

/**
 * Returns `true` if **this** [Tile] is NORTH of the `other` Tile.
 * In other words, the `other` tile is SOUTH of this Tile.
 */
fun Tile.isNorthOf(other: Tile, comparator: TileNeighborDirectionComparator): Boolean {
  return this.isDirectionOf(Direction.NORTH, other, comparator)
}

/**
 * Returns `true` if **this** [Tile] is WEST of the `other` Tile.
 * In other words, the `other` tile is EAST of this Tile.
 */
fun Tile.isWestOf(other: Tile, comparator: TileNeighborDirectionComparator): Boolean {
  return this.isDirectionOf(Direction.WEST, other, comparator)
}

/**
 * Returns `true` if **this** [Tile] is SOUTH of the `other` Tile.
 * In other words, the `other` tile is NORTH of this Tile.
 */
fun Tile.isSouthOf(other: Tile, comparator: TileNeighborDirectionComparator): Boolean {
  return this.isDirectionOf(Direction.SOUTH, other, comparator)
}

/**
 * Returns `true` if **this** [Tile] is EAST of the `other` Tile.
 * In other words, the `other` tile is WEST of this Tile.
 */
fun Tile.isEastOf(other: Tile, comparator: TileNeighborDirectionComparator): Boolean {
  return this.isDirectionOf(Direction.EAST, other, comparator)
}

private fun Tile.isDirectionOf(
  direction: Direction,
  other: Tile,
  comparator: TileNeighborDirectionComparator
): Boolean {
  return try {
    comparator.compare(other, this) == direction
  } catch (ex: NotAValidNeighborException) {
    ex.printStackTrace()
    false
  }
}

val Direction.isNorth
  get() = this == Direction.NORTH

val Direction.isSouth
  get() = this == Direction.SOUTH

val Direction.isWest
  get() = this == Direction.WEST

val Direction.isEast
  get() = this == Direction.EAST

val Direction.isHorizontal
  get() = this.isWest || this.isEast

val Direction.isVertical
  get() = this.isNorth || this.isSouth

/**
 * Returns a valid **left neighbor** [NextTile] from the `gameMap` for this [CurrentTile].
 * If such [NextTile] doesn't exist, the function throws [NotAValidNextTileException].
 */
fun CurrentTile.leftNeighbor(gameMap: GameMap): NextTile {
  val neighbors = gameMap.validTileNeighbors(tile).filter { it.isValid }
  try {
    return when (direction) {
      Direction.NORTH -> NextTile(neighbors.findTile(CHAR_VERTICAL_PATH) { it.x == tile.x - 1 }, Direction.WEST)
      Direction.WEST -> NextTile(neighbors.findTile(CHAR_HORIZONTAL_PATH) { it.y == tile.y + 1 }, Direction.SOUTH)
      Direction.SOUTH -> NextTile(neighbors.findTile(CHAR_VERTICAL_PATH) { it.x == tile.x + 1 }, Direction.EAST)
      Direction.EAST -> NextTile(neighbors.findTile(CHAR_HORIZONTAL_PATH) { it.y == tile.y - 1 }, Direction.NORTH)
    }
  } catch (throwable: Throwable) {
    throw NotAValidNextTileException(this, "left", throwable)
  }
}

/**
 * Returns a valid **right neighbor** [NextTile] from the `gameMap` for this [CurrentTile].
 * If such [NextTile] doesn't exist, the function throws [NotAValidNextTileException].
 */
fun CurrentTile.rightNeighbor(gameMap: GameMap): NextTile {
  val neighbors = gameMap.validTileNeighbors(tile).filter { it.isValid }
  try {
    return when (direction) {
      Direction.NORTH -> NextTile(neighbors.single { it.x == tile.x + 1 }, Direction.EAST)
      Direction.WEST -> NextTile(neighbors.single { it.y == tile.y - 1 }, Direction.NORTH)
      Direction.SOUTH -> NextTile(neighbors.single { it.x == tile.x - 1 }, Direction.WEST)
      Direction.EAST -> NextTile(neighbors.single { it.y == tile.y + 1 }, Direction.SOUTH)
    }
  } catch (throwable: Throwable) {
    throw NotAValidNextTileException(this, "right", throwable)
  }
}

/**
 * Returns a valid **forward neighbor** [NextTile] from the `gameMap` for this [CurrentTile].
 * If such [NextTile] doesn't exist, the function throws [NotAValidNextTileException].
 */
fun CurrentTile.forwardNeighbor(gameMap: GameMap): NextTile {
  val neighbors = gameMap.validTileNeighbors(tile).filter { it.isValid }
  try {
    return when (direction) {
      Direction.NORTH -> NextTile(neighbors.single { it.y == tile.y - 1 }, Direction.NORTH)
      Direction.WEST -> NextTile(neighbors.single { it.x == tile.x - 1 }, Direction.WEST)
      Direction.SOUTH -> NextTile(neighbors.single { it.y == tile.y + 1 }, Direction.SOUTH)
      Direction.EAST -> NextTile(neighbors.single { it.x == tile.x + 1 }, Direction.EAST)
    }
  } catch (throwable: Throwable) {
    throw NotAValidNextTileException(this, "forward", throwable)
  }
}

private fun List<Tile>.findTile(
  illegalChar: Char,
  tilePredicate: (Tile) -> Boolean
): Tile {
  val tile = single(tilePredicate)
  return if (tile.char == illegalChar) throw RuntimeException("Unexpected char `$illegalChar`")
  else tile
}

/**
 * Adds the `actorTile` to the [Actor]s Inventory.
 * The function appends the pathway character to the [Inventory.pathway] property.
 * If the character is a letter that's not already contained in the [Inventory.letters]
 * property, it appends the character to the that property.
 *
 * The result of the function is a copy of this Inventory.
 */
fun Inventory.addTile(actorTile: ActorTile): Inventory {
  val appendLetters = with (actorTile.tile.char) {
    if (isUpperCase() && !letters.contains(this)) letters + this
    else letters
  }
  val appendPathway = pathway + actorTile.tile.char
  return copy(letters = appendLetters, pathway = appendPathway)
}