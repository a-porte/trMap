package treasureMap.map

import scala.collection.mutable.HashMap

import treasureMap.coordinates.Coordinates
import treasureMap.adventurer.{Moveable, Pedestrian}
import java.io.{BufferedWriter, File, FileWriter}
import scala.util.Failure
import scala.util.Success
import treasureMap.utils.FileHandler
import scala.annotation.tailrec
import scala.util.matching.Regex


trait Mapable {
  def mountains : Set[Coordinates]
  def width : Int
  def heigth : Int

  def treasures : HashMap[Coordinates, Int]

  def adventurers : List[Moveable]
}

case class PedestrianMap (
  width: Int = 0, 
  heigth: Int = 0,  
  treasures: HashMap[Coordinates,Int] = HashMap(),
  adventurers: List[Moveable] = List(),
  mountains: Set[Coordinates] = Set()
  ) extends  Mapable {




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
              PedestrianMap(width = w.toInt, heigth = h.toInt)
              iter(next, PedestrianMap(width = w.toInt, heigth = h.toInt))
            
            case mountainPattern(x, y) => 
              iter(next, accu.copy(mountains = accu.mountains + (Coordinates(x, y))))
            
            case treasurePattern(x, y, nb) => 
              iter(next, accu.copy(treasures = accu.treasures +(Coordinates(x, y)-> nb.toInt)) )
            
            case advPattern(name, x, y, or, moves) => 
              iter(next, accu.copy(adventurers = List(Pedestrian(name, x, y, or, moves)))) 
              
            case commentPattern => iter(next, accu)
          }
        }
      }
    }
    
    iter(strMap, PedestrianMap())

  }
}
