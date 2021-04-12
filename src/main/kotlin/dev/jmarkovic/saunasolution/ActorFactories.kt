package dev.jmarkovic.saunasolution

fun buildActor(): Actor {
  return ActorController(
    StepperImpl(CornerTurnImpl(), ForwardImpl()),
    StartingTileImpl(TileNeighborDirectionComparatorImpl())
  )
}

fun emptyInventory(): Inventory {
  return Inventory("", "")
}