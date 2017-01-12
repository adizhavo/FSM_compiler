package FSMC

class FsmSyntax {
	var headers = List[Header]()
	var transitions = List[Transitions]()
	var syntaxErrors = List[SyntaxError]()

	override def toString: String = {
		var str : String = ""

		for (_hd <- headers)
			str += "Header name: " + _hd.name + ", value: " + _hd.value + "\n"

		for (_tr <- transitions) {
			str += "State name: " + _tr.state.name + ", entry action: " + _tr.state.entryAction + ", exit action: " + _tr.state.exitAction + "\n"

			for (_sbTr <- _tr.subTransitions) {
				str += "Event: " + _sbTr.event + ", next state: " + _sbTr.nextState + ", actions: "

				for (_act <- _sbTr.actions)
					str += _act + ", "

				str += "\n"
			}
		}
		str
	}
}

class Header (_name : String, _value : String) {
	var name = _name
	var value = _value
}

class Transitions (_state : StateSpec, _subTransitions : List[SubTransition]) {
	var state = _state
	var subTransitions = _subTransitions

	override def toString: String = state.toString() + subTransitions.foreach(_.toString())
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
