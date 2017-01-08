package FSMC
import scala.util.matching.Regex

class Lexer {
	val whiteSpaceRegex = new Regex("\\s+")
	val nameRegex = new Regex("\\w+")

	var lineNumber : Int = 1
	var postionInLine : Int = 0

	def Lex(lines : Iterator[String]) {
		lineNumber = 1
		for (line <- lines) {
			lexLine(line)
			lineNumber += 1
		}
	}

	private def lexLine(line : String) {
		postionInLine = 0
		while (postionInLine < line.length()) {
			lexToken(line)
		}
	}

	private def lexToken(line : String) = {
		if (!findToken(line)) {
			println ("notify token collector for error in line: " + lineNumber + " and position: " + postionInLine + " " + line.length())
			postionInLine += 1
		}
	}

	private def findToken(line : String) = {
		findWhiteSpace(line) ||
		findSingleCharacterToken(line) ||
		findName(line)
	}

	private def findWhiteSpace(line : String) = {
		if (postionInLine < line.length()) {
			val foundWhiteSpace = whiteSpaceRegex.findPrefixOf(line.substring(postionInLine)).size > 0
			if (foundWhiteSpace) {
				val matches = whiteSpaceRegex.findPrefixOf(line.substring(postionInLine)).get.length()
				if (matches > 0) {
					postionInLine += matches
					true
				} else false
			} else false
		} else false
	}

	private def findSingleCharacterToken(line : String) = {
		if (postionInLine < line.length()) {
			val char = line.substring(postionInLine, postionInLine + 1)
			var notFound = false
			char match {
				case "{" => println("notify token collector with: " + char) 
				case "}" => println("notify token collector with: " + char) 
				case ":" => println("notify token collector with: " + char) 
				case ">" => println("notify token collector with: " + char) 
				case "<" => println("notify token collector with: " + char) 
				case default => notFound = true
			}
			if (notFound) false
			else {
				postionInLine += 1
				true
			}
		}
		else false
	}

	private def findName(line : String) = {
		if (postionInLine < line.length()) {
			val found = nameRegex.findPrefixOf(line.substring(postionInLine)).size > 0
			if (found) {
				val name = nameRegex.findPrefixOf(line.substring(postionInLine)).get
				println("notify token collector with: " + name + " name at line: " + lineNumber + " and position: " + postionInLine)
				postionInLine += name.length()
				true
			}
			else false
		} else false
	}
}