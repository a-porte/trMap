package treasureMap.map

import treasureMap.adventurer.Moveable
import scala.collection.immutable
import treasureMap.coordinates.Coordinates
import scala.annotation.tailrec
import treasureMap.adventurer.MoveHandler



trait Mapable {

  def mountains : Set[Coordinates]
  def width : Int
  def heigth : Int

  def treasures : immutable.HashMap[Coordinates, Int]

  def adventurers : List[Moveable]


  def negativeCoord : Set[Coordinates]= (
    for { 
      i <- -1 until width
      j <- -1 until heigth
      if (i == -1 || j == -1) 
     } yield Coordinates(i, j)
    ).to(Set)

  def excessiveCoord : Set[Coordinates] = (
    for { 
      i <- 0 until width+1
      j <- 0 until heigth+1 
      if i == width || j == heigth
     } yield Coordinates(i, j)
    ).to(Set)

  def incorrectCoord : Set[Coordinates] = excessiveCoord ++ negativeCoord

  def obstables : Coordinates => Boolean = 
    (posToCheck : Coordinates) => (
      incorrectCoord ++ mountains ++ adventurers.map(_.pos).to(Set)
      )(posToCheck)

    val treasuresCoorFun = (posToCheck: Coordinates) => (
    treasures.filter(_._2 > 0)
    .map{
      case (coor, nb) => coor
    }
    .to(Set))(posToCheck)

  def play() : Mapable = {
    @tailrec
    def iter(recMap: Mapable) : Mapable = {
      val hasMovesLeftToPlay = !recMap.adventurers.filter(_.moves.moves.length > 0).isEmpty

      println(recMap)

      hasMovesLeftToPlay match {
        case false => recMap

        case true => {
          val treasuresBeforeAdvBeingMoved = recMap.treasures

          val updatedAvd : List[Moveable] = recMap.adventurers.map(
            _.move(recMap.obstables, recMap.treasuresCoorFun)
            )
          
          val advInitialPos = recMap.adventurers.map(_.pos)
          val updatedAdvPositions = updatedAvd.map(_.pos) 

          val changedPositions = (
            (
              advInitialPos zip updatedAdvPositions // we zip together positions before and after moves
              ) filter {
              case (ini, updated) => ini != updated // we only keep the position which have been changed
              }
            ) 
            .map {
              case (ini, updated) => updated // and we unzip then
            } 

          val updatedPosWithUpdatedTreasures = treasuresBeforeAdvBeingMoved.filter{
            //firstly we want to deal exclusively with new adventurers's positions
            case(coord, nb) => changedPositions.contains(coord)
            }
            .filter { // we want to deal with treasures count stricly above 0
              case(k, v) => v > 0
            }
            .map { // then we update the number of treasures
              case(k, v) => (k, v - MoveHandler.amountDiggedAtATime) 
            }

          //treasures are contained on HashMap, so we can replace existing values on the fly thanks to ++ method
          iter(recMap.copyMapable(updatedAvd, treasuresBeforeAdvBeingMoved ++ updatedPosWithUpdatedTreasures))
        }
      }
    }
    iter(this)
  }

  def copyMapable(m : List[Moveable], t:  immutable.HashMap[Coordinates, Int]) : Mapable

  override def toString(): String = {
      val strSize = s"C - $width - $heigth"

    val strTreasures =  treasures.map{case (coord, nb) => s"T - $coord - $nb" }.mkString("\n")

    val strMountains =  mountains.map(c => "M - " + c).mkString("\n")

    val strAdv = adventurers.map(_.toString()).mkString("\n")

    val strMap : String = ( strSize :: strMountains :: strTreasures ::   strAdv :: Nil).mkString("\n")

    s"$strMap\n"
    
  }


}