package treasureMap.adventurer


import treasureMap.move.Move
import treasureMap.move.MovesSequence
import treasureMap.adventurer.Moveable
import treasureMap.move.Move._
import treasureMap.direction.Direction._
import treasureMap.coordinates._

import treasureMap.coordinates.Coordinates
import treasureMap.direction.Direction

object MoveHandler {
 
  /*
    Uses two auxiliary functions to compute the next orientation or position of a Moveable
  */

  def computeMove(
    toMove: Moveable, 
    isIllegalPosFun:  Coordinates => Boolean,
    hasLootFun : Coordinates => Boolean ) : Moveable = {

    def computeDirection(d: Direction, m: Move) : Direction = 
      m match {
        case LEFT => Direction.getNext(d, false)
        case RIGHT => Direction.getNext(d, true)
        case FRONT => d
      }
    
    def computePos(pos: Coordinates, d: Direction) : Coordinates = {
      val (x, y) = Coordinates.unapply(pos)
      d match {
        case EAST => Coordinates(x + 1, y)
        case NORTH => Coordinates(x, y -1)
        case SOUTH => Coordinates(x, y+1)
        case WEST => Coordinates(x - 1, y)
      }
    }

    toMove.moves.pop() match {
      //no move left
      case None =>     toMove 

      //somes moves left
      case Some(tuple) =>     {
        val (moveToDo, newMovesSeq) = tuple
        val actualPos = toMove.pos
        val actualOrientation = toMove.orientation

        val (newOr, newPos, newMoves) = moveToDo match {
          case FRONT => {
            val uncheckedPos = computePos(actualPos, toMove.orientation)

            val checkedPos = if (isIllegalPosFun(uncheckedPos))
                  actualPos
                else
                  uncheckedPos

            (actualOrientation, checkedPos, newMovesSeq) 
          }
          case LEFT | RIGHT => (
            computeDirection(actualOrientation, moveToDo),
            actualPos,
            newMovesSeq
            )
        }
        
        val hasLoot = hasLootFun(newPos)


        hasLoot match {
          case true => toMove.copyMoveable(newOr, newPos, newMoves).digLoot()
          case false => toMove.copyMoveable(newOr, newPos, newMoves)
        }
       
        


        
        }
      }
  }

}

