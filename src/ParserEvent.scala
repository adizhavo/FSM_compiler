package FSMC

object ParserEvent extends Enumeration {
  type ParserEvent = Value
  val NAME,
      OPEN_BRACE,
      CLOSE_BRACE,
      OPEN_ANGLE,
      CLOSE_ANGLE,
      COLON,
      DASH,
      EOF
      = Value
}
