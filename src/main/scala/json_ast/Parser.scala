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

  def convertToJsonValue(str: String): JsonValue = {
    if (isString(str)) JsonString(str.init.tail)
    else if (isJsonTrue(str)) JsonTrue
    else if (isJsonFalse(str)) JsonFalse
    else if (isJsonNull(str)) JsonNull
    else if (isJsonArray(str)) JsonArray()
    else if (isJsonInt(str)) JsonInt(str.toInt)
    else JsonDouble(str.toDouble)
  }

  def parse(objStr: String): JsonObject = {
    var objContent = Map[String, JsonValue]()

    objStr.init.tail.split(",").filter(s => s != "").foreach {
      field => {
        val pair = field.split(":");
        objContent += (pair(0).init.tail -> convertToJsonValue(pair(1)))
      }
    }

    JsonObject(objContent)
  }
}
