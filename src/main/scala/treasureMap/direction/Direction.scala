package treasureMap.direction

sealed case class Direction(value : String)

case object Direction{
  object SOUTH extends Direction("S")
  object NORTH extends Direction("N")
  object WEST extends Direction("O")
  object EAST extends Direction("E")

  val values = Set(SOUTH, NORTH, WEST, EAST)
}
