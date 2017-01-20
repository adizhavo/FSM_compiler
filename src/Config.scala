package FSMC

object Config {
  // Add visitors here
  val SupportedVisistors = Map[String, LanguageVisitor](
    "C#" -> new CSharpVisitor()
  )
  // -----

  def RequestVisitor (visitorCode : String, throwErrorMessages : Boolean = true) : LanguageVisitor = {
		for (sv <- SupportedVisistors)
      if (sv._1 == visitorCode)
        return sv._2

    if (throwErrorMessages) {
      println(Console.RED + visitorCode + " visitor specified is not supported." + Console.RESET)
      PrintSupportedVisitors()
    }
    return null
	}

  def PrintSupportedVisitors() {
    println(Console.CYAN + "Supported visitors are:")
    for (sv <- SupportedVisistors) println(sv._1)
    println(Console.RESET)
  }

  val FSMHeader = "FSM"
  val HeaderInitialState = "Initial"
  val HeaderActionType = "Actions"
  val EmptyAction = "EMPTY_ACTION"
  var JsonOutputFolder = ""
  var GeneratedFolder = "./"
  var LangVisitor : LanguageVisitor = null

  val ChangeJsonOutputFolderCommand = "json"
  val ChangeGeneratedFolderCommand = "gen"
}

object Utils {
  def SearchForPathsAsArg(args : Array[String]) : List[String] = {
    var pathArguments = List[String]()
    for (arg <- args) {
      val command = arg.split("=")

      if (command(0) == Config.ChangeJsonOutputFolderCommand) {
        Config.JsonOutputFolder = command(1)
        println(Console.BOLD + "Set json output folder to: " + command(1) + Console.RESET);
      }
      else if (command(0) == Config.ChangeGeneratedFolderCommand) {
        Config.GeneratedFolder = command(1)
        println(Console.BOLD + "Set generated folder to: " + command(1) + Console.RESET);
      }
      else pathArguments ::= arg
    }
    pathArguments
  }

  def SearchForVisitorsId (args : List[String]) = {
    var pathArguments = List[String]()
    for (arg <- args) {
      var visitor =  Config.RequestVisitor(arg, false)
      if (visitor != null) Config.LangVisitor = visitor
      else pathArguments ::= arg
    }
    pathArguments
  }

  def Output(directory : String, file : String, text : String, message : String = "") {
		val dir = new java.io.File(directory)
    dir.mkdir()
		val bw = new java.io.BufferedWriter(new java.io.FileWriter(dir + "/" + file))
		bw.write(text)
		println(message)
		bw.close()
	}
}
