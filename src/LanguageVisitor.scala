package FSMC

trait LanguageVisitor {
  def GenerateCode(codeNode : CodeGenerationNodes) : String
  def FileExtension() : String
}
