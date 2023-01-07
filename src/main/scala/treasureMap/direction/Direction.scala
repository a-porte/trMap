package treasureMap.direction

import scala.collection.immutable.HashMap

sealed case class Direction(value : String)

case object Direction{
  object SOUTH extends Direction("S")
  object NORTH extends Direction("N")
  object WEST extends Direction("O")
  object EAST extends Direction("E")

  def  values : List[Direction] = counterClockwise.map{case (k, v) => k}.toList

  def counterClockwise = HashMap(
    SOUTH -> EAST,
    EAST -> NORTH,
    NORTH -> WEST,
    WEST -> SOUTH
  )

  def clockwise = counterClockwise.map{case(from, to) => (to, from)}

  def getNext(d: Direction, isClockWise: Boolean) = isClockWise match {
        case true => clockwise(d)
        case false => counterClockwise(d)
      }

    

}
