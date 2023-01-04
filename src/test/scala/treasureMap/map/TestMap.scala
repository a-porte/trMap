import treasureMap.map._
import treasureMap.utils.FileHandler
import scala.util.Failure
import scala.util.Success
import scala.util.Try
import treasureMap.coordinates.Coordinates


class TestMap extends munit.FunSuite {


  test("FileHandler can read file") {
    val strToComparyWith = """C - 3 - 4
# Adventurers

M - 1 - 0
M - 2 - 1
T - 0 - 3 - 2
T - 1 - 3 - 1
A - Lara - 1 - 1 - S - AADADAGGA"""


    val str = FileHandler.readFile("input.data") match {
      case Failure(exception) => exception
      case Success(value) => value
    }

    assertEquals(str, strToComparyWith)
  }

  test("FileHandler can write in a file") {

    val toWrite = "Array,Test".split(",")
    
    val tryUnit = FileHandler.writeSeqLinesInFile("src/test/resources/output.txt", toWrite)

    assert(tryUnit.isSuccess)
  }

    test("Can instantiate Map from file name") {
    val map = PedestrianMap("input.data")

    val posToCompareWith = Coordinates(1, 1)

    val mountainsToCompareWith = Set(Coordinates(1, 0), Coordinates(2, 1))

    assertEquals(map.adventurers(0).pos, posToCompareWith)

    assertEquals(map.mountains, mountainsToCompareWith)

  }

  
}