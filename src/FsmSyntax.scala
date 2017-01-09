package FSMC

class FsmSyntax {
	var headers = List[Header]()
	var transitions = List[Transitions]()
	var syntaxErrors = List[SyntaxError]()

	class Header (_name : String, _value : String) {
		var name = _name
		var value = _value
	}

	class Transitions (_state : StateSpec, _subTransition : List[SubTransition]) {
		var state = _state
		var subTransition = _subTransition
	}

	class StateSpec (_name : String, _entryAction : String, _exitAction : String) {
		var name = _name
		var entryAction = _entryAction
		var exitAction = _exitAction
	}

	class SubTransition (_event : String, _nextState : String, _actions : List[String]) {
		var event = _event
		var nextState = _nextState
		var actions = _actions
	}

	class SyntaxError (_message : String) {
		var message = _message
	}
}