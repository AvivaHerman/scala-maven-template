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

  private def convertToJsonValue(str: String): JsonValue = {
    if (str.isEmpty) JsonEmpty
    else if (isString(str)) JsonString(str.init.tail)
    else if (isJsonTrue(str)) JsonTrue
    else if (isJsonFalse(str)) JsonFalse
    else if (isJsonNull(str)) JsonNull
    else if (isJsonArray(str)) {
      var values = List[JsonValue]()
      str.init.tail.split(",").filter(_ != "").map(s => (convertToJsonValue(s))).foreach(v => values = v :: values)
      JsonArray(values.reverse)
    }
    else if (isJsonInt(str)) JsonInt(str.toInt)
    else JsonDouble(str.toDouble)
  }

  private def splitWithBalancedBrackets(strings: Array[String]): List[String] = {
    var result = List[String]()
    var squareBracket = 0

    strings.foreach{ str =>
      if (squareBracket > 0)
        result = result.init ::: List(s"${result.last},${str}")
      else
        result = result ::: List(str)

      str.foreach {
        c => if (c == '[') squareBracket = squareBracket + 1
        else if (c == ']') squareBracket = squareBracket - 1
      }
    }

    result
  }

  def parse(objStr: String): JsonObject = {
    var objContent = Map[String, JsonValue]()

    splitWithBalancedBrackets(objStr.init.tail.split(",").filter(_ != "")).foreach {
      field => {
        val pair = field.split(":");
        objContent += (pair(0).init.tail -> convertToJsonValue(pair(1)))
      }
    }

    JsonObject(objContent)
  }
}
