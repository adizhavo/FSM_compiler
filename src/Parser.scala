package FSMC
import ParserEvent._
import ParserState._

class Parser extends TokenCollector {
  var parserState = HEADER

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
    handleEvent(NAME, line, pos);
  }

  def Error(line : Int, pos : Int) = {
    	println ("parser receives an error at: " + line + " and position: " + pos)
  }

  class Transition (_currenState : ParserState, _event : ParserEvent, _nextState : ParserState){ // put function call as callback
    var currentState = _currenState
    var event = _event
    var nextState = _nextState
    // vat action = _action
  }

  val transitions = Array[Transition](
    new Transition(HEADER,              NAME,         HEADER_COLON        ),
    new Transition(HEADER_COLON,        COLON,        HEADER_VALUE        ),
    new Transition(HEADER_VALUE,        NAME,         HEADER              ),
    new Transition(HEADER,              OPEN_BRACE,   STATE_SPEC          ),
    new Transition(STATE_SPEC,          NAME,         STATE_MODIFIER      ),
    new Transition(STATE_SPEC,          CLOSE_BRACE,  END                 ),
    new Transition(STATE_MODIFIER,      OPEN_ANGLE,   EXIT_ACTION         ),
    new Transition(STATE_MODIFIER,      CLOSE_ANGLE,  ENTRY_ACTION        ),
    new Transition(STATE_MODIFIER,      OPEN_BRACE,   STATE_EVENT         ),
    new Transition(ENTRY_ACTION,        NAME,         STATE_MODIFIER      ),
    new Transition(EXIT_ACTION,         NAME,         STATE_MODIFIER      ),
    new Transition(STATE_EVENT,         NAME,         NEXT_STATE          ),
    new Transition(STATE_EVENT,         CLOSE_BRACE,  STATE_SPEC          ),
    new Transition(NEXT_STATE,          NAME,         STATE_ACTION        ),
    new Transition(STATE_ACTION,        OPEN_BRACE,   GROUP_STATE_ACTION  ),
    new Transition(STATE_ACTION,        NAME,         STATE_EVENT         ),
    new Transition(STATE_ACTION,        DASH,         STATE_EVENT         ),
    new Transition(GROUP_STATE_ACTION,  NAME,         GROUP_STATE_ACTION  ),
    new Transition(GROUP_STATE_ACTION,  CLOSE_BRACE,  STATE_EVENT         ),
    new Transition(END,                 EOF,          END                 )
  )

  private def handleEvent(_event : ParserEvent, line : Int, pos : Int) {
    for (t <- transitions) {
      if (t.event == _event && t.currentState == parserState) {
        println("event received: " + _event + ", parser in state " + parserState + " transition to: " + t.nextState + ", line: " + line + " pos: " + pos)
        parserState = t.nextState
        // call the action if its not null
        return
      }
    }
    handleError(_event, line, pos)
  }

  private def handleError(_event : ParserEvent, line : Int, pos : Int) {
    println ("Error found for event " + _event + " at line " + line + " and position " + pos)
  }
}
