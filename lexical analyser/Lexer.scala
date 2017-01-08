package FSMC

import scala.util.matching.Regex

class Lexer {
	val emptySpace = " "
	val whiteSpaceRegex = new Regex("\\s+")
	val nameRegex = new Regex("\\w+")

	var lineNumber : Int = 1
	var postionInLine : Int = 0

	def Lex(fileStream : String) {
		lineNumber = 1
		var lines = fileStream.split(emptySpace)
		for (line <- lines) {
			lexLine(line)
			lineNumber += 1
		}
	}

	private def lexLine(line : String) {
		for (postionInLine <- 0 to line.length()) {
			lexToken(line)
		}
	}

	private def lexToken(line : String) = {
		if (!findToken(line)) {
			println ("notify token collector for error in line: " + lineNumber + " and position: " + postionInLine)
			postionInLine += 1
		}
	}

	private def findToken(line : String) = {
		findWhiteSpace(line) ||
		findSingleCharacterToken(line) ||
		findName(line)
	}

	private def findWhiteSpace(line : String) = {
		val matches = whiteSpaceRegex.findPrefixOf(line.substring(postionInLine)).get.length()
		if (matches > 0) {
			postionInLine += matches
			true
		}
		else false
	}

	private def findSingleCharacterToken(line : String) = {
		val char = line.substring(postionInLine, postionInLine + 1)
		char match {
			case "{" => println("notify token collector with: " + char) 
			case "}" => println("notify token collector with: " + char) 
			case ":" => println("notify token collector with: " + char) 
			case ">" => println("notify token collector with: " + char) 
			case "<" => println("notify token collector with: " + char) 
			case default => false
		}

		postionInLine += 1
		true
	}

	private def findName(line : String) = {
		val found = nameRegex.findPrefixOf(line.substring(postionInLine)).size > 0
		if (found) {
			val name = nameRegex.findPrefixOf(line.substring(postionInLine)).get
			println("notify token collector with: " + name + " name")
			postionInLine += name.length()
			true
		}
		else false
	}
}






