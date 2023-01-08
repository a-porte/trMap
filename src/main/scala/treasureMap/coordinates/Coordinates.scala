package treasureMap.coordinates

import scala.util.Try
import scala.util.Success
import scala.math.ScalaNumericConversions

case class Coordinates(x: Int, y: Int) {
  override def  toString() : String = s"$x - $y"
}



case object Coordinates {
  def apply(strX : String, strY: String) : Coordinates = 
    (Try(strX.toInt), Try(strY.toInt)) match {
      case (Success(x), Success(y)) => Coordinates(x, y)
      case _ => throw new IllegalArgumentException(
        s"COORDINATES SHOULD BE ABLE TO BE CONVERTED TO INTEGERS : $strX $strY"
        )
    }    
  

  def unapply(c: Coordinates) : (Int, Int) = (c.x, c.y)
}
