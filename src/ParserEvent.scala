package FSMC

object ParserEvents extends Enumeration {
  type ParserEvent = Value
  val NAME,
      OPEN_BRACE,
      CLOSE_BRACE,
      OPEN_ANGLE,
      CLOSE_ANGLE,
      COLON,
      EOF
      = Value
}
