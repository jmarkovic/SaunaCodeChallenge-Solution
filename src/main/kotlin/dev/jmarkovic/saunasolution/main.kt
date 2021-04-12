package dev.jmarkovic.saunasolution

fun main(args: Array<String>) {
//  val map = loadMap(
//    """
//  @---A---+
//          |
//  x-B-+   C
//      |   |
//      +---+
//    """.trimIndent()
//  )
//
//  println(map.toString())
//
//  val start = map.startLocation()
//  println("Starting tile -> $start")
//  println("North tile -> ${start.north(map)}")
//  println("West tile -> ${start.west(map)}")
//  println("South tile -> ${start.south(map)}")
//  println("East tile -> ${start.east(map)}")

//  val actor = Actor(map, start)
//
//  actor.run()
}

//fun loadMap(input: String): Map {
//  return Map(input)
//}
//
//class Map(input: String) {
//
//  val width: Int
//  val height: Int
//  val field: List<CharArray>
//
//  init {
//    val lines = input.lines()
//    width = lines.maxOf { it.length }
//    height = lines.count()
//
//    field = lines.map { line ->
//      // ensure that all arrays have the same length
//      val array = CharArray(width) { ' ' }
//      line.toCharArray(destination = array)
//    }
//  }
//
//  private val tiles: List<Tile>
//    get() = this.field.flatMapIndexed { y, chars -> chars.mapIndexed { x, c -> Tile(x, y, c) } }
//
//  fun startLocation(): Tile {
//    // fixme this assumes there is not more than one starting tile
//    try {
//      return tiles.first { it.char == CHAR_START }
//    } catch (ex: NoSuchElementException) {
//      throw IllegalStateException("The map does not contain a start point.", ex)
//    }
//  }
//
//  override fun toString(): String {
//    return buildString {
//      append("Width: $width\n")
//        .append("Height: $height\n\n")
//
//      field.forEach { line ->
//        line.forEach { character ->
//          when {
//            character.isWhitespace() -> append('░')
//            else -> append(character)
//          }
//        }
//        append("\n")
//      }
//    }
//  }
//}
//
//data class Tile(val x: Int, val y: Int, val char: Char) {
//
//  val isValid: Boolean
//    get() = !char.isWhitespace()
//
//  val isEnd: Boolean
//    get() = char == CHAR_END
//
//  val isStart: Boolean
//    get() = char == CHAR_START
//
//  val isHorizontalPath: Boolean
//    get() = char == CHAR_HORIZONTAL_PATH
//
//  val isVerticalPath: Boolean
//    get() = char == CHAR_VERTICAL_PATH
//
//  val isCorner: Boolean
//    get() = char == CHAR_CORNER
//
//  fun north(onMap: Map): Tile? {
//    val y = this.y - 1
//    if (y < 0) return null
//
//    return Tile(x, y, onMap.field[y][x])
//  }
//
//  fun west(onMap: Map): Tile? {
//    val x = this.x - 1
//    if (x < 0) return null
//
//    return Tile(x, y, onMap.field[y][x])
//  }
//
//  fun south(onMap: Map): Tile? {
//    val y = this.y + 1
//    if (y >= onMap.height) return null
//
//    return Tile(x, y, onMap.field[y][x])
//  }
//
//  fun east(onMap: Map): Tile? {
//    val x = this.x + 1
//    if (x >= onMap.width) return null
//
//    return Tile(x, y, onMap.field[y][x])
//  }
//
//}
//
//class Actor(private val map: Map, private val startingTile: Tile) {
//
//  private var currentTile: CurrentTile = CurrentTile(startingTile, initializeFirstDirection())
//
//  fun run() {
//    var isEnd = true
//    while (isEnd) {
//      val next = currentTile.next(map)
//      currentTile = next
//      println("Moved to $currentTile")
//      if (currentTile.tile.isEnd) isEnd = false
//      Thread.sleep(150)
//    }
//
//  }
//
//  // fixme verify that only one direction is possible
//  private fun initializeFirstDirection(): Direction {
//    val north = startingTile.north(map)
//    if (north?.isValid == true) return Direction.NORTH
//
//    val west = startingTile.west(map)
//    if (west?.isValid == true) return Direction.WEST
//
//    val south = startingTile.south(map)
//    if (south?.isValid == true) return Direction.SOUTH
//
//    val east = startingTile.east(map)
//    if (east?.isValid == true) return Direction.EAST
//
//    throw IllegalStateException("Impossible case - No valid tiles available next to the current tile.")
//  }
//
//}
//
//val Direction.isNorth
//  get() = this == Direction.NORTH
//
//val Direction.isSouth
//  get() = this == Direction.SOUTH
//
//val Direction.isWest
//  get() = this == Direction.WEST
//
//val Direction.isEast
//  get() = this == Direction.EAST
//
//val Direction.isHorizontal
//  get() = this.isWest || this.isEast
//
//val Direction.isVertical
//  get() = this.isNorth || this.isSouth
//
//fun CurrentTile.next(map: Map): NextTile {
//
//  val next = when {
//    (tile.isHorizontalPath || tile.isStart || tile.char.isLetter()) && direction.isWest -> {
//      NextTile(tile.west(map)!!, direction)
//    }
//    (tile.isHorizontalPath || tile.isStart || tile.char.isLetter()) && direction.isEast -> {
//      NextTile(tile.east(map)!!, direction)
//    }
//    (tile.isVerticalPath || tile.isStart || tile.char.isLetter()) && direction.isNorth -> {
//      NextTile(tile.north(map)!!, direction)
//    }
//    (tile.isVerticalPath || tile.isStart || tile.char.isLetter()) && direction.isSouth -> {
//      NextTile(tile.south(map)!!, direction)
//    }
//    tile.isCorner && direction.isVertical -> {
//      val west = tile.west(map)
//      val east = tile.east(map)
//      if (west?.isValid == true) return NextTile(west, Direction.WEST)
//      else if (east?.isValid == true) return NextTile(east, Direction.EAST)
//      throw IllegalStateException("Cannot move anymore, stuck in the corner")
//    }
//    tile.isCorner && direction.isHorizontal -> {
//      tile.south(map) ?: tile.north(map)
//      val north = tile.north(map)
//      val south = tile.south(map)
//      if (north?.isValid == true) return NextTile(north, Direction.NORTH)
//      else if (south?.isValid == true) return NextTile(south, Direction.SOUTH)
//      throw IllegalStateException("Cannot move anymore, stuck in the corner")
//    }
//    else -> throw IllegalStateException("Unknown tile or direction '$this'")
//  }
//
//  if (next.tile.isStart) throw IllegalStateException("Another start tile found, the map is invalid.")
//  return next
//
//}