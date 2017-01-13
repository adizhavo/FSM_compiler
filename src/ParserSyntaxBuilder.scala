package FSMC

class ParserSyntaxBuilder extends SyntaxBuilder {
  private var passedName : String = null

  private var fsmSyntax = new FsmSyntax()
  private var header : Header = null
  private var stateSpec : StateSpec = null
  private var subTransition : SubTransition = null
  private var subTransitions = List[SubTransition]()

  def NewHeaderWithName() {
    header = new Header(passedName, null)
  }

  def NewHeaderWithValue() {
    header.value = passedName
    fsmSyntax.headers ::= header
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
    subTransition.actions ::= "EMPTY_ACTION"
  }

  def CloseTransition() {
    subTransitions ::= subTransition
  }

  def CloseSubtransitions() {
    fsmSyntax.transitions ::= new Transitions(stateSpec, subTransitions)
    subTransitions = List[SubTransition]()
  }

  def AddError(event : String, line : Int, pos : Int) {
    fsmSyntax.syntaxErrors ::= new SyntaxError("Not expecting event: " + event + " at line: " + line + " and position: " + pos)
  }

  def SetName(name : String) {
    passedName = name
  }

  def Done() {
    println("Syntax builder received done, it built: \n" + fsmSyntax.toString())
    passedName = null
    fsmSyntax = new FsmSyntax()
    header = null
    stateSpec = null
    subTransition = null
    subTransitions = List[SubTransition]()
  }
}
