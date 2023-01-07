import treasureMap.map._
import treasureMap.utils.FileHandler
import scala.util.Failure
import scala.util.Success
import scala.util.Try
import treasureMap.coordinates.Coordinates
import treasureMap.direction.Direction
import treasureMap.adventurer.Pedestrian
import treasureMap.adventurer.Moveable


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

  test("Can move Adv and avoid obstable") {
    val map = PedestrianMap("inputAdvTowardsMountain.data")

    val adv = map.adventurers.head

    val mountainsToCompareWith = Set(Coordinates(1, 1))
    assertEquals(map.mountains, mountainsToCompareWith)

    assertEquals(adv.pos, Coordinates(1,0))

    val advMv1 = adv.move(map.obstables)

    assertEquals(advMv1.pos, Coordinates(1,0))

    val advMv2 = advMv1.move(map.obstables)

    val turnAdv = advMv2.move(map.obstables)

    val AdvIn20 = turnAdv.move(map.obstables)

    assertEquals(AdvIn20.pos, Coordinates(2,0))

  }

  test("Can move Adv and avoid each other") {
    val map = PedestrianMap("inputTwoAdv.data")

    val lara = map.adventurers.head
    assertEquals(lara.pos, Coordinates(1, 1))
    assertEquals(lara.orientation, Direction.SOUTH)


    val indie = map.adventurers.tail.head
    assertEquals(indie.pos, Coordinates(2, 2))
    assertEquals(indie.orientation, Direction.NORTH)

    val mapAfterOneTurn = map.copy(adventurers = List(lara.move(), indie.move()))

    val lara2 = mapAfterOneTurn.adventurers.head
    assertEquals(lara2.pos, Coordinates(1, 1))
    assertEquals(lara2.orientation, Direction.EAST)


    val indie2 = mapAfterOneTurn.adventurers.tail.head
    assertEquals(indie2.pos, Coordinates(2, 1))
    assertEquals(indie2.orientation, Direction.NORTH)

    val laraImmobile = lara2.move(mapAfterOneTurn.obstables)
    assertEquals(laraImmobile.pos, Coordinates(1, 1))
    assertEquals(laraImmobile.orientation, Direction.EAST)

  }

    test("Can play Adv ") {
    val map = PedestrianMap("inputAdvTowardsMountain.data")

    val lara = map.adventurers.head
    assertEquals(lara.pos, Coordinates(1, 0))
    assertEquals(lara.orientation, Direction.SOUTH)

    val newMap = map.play()

    val laraAtTheEnd = newMap.adventurers.head
    assertEquals(laraAtTheEnd.pos, Coordinates(2, 0))
    assertEquals(laraAtTheEnd.orientation, Direction.EAST)

  }

    test("Can play 2 advs") {
    val map = PedestrianMap("inputTwoAdvMeet.data")

    val lara = map.adventurers.head
    assertEquals(lara.pos, Coordinates(1, 1))
    assertEquals(lara.orientation, Direction.SOUTH)


    val indie = map.adventurers.tail.head
    assertEquals(indie.pos, Coordinates(2, 2))
    assertEquals(indie.orientation, Direction.NORTH)

    val newMap = map.play()


    val lara2 = newMap.adventurers.head
    assertEquals(lara2.pos, Coordinates(1, 1))
    assertEquals(lara2.orientation, Direction.EAST)


    val indie2 = newMap.adventurers.tail.head
    assertEquals(indie2.pos, Coordinates(2, 0))
    assertEquals(indie2.orientation, Direction.NORTH)


  }


  test("Adv can get a loot an the map is updated") {
    val map = PedestrianMap("inputAdvLoots.data")

    val lara = map.adventurers.head

    val initialsTreasures = lara.toString().reverse.head.toInt - '0'.toInt
    val treasuresOnMap = map.treasures.map{ case (coor, nb) => nb}.reduce(_ + _)

    assertEquals(initialsTreasures, 0)
    assertEquals(lara.pos, Coordinates(1, 0))
    assertEquals(lara.orientation, Direction.SOUTH)
    assertEquals(treasuresOnMap, 10)


    val newMap = map.play()


    val newLara = newMap.adventurers.head
    val treasures = newLara.toString().reverse.head.toInt - '0'.toInt
    //lara is seen as Moveable and not a Pedestrian ... Variance problem ?
    //As a consequence, we need to trick a little bit to know how many treasures she's got
    val treasuresOnNewMap = newMap.treasures.map{ case (coor, nb) => nb}.reduce(_ + _)

    println(treasuresOnNewMap)
    assertEquals(newLara.pos, Coordinates(2, 2))
    assertEquals(newLara.orientation, Direction.EAST)
    assertEquals(treasures, 1)
    assertEquals(treasuresOnNewMap, 9)
  }

  
}