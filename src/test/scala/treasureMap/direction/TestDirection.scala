import treasureMap.direction._
import treasureMap.direction.Direction.EAST
import treasureMap.direction.Direction.NORTH
import treasureMap.direction.Direction.SOUTH
import treasureMap.direction.Direction.WEST

class TestDirection extends munit.FunSuite {


    test("from String to Direction.SOUTH") {

    val d = Direction("S")

  
    assertEquals(d, Direction.SOUTH)
  }
}