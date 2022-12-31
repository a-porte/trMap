import treasureMap.move._

class TestMove extends munit.FunSuite {

  test("from String to Moves") {
    val strMoves ="ADA"

    val seqMove = MovesSequence.fromStringToMoves(strMoves)

    assertEquals(seqMove, List(Move("A"), Move("D"), Move("A")))
  }
}