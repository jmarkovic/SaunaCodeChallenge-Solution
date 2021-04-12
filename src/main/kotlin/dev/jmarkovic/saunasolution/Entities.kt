package dev.jmarkovic.saunasolution

// Strongly defined Char constants.
// Every map is expected to have one starting Char, one ending Char, and optionally other Chars.

/** The starting Char. An [Actor] starts to travel at this point. Only one should exist on the [GameMap]. */
const val CHAR_START = '@'

/** The ending Char. An [Actor] ends its travel by reaching this point. Only one should exist on the [GameMap]. */
const val CHAR_END = 'x'

/** The cornering Char. An [Actor] needs to change its direction at this point. Only one branch is allowed. */
const val CHAR_CORNER = '+'

/** An [Actor] travels over _horizontal_ paths when traveling [WEST][Direction.WEST] or [EAST][Direction.EAST]. */
const val CHAR_HORIZONTAL_PATH = '-'

/** An [Actor] travels over _vertical_ paths when traveling [NORTH][Direction.NORTH] or [EAST][Direction.SOUTH]. */
const val CHAR_VERTICAL_PATH = '|'

/**
 * A [GameMap] is the container of all [Tile]s. Tiles are stored into a matrix
 * specified by [width] and [height].
 */
data class GameMap(val width: Int, val height: Int, val board: List<CharArray>) {

  val boardAsList by lazy {
    board.flatMapIndexed { y, chars -> chars.mapIndexed { x, c -> Tile(x, y, c) } }
  }

  val startTile by lazy {
    boardAsList.single { it.isStart }
  }

  /**
   * Returns a list of [Tile]s that could theoretically exist within the
   * boundaries of this Map. If the provider [tile] lies on a border or a corner of the Map,
   * it can only have three or two valid neighbors respectively because the points for
   * the non-valid Tiles would exist outside of the Map.
   *
   * This function does not validate the [Tile] itself. It only provides Tiles
   * with valid coordinates. Tiles can still have non-valid characters and it's
   * up to the calling code to take care of that case.
   */
  fun validTileNeighbors(tile: Tile): List<Tile> {
    val list = mutableListOf<Tile>()
    with (tile) {
      if (x > 0) list.add(Tile(x - 1, y, board[y][x - 1]))
      if (y > 0) list.add(Tile(x, y - 1, board[y - 1][x]))
      if (x < width - 1) list.add(Tile(x + 1, y, board[y][x + 1]))
      if (y < height - 1) list.add(Tile(x, y + 1, board[y + 1][x]))
    }
    return list
  }

}

/**
 * Defines a single tile in a map. A single tile is a [Char] with an [x] and [y] coordinate
 * that directly correspond to an internal matrix in the [GameMap] instance.
 *
 * A tile is **not** aware of its neighbors.
 */
data class Tile(val x: Int, val y: Int, val char: Char) {

  /** A tile is valid if it has any character, but not whitespace (empty area on the board). */
  val isValid: Boolean
    get() = !char.isWhitespace()

  val isEnd: Boolean
    get() = char == CHAR_END

  val isStart: Boolean
    get() = char == CHAR_START

  val isCorner: Boolean
    get() = char == CHAR_CORNER

}

/**
 * A predefined set of directions an [Actor] can travel.
 *
 * When starting its travel, an Actor will face a certain direction
 * (the way the first path goes from the starting point). After the first step,
 * an Actor moves in the same direction, until it reaches a corner. The direction
 * of travel is denoted with one of the values defined in this enum.
 */
enum class Direction { NORTH, WEST, SOUTH, EAST }

/**
 * An [ActorTile] is a tile that an Actor can act upon. The act implies a direction.
 *
 * In other words, an [ActorTile] is a composition of a [Tile] and [Direction].
 */
data class ActorTile(val tile: Tile, val direction: Direction)
/**
 * Represents a [Tile] an [Actor] is currently situated on, facing a [Direction].
 * Depending on what the [Tile] is, an action function can calculate the [NextTile].
 */
typealias CurrentTile = ActorTile
/**
 * Represents a [Tile] an [Actor] will move to based on what the [CurrentTile] is.
 * The [Direction] value may be different from what [CurrentTile] had for its direction
 * (for example, a corner tile).
 */
typealias NextTile = ActorTile

/**
 * The collection of information that ends up as a result of the [Actor]
 * traveling the [GameMap]. The [letters] are all unique letter characters
 * that the Actor collects while traveling. The [pathway] is a linear
 * documentation of the path the Actor traveled to reach the end.
 */
data class Inventory(val letters: String, val pathway: String)