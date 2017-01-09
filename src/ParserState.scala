package FSMC

object ParserStates extends Enumeration {
  type ParserState = Value
  val HEADER,
      HEADER_COLON,
      HEADER_VALUE,
      ENTRY_TRANSITION,
      STATE_SPEC,
      STATE_MODIFIER,
      ENTRY_ACTION,
      EXIT_ACTION,
      STATE_EVENT,
      NEXT_STATE,
      STATE_ACTION,
      EXIT_TRANSITION,
      END
      = Value
}
