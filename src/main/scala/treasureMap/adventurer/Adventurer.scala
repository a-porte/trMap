package treasureMap.adventurer

import fr.ap.treasureMap.Coordinates
import treasureMap.move.MovesSequence
import scala.util.Try
import scala.util.Failure
import scala.util.Success
import treasureMap.direction.Direction

case class Adventurer(
  val name: String,
  val pos: Coordinates , 
  val orientation: Direction, 
  val moves: MovesSequence,
  val treasures : Int =0
  ) {

  override def toString() : String = s"$name $pos $orientation $moves "

}

object Adventurer{


  def apply(line: String) : Adventurer = {
    line.replace(" ", "").split("-") match {
    case Array(name, x, y, direction, moves) => {   
      Adventurer(
      name, 
      Coordinates(x, y), 
      Direction(direction), 
      MovesSequence(moves)
      )

    }
    case _ => 
      throw new MatchError(s"ADVENTURER CAN'T BE INSTANTIATED : $line")
    }  
  }

}
