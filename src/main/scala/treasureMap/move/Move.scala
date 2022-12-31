package treasureMap.move

sealed case class  Move (value: Char) 

case object Move {
  object LEFT extends Move('G')
  object RIGHT extends Move('D')
  object FRONT extends Move('A')

  val values = Set(LEFT, RIGHT, FRONT)   

}
