package FSMC
import scala.io.Source._

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._

object FSMCApp {
	def main (args : Array[String]) {
		val visitorId = args(0)
		for (arg <- 1 to args.length - 1)
			readAndCompileFromPath(args(arg), visitorId)
	}

	def readAndCompileFromPath(path : String, visitorId : String) {
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

		val visitor = Config.RequestVisitor(visitorId)
		// Write file in the configured folder
		Utils.Output(Config.GeneratedCodeFolder + "/" + syntaxNodes.header.fsm + visitor.FileExtension(),
					 			 visitor.GenerateCode(syntaxNodes),
					 		 	 "Generated code with visitor id:" + visitorId + " in folder: \"" + Config.GeneratedCodeFolder + "/\"")

		// FileWriter writes the syntax nodes to a json
		Utils.Output(Config.JsonOutputFolder + "/" + syntaxNodes.header.fsm + ".json",
					 			 pretty(render(syntaxNodes.json)),
					 		 	 "Json with the syntax nodes outputed in: \"" + Config.JsonOutputFolder + "/\"")
	}
}
