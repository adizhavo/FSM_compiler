package FSMC
import org.json4s.JsonDSL._

class CodeGenerationNodes(_header : HeaderNode,
                          _actionEnum : ActionEnumNode,
                          _stateEnum : StateEnumNode,
                          _switchNode : SwitchNode) {
  val header = _header
  val actionEnum = _actionEnum
  val stateEnum = _stateEnum
  val switchNode = _switchNode

  val json =
    ("syntaxStructure" ->
      ("name" -> header.fsm) ~
      ("initialState" -> header.initialState) ~

      ("actionsEnum" ->
        ("type" -> actionEnum.enumType) ~
        ("values" -> actionEnum.values)) ~

      ("statesEnum" ->
        ("type" -> stateEnum.enumType) ~
        ("values" -> stateEnum.values)) ~

      ("switchCaseNode" ->
        ("currentStateCase" ->
          switchNode.currentStateCaseNodes.map { c =>
          (("stateCase") ->  c.state) ~
          ("eventCaseNode" ->
            c.eventCaseNodes.map { e =>
            (("event" -> e.event) ~
             ("nextState" -> e.nextState) ~
             ("functions" -> e.functions))})})) ~

      ("entryFunctions" ->
        switchNode.entryFunctionCaseNodes.map { e =>
        (("state" -> e.state) ~
         ("function" -> e.function))}) ~

      ("exitFunctions" ->
       switchNode.exitFunctionCaseNodes.map { e =>
       (("state" -> e.state) ~
        ("function" -> e.function))}))
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
