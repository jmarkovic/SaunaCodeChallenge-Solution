package dev.jmarkovic.saunasolution

/**
 * Actor is the controller that executes operations of traversing the map.
 * It is responsible of collecting all the information.
 */
interface Actor {

  fun traverse(gameMap: GameMap, delayEachStepMs: Long = 0): Inventory

}

class ActorController(
  private val stepper: Stepper,
  private val startTileFinder: StartingTile,
) : Actor {

  private lateinit var currentTile: CurrentTile
  private var inventory: Inventory = emptyInventory()

  override fun traverse(gameMap: GameMap, delayEachStepMs: Long): Inventory {
    currentTile = startTileFinder.findStartingTile(gameMap)
    // collect the starting tile
    inventory = inventory.addTile(currentTile)

    var nextTile: NextTile? = null

    do {
      if (nextTile != null) {
        println("Moving to $nextTile")
        inventory = inventory.addTile(nextTile)
        currentTile = nextTile
      }

      nextTile = stepper.nextStep(currentTile, gameMap)

      if (delayEachStepMs > 0) Thread.sleep(delayEachStepMs)
    } while (nextTile != currentTile)

    return inventory
  }

}