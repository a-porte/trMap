package treasureMap.adventurer

import treasureMap.coordinates.Coordinates
import treasureMap.direction.Direction
import treasureMap.move.MovesSequence

import treasureMap.adventurer.Moveable
abstract class Adventurer(
  name: String,
  pos: Coordinates , 
  orientation: Direction, 
  moves: MovesSequence,
  treasures : Int 
  ) extends  Moveable 
