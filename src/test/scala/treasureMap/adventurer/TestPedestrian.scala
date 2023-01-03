
import treasureMap.coordinates._
import treasureMap.adventurer._
import treasureMap.move.MovesSequence
import treasureMap.direction.Direction

class TestPedestrian extends munit.FunSuite {


    test("from String to Adventurer ") {
    val strAdv ="Lara - 5 - 2 - S - ADAGA"

    val adv = Pedestrian(strAdv)

    val advToComparyWith = Pedestrian("Lara", 
      Coordinates(5, 2), 
      Direction("S"), 
      MovesSequence("ADAGA")
      )

    assertEquals(adv, advToComparyWith)
  }

 test("Adventurer gets a loot ") {
    val strAdv ="Lara - 5 - 2 - S - ADAGA"

    val adv = Pedestrian(strAdv)

    assertEquals(adv.treasures, 0)

    val advLoot = adv.digLoot

    assertEquals(advLoot.treasures, 1)

  }

 test("Adventurer can go South and change Direction") {
    val strAdv ="Lara - 5 - 2 - S - ADAGA"

    val adv = Pedestrian(strAdv)

    assertEquals(adv.treasures, 0)

    val advStep1 = adv.move()

    assertEquals(advStep1.orientation, Direction.SOUTH)

    val advStep2 = advStep1.move()

    assertEquals(advStep2.orientation, Direction.WEST)

  }

   test("Adventurer can go South and stops") {
    val strAdv ="Lara - 5 - 2 - S - A"

    val adv = Pedestrian(strAdv)

    assertEquals(adv.treasures, 0)

    val advStep1 = adv.move()

    assertEquals(advStep1.orientation, Direction.SOUTH)

    val advStep2 = advStep1.move()

    assertEquals(advStep2.orientation, Direction.SOUTH)

  }

  test("Adventurer can turn right towards the East ") {
    val strAdv ="Lara - 5 - 2 - S - G"

    val adv = Pedestrian(strAdv)

    assertEquals(adv.treasures, 0)

    val advStep1 = adv.move()

    assertEquals(advStep1.orientation, Direction.EAST)



  }


}