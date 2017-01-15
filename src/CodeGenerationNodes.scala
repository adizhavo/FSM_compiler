package FSMC

class CodeGenerationNodes(_header : HeaderNode,
                          _actionEnum : ActionEnumNode,
                          _stateEnum : StateEnumNode,
                          _switchNode : SwitchNode) {
  val header = _header
  val actionEnum = _actionEnum
  val stateEnum = _stateEnum
  val switchNode = _switchNode
}

class HeaderNode(_fsm : String,  _initialState : String) {
  val fsm = _fsm
  val initialState = _initialState
}

class ActionEnumNode (_enumType : String, _values : List[String]) {
  val enumType  = _enumType
  val values = _values
}

class StateEnumNode (_enumType : String,  _values : List[String]) {
  val enumType  = _enumType
  val values = _values
}

class SwitchNode (_currentStateCaseNodes : List[CurretStateCaseNode],
                  _entryFunctionCaseNodes : List[EntryFunctionNode],
                  _exitFunctionCaseNodes : List[ExitFunctionNode]) {

  val currentStateCaseNodes = _currentStateCaseNodes
  val entryFunctionCaseNodes = _entryFunctionCaseNodes
  val exitFunctionCaseNodes = _exitFunctionCaseNodes
}

class CurretStateCaseNode (_state : String, _eventCaseNodes : List[EventCaseNode]) {
  val state = _state
  val eventCaseNodes = _eventCaseNodes
}

class EventCaseNode (_event : String, _nextState : String, _functions : List[String]) {
  val event = _event
  val nextState = _nextState
  val functions = _functions
}

class EntryFunctionNode (_state : String, _function : String) {
  val state = _state
  val function = _function
}

class ExitFunctionNode (_state : String, _function : String) {
  val state = _state
  val function = _function
}
