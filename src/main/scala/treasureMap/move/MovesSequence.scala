package treasureMap.move

case class MovesSequence(moves: List[Move]) {


}

object MovesSequence {

    def fromStringToMoves(seq: String) : List[Move] = { 
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

      loop(charsList)
    } 
}