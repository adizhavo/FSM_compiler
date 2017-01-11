package FSMC
import ParserEvent._
import ParserState._

class Parser (_builder : SyntaxBuilder)  extends TokenCollector {
  var parserState = HEADER
  var builder = _builder

  def OpenBrace(line : Int, pos : Int) = {
    handleEvent(OPEN_BRACE, line, pos);
  }

  def CloseBrace(line : Int, pos : Int) = {
    handleEvent(CLOSE_BRACE, line, pos);
  }

  def OpenAngle(line : Int, pos : Int) = {
    handleEvent(OPEN_ANGLE, line, pos);
  }

  def CloseAngle(line : Int, pos : Int) = {
    handleEvent(CLOSE_ANGLE, line, pos);
  }

  def Colon(line : Int, pos : Int) = {
    handleEvent(COLON, line, pos);
  }

  def Dash(line : Int, pos : Int) = {
    handleEvent(DASH, line, pos);
  }

  def Name(name : String, line : Int, pos : Int) = {
    builder.SetName(name)
    handleEvent(NAME, line, pos);
  }

  def EndOfFile() = {
    handleEvent(EOF, -1, -1)
  }

  def Error(line : Int, pos : Int) = {
    	println ("parser receives an error at: " + line + " and position: " + pos)
  }

  class Transition (_currenState : ParserState, _event : ParserEvent, _nextState : ParserState, _action : () => Unit) {
    var currentState = _currenState
    var event = _event
    var nextState = _nextState
    var action = _action
  }

  val transitions = Array[Transition](
    new Transition(HEADER,              NAME,         HEADER_COLON,        builder.NewHeaderWithName),
    new Transition(HEADER_COLON,        COLON,        HEADER_VALUE,        null),
    new Transition(HEADER_VALUE,        NAME,         HEADER,              builder.NewHeaderWithValue),
    new Transition(HEADER,              OPEN_BRACE,   STATE_SPEC,          null),
    new Transition(STATE_SPEC,          NAME,         STATE_MODIFIER,      builder.SetStateName),
    new Transition(STATE_SPEC,          CLOSE_BRACE,  END,                 builder.Done),
    new Transition(STATE_MODIFIER,      OPEN_ANGLE,   EXIT_ACTION,         null),
    new Transition(STATE_MODIFIER,      CLOSE_ANGLE,  ENTRY_ACTION,        null),
    new Transition(STATE_MODIFIER,      OPEN_BRACE,   STATE_EVENT,         null),
    new Transition(ENTRY_ACTION,        NAME,         STATE_MODIFIER,      builder.SetEntryAction),
    new Transition(EXIT_ACTION,         NAME,         STATE_MODIFIER,      builder.SetExitAction),
    new Transition(STATE_EVENT,         NAME,         NEXT_STATE,          builder.SetEvent),
    new Transition(STATE_EVENT,         CLOSE_BRACE,  STATE_SPEC,          null),
    new Transition(NEXT_STATE,          NAME,         STATE_ACTION,        builder.SetNextState),
    new Transition(STATE_ACTION,        OPEN_BRACE,   GROUP_STATE_ACTION,  null),
    new Transition(STATE_ACTION,        NAME,         STATE_EVENT,         builder.AddAction),
    new Transition(STATE_ACTION,        DASH,         STATE_EVENT,         builder.AddEmptyAction),
    new Transition(GROUP_STATE_ACTION,  NAME,         GROUP_STATE_ACTION,  builder.AddAction),
    new Transition(GROUP_STATE_ACTION,  CLOSE_BRACE,  STATE_EVENT,         null),
    new Transition(END,                 EOF,          END,                 null)
  )

  private def handleEvent(_event : ParserEvent, line : Int, pos : Int) {
    for (t <- transitions) {
      if (t.event == _event && t.currentState == parserState) {
        parserState = t.nextState
        if (t.action != null) t.action()
        return
      }
    }
    handleError(_event, line, pos)
  }

  private def handleError(_event : ParserEvent, line : Int, pos : Int) = {
    println ("Error found for event " + _event + " at line " + line + " and position " + pos)
  }
}
