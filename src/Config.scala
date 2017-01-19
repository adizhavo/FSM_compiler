package FSMC

import java.io._

object Config {
  val FSMHeader = "FSM"
  val HeaderInitialState = "Initial"
  val HeaderActionType = "Actions"
  val EmptyAction = "EMPTY_ACTION"
  val JsonOutputFolder = "output"
  val GeneratedCodeFolder = "generated"

  // Add visitors IDs here
  // -----
  val CSharpVisitor = "C#"

  // -----

  def RequestVisitor (visitorCode : String) : LanguageVisitor = {
		visitorCode match {
			case Config.CSharpVisitor => return new CSharpVisitor()
			case default => return null
    }
	}
}

object Utils {
  def Output(path : String, text : String, message : String = "") {
		val file = new File(path)
		val bw = new BufferedWriter(new FileWriter(file))
		bw.write(text)
		println(message)
		bw.close()
	}
}
