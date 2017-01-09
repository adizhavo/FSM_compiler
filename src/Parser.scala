package FSMC

class Parser extends TokenCollector {
  def OpenBrace(line : Int, pos : Int) = {
    println("parser receives an {")
  }

  def CloseBrace(line : Int, pos : Int) = {
    println("parser receives a }")
  }

  def OpenAngle(line : Int, pos : Int) = {
    println("parser receives a <")
  }

  def CloseAngle(line : Int, pos : Int) = {
    println("parser receives a >")
  }

  def Colon(line : Int, pos : Int) = {
    println("parser receives a :")
  }

  def Name(name : String, line : Int, pos : Int) = {
    println("parser receives the name: " + name)
  }

  def Error(line : Int, pos : Int) = {
    	println ("parser receives an error at: " + line + " and position: " + pos)
  }
}
