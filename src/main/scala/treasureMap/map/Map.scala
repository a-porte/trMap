package treasureMap.map


import treasureMap.coordinates.Coordinates
import treasureMap.adventurer.{Moveable, Pedestrian, Adventurer}
import java.io.{BufferedWriter, File, FileWriter}
import scala.util.{Failure, Success}
import treasureMap.utils.FileHandler
import scala.annotation.tailrec
import scala.util.matching.Regex
import scala.collection.immutable


trait Mapable {
  def mountains : Set[Coordinates]
  def width : Int
  def heigth : Int

  def treasures : immutable.HashMap[Coordinates, Int]

  def adventurers : List[Moveable]

  def obstables : Coordinates => Boolean = 
    (posToCheck : Coordinates) => (mountains ++ adventurers.map(_.pos).to(Set))(posToCheck)

  
  val treasuresCoorFun = (posToCheck: Coordinates) => (
    treasures.map{
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
          
          val updatedAdvPositions = updatedAvd.map(_.pos)

          val updatedPosWithUpdatedTreasures = treasuresBeforeAdvBeingMoved.filter{
            //firstly we want to deal with new adventurers's positions
            case(coord, nb) => updatedAdvPositions.contains(coord)
            }
            .map { // then we update the number of treasures
              case(k, v) => (k, v -1)
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
 

    val strSize = s"C - $width - $heigth\n"

    val strTreasures =  treasures.map{case (coord, nb) => s"T - $coord - $nb" }.mkString("\n")

    val strMountains =  mountains.map(c => "M - " + c).mkString("\n")

    val strAdv = adventurers.map(_.toString()).mkString("\n")

    val strMap : String = ( strSize :: strMountains :: strTreasures ::   strAdv :: Nil).mkString("\n")

    strMap
    
  }
  
}

case class PedestrianMap (
  width: Int = 0, 
  heigth: Int = 0,  
  treasures: immutable.HashMap[Coordinates,Int] = immutable.HashMap(),
  adventurers: List[Moveable] = List(),
  mountains: Set[Coordinates] = Set()
  ) extends  Mapable {

  override def copyMapable(advToUpdate: List[Moveable],treasuresToUpdate : immutable.HashMap[Coordinates, Int]): Mapable = {
    this.copy(adventurers = advToUpdate, treasures = treasuresToUpdate)}

}



object PedestrianMap {
  private def readFile(fileName : String, funTreatment : String => Seq[String]) : Seq[String] = {
    FileHandler.readFile(fileName) match {
      case Failure(exception) => {
        println("MAP CANNOT BE INSTANTIATED BECAUSE THE FILE CANNOT BE READ CORRECTLY")
        throw new ExceptionInInitializerError(exception)
      }
      case Success(value) => funTreatment(value)
    }
  }

  

  def apply(fileName : String) : PedestrianMap = {
    val funToApplyToReadString : String => Seq[String] = 
        _.replace(" ", "").split("\n").toList

    val strMap : Seq[String] = readFile(fileName, funToApplyToReadString)

    @tailrec
    //more idiomatic way to parse the read lines ?
    def iter(args : Seq[String], accu : PedestrianMap) : PedestrianMap =  {
      args match {
        case Nil => accu
        case head :: next => {

          val sizePattern : Regex = """C-(\d)-(\d)""".r
          val mountainPattern : Regex = """M-(\d)-(\d)""".r
          val treasurePattern : Regex = """T-(\d)-(\d)-(\d)""".r
          val advPattern : Regex = """A-(\w+)-(\d)-(\d)-(\w)-(\w+)""".r
          val commentPattern = """(#| ).*""".r

          head match {
            case sizePattern(w, h) => 
              accu.copy(width = w.toInt, heigth = h.toInt)
              iter(next, PedestrianMap(width = w.toInt, heigth = h.toInt))
            
            case mountainPattern(x, y) => 
              iter(next, accu.copy(mountains = accu.mountains + (Coordinates(x, y))))
            
            case treasurePattern(x, y, nb) => 
              iter(next, accu.copy(treasures = accu.treasures +(Coordinates(x, y)-> nb.toInt)) )
            
            case advPattern(name, x, y, or, moves) => 
              iter(next, accu.copy(adventurers = accu.adventurers :+ Pedestrian(name, x, y, or, moves)  ))
              // :+ appends, :: prepends
              
            case commentPattern => iter(next, accu)
          }
        }
      }
    }
    
    iter(strMap, PedestrianMap())

  }
}
