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

			val builder = new ParserSyntaxBuilder()
			val collector = new Parser(builder)
			val lexer = new Lexer(collector)
			lexer.Lex(lines)
		}
		catch {
  			case e: Exception => println("File not found: " + path + ", exception message: " + e);
		}
	}
}
