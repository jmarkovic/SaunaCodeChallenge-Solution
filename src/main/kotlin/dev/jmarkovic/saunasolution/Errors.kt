package dev.jmarkovic.saunasolution

/**
 * Thrown when asserting if the [GameMap] has exactly one start
 * (exactly one tile with the [CHAR_START] character).
 */
class IllegalNumberOfStartTilesException(cause: Throwable? = null) : Exception(
  "The map can have only one start tile.",
  cause
)

/**
 * Thrown when asserting if the [GameMap] has exactly one end
 * (exactly one tile with the [CHAR_END] character).
 */
class IllegalNumberOfEndTilesException(cause: Throwable? = null) : Exception(
  "The map can have only one end tile.",
  cause
)

/**
 * Thrown when asserting if the [GameMap] has exactly one pathway from
 * the starting tile.
 */
class IllegalNumberOfPathwaysFromStartException(cause: Throwable? = null) : Exception(
  "The map can have only a single pathway from a starting tile.",
  cause
)

/**
 * Thrown when asserting if the [GameMap] has exactly one pathway to
 * the ending tile.
 */
class IllegalNumberOfPathwaysToEndException(cause: Throwable? = null) : Exception(
  "The map can have only a single pathway from a starting tile.",
  cause
)

/**
 * Thrown when asserting if the [GameMap] has an illegal junction or a corner.
 * A corner can have exactly two paths.
 */
class IllegalJunctionInMapException(cause: Throwable? = null) : Exception(
  "The map contains an illegal junction.",
  cause
)

/**
 * Thrown while performing a comparison between two Tiles when trying to determine
 * their direction or neighbors.
 */
class NotAValidNeighborException(first: Tile, second: Tile, cause: Throwable? = null) : Exception(
  "'$first' and '$second' tiles are not valid neighbors.",
  cause
)

/**
 * Thrown when the [CurrentTile] is not an expected [Tile] for the operation
 * that needs to be executed.
 */
class NotAValidCurrentTileException(
  currentTile: CurrentTile,
  operationName: String,
  cause: Throwable? = null
) : Exception(
  "Cannot perform `$operationName` on the current tile `$currentTile`",
  cause
)

/**
 * Thrown when trying to find a [NextTile] instance from the [CurrentTile] instance.
 * It may be thrown if an unexpected operation is being executed.
 */
class NotAValidNextTileException(currentTile: CurrentTile, operationName: String, cause: Throwable? = null) : Exception(
  "Cannot perform `$operationName` from the current tile `$currentTile`",
  cause
)

/**
 * Thrown when a [Stepper] cannot determine what is the current tile.
 * In practice, this exception should never be thrown. However, if thrown, the implementer
 * is responsible to adapt the implementation in order to cover all possible characters.
 */
class UnknownCurrentTileException(currentTile: CurrentTile, cause: Throwable? = null) : Exception(
  "Unknown current tile `$currentTile`",
  cause
)