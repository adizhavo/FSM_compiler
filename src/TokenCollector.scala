package FSMC

trait TokenCollector {
  def OpenBrace(line : Int, pos : Int)
  def CloseBrace(line : Int, pos : Int)
  def OpenAngle(line : Int, pos : Int)
  def CloseAngle(line : Int, pos : Int)
  def Colon(line : Int, pos : Int)
  def Dash(line : Int, pos : Int)
  def Name(name : String, line : Int, pos : Int)
  def Error(line : Int, pos : Int)
  def EndOfFile()
}
