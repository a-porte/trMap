package treasureMap.move

case class MovesSequence(moves: List[Move]) {


}

object MovesSequence {
    def fromStringToMoves(seq: String) : List[Move] = { //rename to apply ?
      val l = List(Move(""))

      val charsList = seq.trim().toList

      def loop(charsList : List[Char]) : List[Move] = 
          charsList match {
            case List() => List() 
            // TODO use a guard to enforce legal moves
            case head :: next => Move(head.toString()) :: loop(next)
          }       

      loop(charsList)
    } 
}
