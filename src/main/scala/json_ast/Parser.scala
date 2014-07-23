package json_ast

/**
 * Created by Aviva_Herman on 7/22/14.
 */
class Parser {

  private def isJsonFalse(str: String): Boolean = {
    str == "false"
  }

  private def isJsonTrue(str: String): Boolean = {
    str == "true"
  }

  private def isString(str: String): Boolean = {
    str.head == '\"'
  }

  private def isJsonNull(str: String): Boolean = {
    str == "null"
  }

  private def isJsonInt(str: String): Boolean = {
    str forall (c => c.isDigit)
  }

  private def isJsonArray(str: String): Boolean = {
    str.head == '['
  }

  private def isJsonObject(str: String): Boolean = {
    str.head == '{'
  }

  private def convertToJsonValue(str: String): JsonValue = {
    if (isString(str))          JsonString(str.init.tail)
    else if (isJsonTrue(str))   JsonTrue
    else if (isJsonFalse(str))  JsonFalse
    else if (isJsonNull(str))   JsonNull
    else if (isJsonArray(str))  convertToJsonArray(str)
    else if (isJsonObject(str)) convertToJsonObject(str)
    else if (isJsonInt(str))    JsonInt(str.toInt)
    else                        JsonDouble(str.toDouble)
  }


  private def convertToJsonArray(str: String): JsonArray = {
    JsonArray(splitWithBalancedBrackets(str).map(s => convertToJsonValue(s)))
  }

  private def trimEdges(str: String) = {
    str.init.tail
  }

  private def splitWithBalancedBrackets(strToParse: String): List[String] = {
    var result = List[String]()
    var bracketsCount = 0

    trimEdges(strToParse).split(",").filter(_ != "").foreach{ str =>
      if (bracketsCount > 0)
        result = result.init ::: List(s"${result.last},${str}")
      else
        result = result ::: List(str)

      bracketsCount = bracketsCount + str.filter(_ == '[').length - str.filter(_ == ']').length + str.filter(_ == '{').length - str.filter(_ == '}').length
    }

    result
  }

  private def convertToKeyValue(fieldStr: String): (String, JsonValue) = {
    trimEdges(fieldStr.split(":")(0)) -> convertToJsonValue(fieldStr.split(":")(1))
  }

  def convertToJsonObject(objStr: String): JsonObject = {
    JsonObject(splitWithBalancedBrackets(objStr).map(s => convertToKeyValue(s)).toMap)
  }

}
