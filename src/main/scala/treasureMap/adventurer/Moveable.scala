package treasureMap.adventurer

import treasureMap.coordinates._
import treasureMap.move.MovesSequence
import treasureMap.direction._

import treasureMap.coordinates.Coordinates
import treasureMap.rules.LootDigger

trait Moveable extends Positionable with Orientable  {
  val moves: MovesSequence 
  
  def copyMoveable(orientation: Direction, position: Coordinates, moves: MovesSequence) : Moveable
  
  def move(
    obstables: Coordinates => Boolean = _ => false, 
    hasLoot: Coordinates => Boolean = _ => false) : Moveable =
       MoveHandler.computeMove(this, obstables, hasLoot)

  def amountDiggedAtATime = MoveHandler.amountDiggedAtATime 

  def digLoot() : Moveable
} 