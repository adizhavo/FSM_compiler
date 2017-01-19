package FSMC
import java.io._
import scala.io.Source._

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._

object FSMCApp {
	def main (args : Array[String]) {
		for (path <- args)
			readAndCompileFromPath(path)
	}

	def readAndCompileFromPath(path : String) {
		var lines = Iterator[String]()

		try lines = fromFile(path).getLines
		catch {
  			case e: Exception => println("File not found: " + path + ", exception message: " + e);
		}

		// start new build
		val builder = new ParserSyntaxBuilder()
		builder.SetupNewBuild()

		// lex and build syntax structure
		val collector = new Parser(builder)
		val lexer = new Lexer(collector)
		lexer.Lex(lines)

		// Generate syntax nodes
		val generator = new Generator()
		val syntaxNodes = generator.Generate(builder.lastBuild)

		// FileWriter writes the syntax nodes to a json
		val syntaxJson = pretty(render(syntaxNodes.json))
		val file = new File(Config.OutputFolder + "/" + syntaxNodes.header.fsm + ".json")
		val bw = new BufferedWriter(new FileWriter(file))
		bw.write(syntaxJson)
		println("Json with the syntax nodes outputed in: \"" + Config.OutputFolder + "/\"")
		bw.close()
	}
}
