package FSMC

object Config {
  val FSMHeader = "FSM"
  val HeaderInitialState = "Initial"
  val HeaderActionType = "Actions"
  val EmptyAction = "EMPTY_ACTION"
  val JsonOutputFolder = "output"
  val GeneratedCodeFolder = "generated"

  // Add visitors here
  val SupportedVisistors = Map[String, LanguageVisitor](
    "C#" -> new CSharpVisitor()
  )
  // -----

  def RequestVisitor (visitorCode : String) : LanguageVisitor = {
		for (sv <- SupportedVisistors)
      if (sv._1 == visitorCode)
        return sv._2

    println(Console.RED + visitorCode + " visitor specified is not supported.")
    println(Console.CYAN + "Suppoted visitors are:")
    for (sv <- SupportedVisistors) println(sv._1)
    println(Console.RESET)
    return null
	}
}

object Utils {
  def Output(path : String, text : String, message : String = "") {
		val file = new java.io.File(path)
		val bw = new java.io.BufferedWriter(new java.io.FileWriter(file))
		bw.write(text)
		println(message)
		bw.close()
	}
}
