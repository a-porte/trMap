import treasureMap.move._

class TestMove extends munit.FunSuite {


    test("from String (lower and uppercase + improper ones) to IMoves") {
    val strMoves ="zAZdAz"

    val seqMove = MovesSequence(strMoves)

    assertEquals(seqMove.toString(), List(Move.FRONT, Move.RIGHT, Move.FRONT).toString())
  }
}