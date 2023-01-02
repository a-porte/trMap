package treasureMap.move

sealed case class  Move (value: Char) 

case object Move {
  object LEFT extends Move('G')
  object RIGHT extends Move('D')
  object FRONT extends Move('A')

  val values = Set(LEFT, RIGHT, FRONT)   

}



object m extends App {
    val l :Move = Move.RIGHT

    println(l)

    val m = l match {
      case Move.RIGHT => "I'm goind right"
      case _ => "BOOO"
    }

    println(m)

    println(Move.values)
    println(Move.values.contains(Move('L')))
}
