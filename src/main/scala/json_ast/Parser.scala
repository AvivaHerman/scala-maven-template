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
  
  def convertToJsonValue(str: String): JsonValue = {
    if (isString(str)) JsonString(str.init.tail)
    else if (isJsonTrue(str)) JsonTrue
    else if (isJsonFalse(str)) JsonFalse
    else JsonNumber(str.toInt)
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
