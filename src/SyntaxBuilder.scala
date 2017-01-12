package FSMC

trait SyntaxBuilder {
  def NewHeaderWithName()
  def NewHeaderWithValue()
  def SetStateName()
  def SetEntryAction()
  def SetExitAction()
  def SetEvent()
  def SetNextState()
  def SetAction()
  def AddAction()
  def AddEmptyAction()
  def CloseTransition()
  def CloseSubtransitions()
  def AddError(line : Int, pos : Int)
  def SetName(name : String)
  def Done()
}
