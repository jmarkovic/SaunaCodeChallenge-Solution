package dev.jmarkovic.saunasolution

/**
 * Compares two [Tile]s orientation relative to each other.
 * Two Tiles can be compared only if the two are neighbors.
 */
interface TileNeighborDirectionComparator {

  /**
   * Returns the [Direction] of the `second` [Tile] relative to the `first` [Tile].
   * If the second Tile is not a valid neighbor of the first Tile, the function
   * throws [NotAValidNeighborException].
   */
  fun compare(first: Tile, second: Tile): Direction

}

class TileNeighborDirectionComparatorImpl : TileNeighborDirectionComparator {

  override fun compare(first: Tile, second: Tile): Direction {
    if (first.x == second.x) {
      if (second.y + 1 == first.y) return Direction.NORTH
      else if (second.y - 1 == first.y) return Direction.SOUTH
    } else if (first.y == second.y) {
      if (second.x + 1 == first.x) return Direction.WEST
      else if (second.x - 1 == first.x) return Direction.EAST
    }

    throw NotAValidNeighborException(first, second)
  }

}

/**
 * Finds the starting [Tile] from the provided `gameMap` and find the
 * appropriate [Direction] for the next step.
 *
 * The starting [Tile] is defined by the [CHAR_START] character.
 */
interface StartingTile {

  fun findStartingTile(gameMap: GameMap): CurrentTile

}

class StartingTileImpl(
  private val tileDirectionComparator: TileNeighborDirectionComparator
) : StartingTile {

  override fun findStartingTile(gameMap: GameMap): CurrentTile {
    val start = gameMap.startTile
    val firstPath = gameMap.validTileNeighbors(start).single { it.isValid }
    val startingDirection = tileDirectionComparator.compare(start, firstPath)
    return CurrentTile(start, startingDirection)
  }

}

/**
 * Calculates a step. A "step" is an action the [Actor]
 * performs to move from the [CurrentTile] to the [NextTile].
 *
 * Different [ActorTile]s have different rules how to perform a step
 * based on the [Tile.char]. For example, if the [CurrentTile]
 * has a [NORTH][Direction.NORTH] direction and the [Tile.char]
 * is [CHAR_VERTICAL_PATH], then this Stepper can provide a [NextTile]
 * that would naturally follow NORTH.
 */
interface Stepper {

  fun nextStep(currentTile: CurrentTile, onGameMap: GameMap): NextTile

}

class StepperImpl(
  private val cornerTurn: CornerTurn,
  private val forward: Forward
) : Stepper {

  override fun nextStep(currentTile: CurrentTile, onGameMap: GameMap): NextTile {
    // specify a letter char as 'a' so that it can be used in the comparison below

    return when (if (currentTile.tile.char.isUpperCase()) 'a' else currentTile.tile.char) {
      CHAR_CORNER -> cornerTurn.cornerTurn(currentTile, onGameMap)
      CHAR_START, CHAR_HORIZONTAL_PATH, CHAR_VERTICAL_PATH -> forward.forward(currentTile, onGameMap)
      CHAR_END -> currentTile
      'a' -> {
        // assume a letter can continue forward, but try to turn a corner if forward fails
        runCatching { forward.forward(currentTile, onGameMap) }
          .getOrElse { cornerTurn.cornerTurn(currentTile, onGameMap) }
      }

      else -> throw UnknownCurrentTileException(currentTile) // should never happen
    }
  }

}

/**
 * Performs a turn on the corner [Tile]. A corner is defined by [CHAR_CORNER] character,
 * or an uppercase letter.
 *
 * If the current tile is not a valid character, the validation fails and no operation is executed.
 */
interface CornerTurn {

  fun cornerTurn(currentTile: CurrentTile, onGameMap: GameMap): NextTile

}

internal class CornerTurnImpl : CornerTurn {

  override fun cornerTurn(currentTile: CurrentTile, onGameMap: GameMap): NextTile {
    if (!currentTile.tile.isCorner && !currentTile.tile.char.isUpperCase()) {
      throw NotAValidCurrentTileException(currentTile, "cornerTurn")
    }

    val leftAndRight =
      runCatching { currentTile.leftNeighbor(onGameMap) } to runCatching { currentTile.rightNeighbor(onGameMap) }

    // T junctions are not allowed
    if (leftAndRight.first.isSuccess && leftAndRight.second.isSuccess) {
      throw IllegalJunctionInMapException()
    }

    with  (leftAndRight) {
      return if (first.isSuccess) first.getOrThrow()
      else second.getOrThrow()
    }
  }

}

/**
 * Performs a forward move on any [Tile] that is not a corner tile or an end tile,
 * in which case it throws [NotAValidCurrentTileException].
 */
interface Forward {

  fun forward(currentTile: CurrentTile, onGameMap: GameMap): NextTile

}

internal class ForwardImpl : Forward {

  override fun forward(currentTile: CurrentTile, onGameMap: GameMap): NextTile {
    if (currentTile.tile.isCorner || currentTile.tile.isEnd) {
      throw NotAValidCurrentTileException(currentTile, "forward")
    }
    return currentTile.forwardNeighbor(onGameMap)
  }

}