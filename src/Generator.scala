package FSMC
import Config._

class Generator () {
  def Generate(_fsmSyntax : FsmSyntax) : CodeGenerationNodes = {
    if (_fsmSyntax == null) {
      println(Console.RED + "Passed null syntax data structure to the generator" + Console.RESET)
      null
    }
    else {
      println(Console.GREEN + "Generated syntax nodes" + Console.RESET)
      new CodeGenerationNodes(
        ExtractHeaderNode(_fsmSyntax),
        ExtractActionEnumNode(_fsmSyntax),
        ExtractStateEnumNode(_fsmSyntax),
        new FSMNode(
              ExtractEventCaseNode(_fsmSyntax.transitions),
              ExtractEntryFunctionNodes(_fsmSyntax.transitions),
              ExtractExitFunctionNodes(_fsmSyntax.transitions)))
     }
  }

  private def ExtractHeaderNode(_fsmSyntax : FsmSyntax) = {
    var fsmName = ""
    var initialState = ""
    for (_hd <- _fsmSyntax.headers)
      if (_hd.name == FSMHeader) fsmName = _hd.value
      else if (_hd.name == HeaderInitialState) initialState = _hd.value

    if (fsmName.length() == 0) throw new Exception("\""+ FSMHeader +"\" key is missing")
    else if (initialState.length() == 0) throw new Exception("\""+ HeaderInitialState +"\" key is missing")

    new HeaderNode(fsmName, initialState)
  }

  private def ExtractActionEnumNode(_fsmSyntax : FsmSyntax) = {
    var actionEnumType = ""
    for (_hd <- _fsmSyntax.headers)
      if (_hd.name == HeaderActionType) actionEnumType = _hd.value

    if (actionEnumType.length() == 0) throw new Exception("\""+ HeaderActionType +"\" key is missing")

    var enumValues = List[String]()

    for (_tr <- _fsmSyntax.transitions)
      for (_sbTr <- _tr.subTransitions)
        if (!enumValues.contains(_sbTr.event))
          enumValues ::= _sbTr.event

    new ActionEnumNode(actionEnumType, enumValues)
  }

  private def ExtractStateEnumNode(_fsmSyntax : FsmSyntax) = {
    var stateEnumType = ""
    for (_hd <- _fsmSyntax.headers)
      if (_hd.name == FSMHeader) stateEnumType = _hd.value + "State"

    if (stateEnumType.length() == 0) throw new Exception("\""+ FSMHeader +"\" key is missing")

    var enumValues = List[String]()
    for (_hd <- _fsmSyntax.headers)
      if (_hd.name == HeaderInitialState) enumValues ::= _hd.value

      for (_tr <- _fsmSyntax.transitions) {
        if (!enumValues.contains(_tr.state.name))
          enumValues ::= _tr.state.name

        for (_sbTr <- _tr.subTransitions)
          if (!enumValues.contains(_sbTr.nextState))
            enumValues ::= _sbTr.nextState
      }

    new StateEnumNode(stateEnumType, enumValues)
  }

  private def ExtractEventCaseNode(transitions : List[Transitions]) = {
    var switchCaseState = List[CurretStateCaseNode]()

    for (_tr <- transitions) {
      var eventCases = List[EventCaseNode]()
      for (_sbTr <- _tr.subTransitions)
        eventCases ::= new EventCaseNode(_sbTr.event, _sbTr.nextState, _sbTr.actions)

      var singleCase = new CurretStateCaseNode(_tr.state.name, eventCases)
      switchCaseState ::= singleCase
    }

    switchCaseState
  }

  private def ExtractEntryFunctionNodes(transitions : List[Transitions]) = {
    var entryFunctions = List[EntryFunctionNode]()
    for (_tr <- transitions)
      entryFunctions ::= new EntryFunctionNode(_tr.state.name, _tr.state.entryAction)
    entryFunctions
	}

  private def ExtractExitFunctionNodes(transitions : List[Transitions]) = {
    var extitFunctions = List[ExitFunctionNode]()
    for (_tr <- transitions)
      extitFunctions ::= new ExitFunctionNode(_tr.state.name, _tr.state.exitAction)
    extitFunctions
	}
}
