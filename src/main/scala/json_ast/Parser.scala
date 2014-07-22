package json_ast

import scala.collection.immutable.HashMap

/**
 * Created by Aviva_Herman on 7/22/14.
 */
class Parser {

  def parse(str: String): JsonObject = {


    if (str == "{}")
      JsonObject.empty
    else {
      var pairsStrList = str.init.tail.split(",")

      var map = HashMap[String, JsonNumber]()

      pairsStrList.foreach(pair => map += (pair.split(":")(0).init.tail -> JsonNumber(pair.split(":")(1).toInt)))

      JsonObject(map)
    }
  }
}
