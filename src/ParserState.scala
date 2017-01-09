package FSMC

object ParserState extends Enumeration {
  type ParserState = Value
  val HEADER,
      HEADER_COLON,
      HEADER_VALUE,
      STATE_SPEC,
      STATE_MODIFIER,
      ENTRY_ACTION,
      EXIT_ACTION,
      STATE_EVENT,
      NEXT_STATE,
      STATE_ACTION,
      GROUP_STATE_ACTION,
      END
      = Value
}
