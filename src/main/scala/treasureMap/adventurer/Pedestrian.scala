package treasureMap.adventurer

import treasureMap.coordinates._
import treasureMap.move.MovesSequence
import scala.util.{Try, Success, Failure}
import treasureMap.direction._
import treasureMap.move.Move

  
case class Pedestrian (
  name: String,
  pos: Coordinates , 
  orientation: Direction, 
  moves: MovesSequence,
  treasures : Int =0
  )  extends Adventurer(name, pos, orientation, moves, treasures)  {

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

  //new apply method uses the old one, but it's rather unaethetic and wastes the patmat done in Map
  def apply(name: String, x: String, y : String, or: String, moves: String) : Pedestrian =
    apply(name + "-" + x + "-" + y + "-" + or + "-" + moves) 

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
