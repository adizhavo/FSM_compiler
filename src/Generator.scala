package FSMC

class Generator () {
  def Generate(_fsmSyntax : FsmSyntax, logStructure : Boolean = false) : SwitchNode = {
    if (logStructure) println(StringGeneratedStructure(_fsmSyntax))
    if (_fsmSyntax == null) null
    else return new SwitchNode(
          ExtractEventCaseNode(_fsmSyntax.transitions),
          ExtractEntryFunctionNodes(_fsmSyntax.transitions),
          ExtractExitFunctionNodes(_fsmSyntax.transitions)
     )
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

  private def StringGeneratedStructure(_fsmSyntax : FsmSyntax) : String = {
    return ""
  }

}
