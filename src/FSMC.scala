package FSMC
import scala.io.Source._

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._

object FSMCApp {
	def main (args : Array[String]) {
		val paths = Utils.SearchForVisitorsId( Utils.SearchForPathsAsArg(args) )
		for (path <- paths)
			readAndCompileFromPath(path, Config.LangVisitor)
	}

	def readAndCompileFromPath(path : String, visitor : LanguageVisitor) {
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

		if (visitor == null) {
			println(Console.RED + "Language visitor not defined" + Console.RESET)
		}
		else {
			// Write generated code
			Utils.Output(Config.GeneratedFolder,
						 syntaxNodes.header.fsm + visitor.FileExtension(),
						 visitor.GenerateCode(syntaxNodes),
						 "Generated code in folder: \"" + Config.GeneratedFolder + "/\"")
		}

		if (!Config.JsonOutputFolder.isEmpty) {
			// Write the syntax structure in a json
			Utils.Output(Config.JsonOutputFolder,
						 syntaxNodes.header.fsm + ".json",
						 pretty(render(syntaxNodes.json)),
						 "Json syntax nodes in folder: \"" + Config.JsonOutputFolder + "/\"")
		}
	}
}
