package json_ast

/**
 * Created by Aviva_Herman on 7/22/14.
 */
class Parser {

  def convertToJsonValue(str: String): JsonValue = {
    if (str.head == '\"') JsonString(str.init.tail)
    else if (str == "true") JsonTrue
    else if (str == "false") JsonFalse
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
