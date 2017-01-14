package FSMC
import scala.io.Source._

object FSMCApp {
	def main (args : Array[String]) {
		for (path <- args)
			readAndCompileFromPath(path)
	}

	def readAndCompileFromPath(path : String) {
		var lines = Iterator[String]()

		try {
			lines = fromFile(path).getLines
		}
		catch {
  			case e: Exception => println("File not found: " + path + ", exception message: " + e);
		}

		val builder = new ParserSyntaxBuilder()
		builder.StartNewBuild()

		val collector = new Parser(builder)
		val lexer = new Lexer(collector)
		lexer.Lex(lines)

		val generator = new Generator()
		generator.Generate(builder.lastBuild, true)
	}
}
