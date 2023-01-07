package treasureMap

import treasureMap.map.PedestrianMap
import treasureMap.utils.FileHandler

object Runner extends App {
  val mapToPlay = PedestrianMap("input.data")

  val updatedMap = mapToPlay.play().writeToFile("output.data")
}
