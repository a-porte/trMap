package treasureMap.move

case class MovesSequence(moves: List[Move]) {
  override def toString() : String = moves.toString()

}

object MovesSequence {

    def apply(seq: String) : MovesSequence = { 
      val charsList = seq.trim().toUpperCase.toList

      def loop(charsList : List[Char]) : List[Move] = 
          charsList match {
            case List() => List() 
            case head :: next => 
              Move.values.contains(Move(head)) match {
                case true => Move(head) :: loop(next)
                case _ => loop(next)
              } 
          }       

      new MovesSequence(loop(charsList))
    } 
  }
