# Notes

### Task parameters

###### Direction

* an "actor" moves through a map
    * it always moves straight relative to its pointing direction
    * if it cannot continue straight (no "valid" tiles in front), try to go right or left (relative to its pointing
      direction)
    * if it cannot continue into any direction (no valid tiles in front, left, or right), validate that the current tile
      is the **end** tile
    * **crash** if there are **no** valid directions
      **and** the current tile is **not** the end tile

###### Tile validation

* an "actor" validates each tile it steps on
    * it records the tile it stepped on to a path buffer
    * if it stands on a capital letter, it tries to record that letter
      to a letter buffer
    * it validates the tile it steps on:
        * _lenient_ - validates that the tile is not an empty tile
          (part of direction check)
        * _rigid_ - validates that the tile is a valid path tile or corner tile
          (part of the direction check)
    * **crash** if the current tile is the end tile **and** the map continues
      with more path tiles
    * if _rigid_, **crash** if the current tile is not a valid path tile
      or corner tile 

###### Map validation

* the game master (non-actor) validates all tiles
  before an "actor" runs the map
    * it records the start location and the direction
    * **crash** if it cannot find a start tile
    * **crash** if it finds more than one start tile
    * **crash** if it cannot find an end tile
    * **crash** if it finds more than one end tile
    

## Progression

First, create a working dirty prototype - all in single file, no respect
to CLEAN, SOLID, edge-cases, etc. Just make it work for the simplest
possible solution. Try other solutions and see what happens.