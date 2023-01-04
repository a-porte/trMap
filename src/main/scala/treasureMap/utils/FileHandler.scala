package treasureMap.utils

import java.io.{FileWriter, File, BufferedWriter}
import scala.util.Using
import scala.util.Try

object FileHandler {
  
  def writeLineInFile(name: String, line: String) : Try[Unit] = {
    Try {
      val bw = new BufferedWriter(new FileWriter(new File(name)))
      
      bw.write(line)
      bw.close()
      }
  }

  def writeSeqLinesInFile(name: String, lines: Seq[String]) : Try[Unit] = {
    Try {
      val bw = new BufferedWriter(new FileWriter(new File(name)))
      for (l <- lines) 
        bw.write(l + "\n")
      
      bw.close()
      }
  }

  def readFile(resourceFileName : String) : Try[String] = {  
    Using(io.Source.fromResource(resourceFileName)) { source => {
          val v = for (line <- source.getLines) 
              yield line.mkString

          v.mkString("\n")    
        }
      }
    }
}
