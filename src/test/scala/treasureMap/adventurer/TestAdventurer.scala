import treasureMap.adventurer._
import fr.ap.treasureMap.Coordinates
import treasureMap.move.MovesSequence

class TestAdventurer extends munit.FunSuite {


    test("from String to Adventurer ") {
    val strAdv ="Lara - 5 - 2 - S - ADAGA"

    val adv = Adventurer(strAdv)

    val advToComparyWith = Adventurer("Lara", 
      Coordinates(5, 2), 
      "S", 
      MovesSequence("ADAGA")
      )

    assertEquals(adv, advToComparyWith)
  }
}