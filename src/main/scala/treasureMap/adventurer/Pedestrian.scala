package treasureMap.adventurer

import treasureMap.coordinates._
import treasureMap.move.MovesSequence
import scala.util.{Try, Success, Failure}
import treasureMap.direction._
import treasureMap.move.Move

import treasureMap.adventurer.Moveable
  
case class Pedestrian (
  name: String,
  pos: Coordinates , 
  orientation: Direction, 
  moves: MovesSequence,
  treasures : Int =0
  )  extends Adventurer(name, pos, orientation, moves, treasures) with MoveHandler {

    override def copyMoveable(
      newOrientation: Direction, 
      newPos:Coordinates, 
      newMoves: MovesSequence) : Pedestrian = 
      this.copy(pos = newPos, orientation = newOrientation, moves = newMoves)


  override def toString() : String = s"$name $pos $orientation $moves "

  def digLoot() : Pedestrian = 
    this.copy(treasures = this.treasures + amountDiggedAtATime)
  
}

object Pedestrian{
  def apply(line: String) : Pedestrian = 
    line.replace(" ", "").split("-") match {
    case Array(name, x, y, direction, moves) =>  
      Pedestrian(
        name, 
        Coordinates(x, y), 
        Direction(direction), 
        MovesSequence(moves)
      )
    
    case _ => 
      throw new MatchError(s"ADVENTURER CAN'T BE INSTANTIATED : $line")
    }  
  
}
