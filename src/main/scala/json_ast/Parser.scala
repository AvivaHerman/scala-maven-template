package json_ast

//import scala.collection.immutable.HashMap

/**
 * Created by Aviva_Herman on 7/22/14.
 */
class Parser {

  def convertToJsonValue(str: String): JsonValue = {
    if (str.head == '\"') JsonString(str.init.tail)
    else if (str == "true") JsonTrue
    else JsonNumber(str.toInt)
  }

  def parse(str: String): JsonObject = {
    if (str == "{}")
      JsonObject.empty
    else {
      var pairsStrList = str.init.tail.split(",")

      var map = Map[String, JsonValue]()

      pairsStrList.foreach(pair => map += (pair.split(":")(0).init.tail -> convertToJsonValue(pair.split(":")(1))))

      JsonObject(map)
    }
  }
}
