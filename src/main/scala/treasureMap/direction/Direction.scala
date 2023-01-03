package treasureMap.direction

sealed case class Direction(value : String)

case object Direction{
  object SOUTH extends Direction("S")
  object NORTH extends Direction("N")
  object WEST extends Direction("O")
  object EAST extends Direction("E")

  val values = List(WEST, NORTH, EAST, SOUTH)

  def getNext(d: Direction, isClockWise: Boolean) = {
      val index = Direction.values.indexWhere(_ == d) 

      val remainder = values.length 

      // offset > 0 if we go clockwise, cf Direction.values definition
      val offset = if (isClockWise) 1 else -1
      
      //modulus operation is used in case such as going from S to W
      Direction.values((index +  offset) % remainder) 
    }
}
