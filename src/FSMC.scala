package FSMC
import scala.io.Source._

object FSMCApp {
	def main (args : Array[String]) {
		for (path <- args) 
			readAndCompileFromPath(path)
	}

	def readAndCompileFromPath(path : String) {
		try {
			val lines = fromFile(path).getLines
			val lexer = new Lexer()
			lexer.Lex(lines)
		}
		catch {
  			case e: Exception => println("Wrong path exception: " + e);
		}
	}
}