package dev.jmarkovic.saunasolution

// Functions contained in this file are responsible for creating data class instances
// and states during the runtime.

fun buildGameMap(input: String): GameMap {

  val lines = input.lines()
  val width = lines.maxOf { it.length }
  val height = lines.count()

  val board = lines.map { line ->
    // ensure that all arrays have the same length
    val array = CharArray(width) { ' ' }
    line.toCharArray(destination = array)
  }

  return GameMap(width, height, board).apply {
    validateSingleStart()
    validateSingleEnd()
    validateSinglePathFromStart()
    validateSinglePathToEnd()
  }

}

private fun GameMap.validateSingleStart() {
  try {
    boardAsList.single { it.isStart }
  } catch (throwable: Throwable) {
    throw IllegalNumberOfStartTilesException(throwable)
  }
}

private fun GameMap.validateSingleEnd() {
  try {
    boardAsList.single { it.isEnd }
  } catch (throwable: Throwable) {
    throw IllegalNumberOfEndTilesException(throwable)
  }
}

private fun GameMap.validateSinglePathFromStart() {
  val start = boardAsList.single { it.isStart }
  try {
    validTileNeighbors(start).single { tile -> tile.isValid }
  } catch (throwable: Throwable) {
    throw IllegalNumberOfPathwaysFromStartException(throwable)
  }
}

private fun GameMap.validateSinglePathToEnd() {
  val end = boardAsList.single { it.isEnd }
  try {
    validTileNeighbors(end).single { tile -> tile.isValid }
  } catch (throwable: Throwable) {
    throw IllegalNumberOfPathwaysToEndException(throwable)
  }
}