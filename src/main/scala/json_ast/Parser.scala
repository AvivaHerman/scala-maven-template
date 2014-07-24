package json_ast

/**
 * Created by Aviva_Herman on 7/22/14.
 */
class Parser {

  def convertToJsonObject(objStr: String): JsonObject =
    JsonObject(splitWithBalancedBrackets(objStr).map(s => convertToKeyValue(s)).toMap)

  private def isJsonFalse(str: String): Boolean = str == "false"

  private def isJsonTrue(str: String): Boolean = str == "true"

  private def isString(str: String): Boolean = str.head == '"'

  private def isJsonNull(str: String): Boolean = str == "null"

  private def isJsonInt(str: String): Boolean = str forall (c => c.isDigit)

  private def isJsonArray(str: String): Boolean = str.head == '['

  private def isJsonObject(str: String): Boolean = str.head == '{'

  private def convertToJsonValue(str: String): JsonValue =
    if (isString(str)) JsonString(str.init.tail)
    else if (isJsonTrue(str)) JsonTrue
    else if (isJsonFalse(str)) JsonFalse
    else if (isJsonNull(str)) JsonNull
    else if (isJsonArray(str)) convertToJsonArray(str)
    else if (isJsonObject(str)) convertToJsonObject(str)
    else if (isJsonInt(str)) JsonInt(str.toInt)
    else JsonDouble(str.toDouble)

  private def convertToJsonArray(str: String): JsonArray =
    JsonArray(splitWithBalancedBrackets(str).map(s => convertToJsonValue(s)))

  private def trimBrackets(str: String) = str.init.tail

  private def trimQuotes(str: String) = str.stripPrefix("\"").stripSuffix("\"")

  private def splitWithBalancedBrackets(strToParse: String): Seq[String] = {

    case class State(result: Seq[String] = Seq(), bracketsDepth: Int = 0)

    def bracketsAreBalanced(bracketsCount: Int): Boolean = bracketsCount == 0

    def bracketCounter(str: String) =
      str.filter(_ == '[').length - str.filter(_ == ']').length + str.filter(_ == '{').length - str.filter(_ == '}').length

    val separatedByCommas: Array[String] = trimBrackets(strToParse).split(",").filter(_ != "")

    separatedByCommas.foldLeft(new State) {
      (state: State, str: String) => {
        val (currentResult, bracketsDepth) = (state.result, state.bracketsDepth)

        val newResult = if (bracketsAreBalanced(bracketsDepth))
          currentResult :+ str
        else
          currentResult.init :+ s"${currentResult.last},${str}"

        val totalBracketsCount: Int = bracketsDepth + bracketCounter(str)

        State(newResult, totalBracketsCount)
      }
    }.result

  }

  private def convertToKeyValue(fieldStr: String): (String, JsonValue) = fieldStr.split(":", 2) match {
    case Array(key, value) => (trimQuotes(key) -> convertToJsonValue(value))
  }
}
