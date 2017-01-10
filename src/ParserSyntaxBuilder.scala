package FSMC

class ParserSyntaxBuilder extends SyntaxBuilder {
  def NewHeaderWithName() {
    println("Syntax builder received header name")
  }

  def NewHeaderWithValue() {
    println("Syntax builder received header with value")
  }

  def SetStateName() {
    println("Syntax builder received state name")
  }

  def SetEntryAction() {
    println("Syntax builder received entry action")
  }

  def SetExitAction() {
    println("Syntax builder received exit action")
  }

  def SetEvent() {
    println("Syntax builder received event")
  }

  def SetNextState() {
    println("Syntax builder received next state")
  }

  def AddAction() {
    println("Syntax builder received action add")
  }

  def AddEmptyAction() {
    println("Syntax builder received empty action")
  }

  def AddError(line : Int, pos : Int) {
    println("Syntax builder received error")
  }

  def SetName(name : String) {
    println("Syntax builder received name: " + name)
  }

  def Done() {
    println("Syntax builder received done")
  }
}
