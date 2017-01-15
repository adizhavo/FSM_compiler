package FSMC

class ParserSyntaxBuilder extends SyntaxBuilder {
  var lastBuild = new FsmSyntax()

  private var passedName : String = null
  private var header : Header = null
  private var stateSpec : StateSpec = null
  private var subTransition : SubTransition = null
  private var subTransitions = List[SubTransition]()

  def SetupNewBuild() {
    lastBuild = new FsmSyntax()
    header = null
    stateSpec = null
    subTransition = null
    subTransitions = List[SubTransition]()
  }

  def NewHeaderWithName() {
    header = new Header(passedName, null)
  }

  def NewHeaderWithValue() {
    header.value = passedName
    lastBuild.headers ::= header
  }

  def SetStateName() {
    stateSpec = new StateSpec(passedName, null, null)
  }

  def SetEntryAction() {
    if (stateSpec.entryAction != null) {
      println("Multiple entry action is not supported. The entry action " + passedName + " will not be added")
      return
    }
    stateSpec.entryAction = passedName
  }

  def SetExitAction() {
    if (stateSpec.exitAction != null) {
      println("Multiple exit action is not supported. The exit action " + passedName + " will not be added")
      return
    }
    stateSpec.exitAction = passedName
  }

  def SetEvent() {
    subTransition = new SubTransition(passedName, null, List[String]())
  }

  def SetNextState() {
    subTransition.nextState = passedName
  }

  def SetAction() {
    subTransition.actions ::= passedName
    CloseTransition()
  }

  def AddAction() {
    subTransition.actions ::= passedName
  }

  def AddEmptyAction() {
    // TODO: code will generate an "EMPTY_ACTION" function, deal with this later.
    subTransition.actions ::= "EMPTY_ACTION"
  }

  def CloseTransition() {
    subTransitions ::= subTransition
  }

  def CloseSubtransitions() {
    lastBuild.transitions ::= new Transitions(stateSpec, subTransitions)
    subTransitions = List[SubTransition]()
  }

  def AddError(event : String, line : Int, pos : Int) {
    lastBuild.syntaxErrors ::= new SyntaxError("Not expecting event: " + event + " at line: " + line + " and position: " + pos)
  }

  def SetName(name : String) {
    passedName = name
  }

  def Done() {
    if (lastBuild.syntaxErrors.size > 0) {
      for (_err <- lastBuild.syntaxErrors)
        println(Console.RED + "Error detected: " + _err.message + Console.RESET)

      SetupNewBuild()
      throw new Exception("Detected syntax errors, couldn't build the syntax data structures")
    }
    else println(Console.GREEN + "File syntax built." + Console.RESET)
  }
}
