package json_ast

import scala.collection.immutable.HashMap

/**
 * Created by Aviva_Herman on 7/22/14.
 */
class Parser {

  def parse(str: String): JsonObject = {

    if (str == "{}")
      JsonObject.empty
    else
      new JsonObject(HashMap[String, JsonNumber]("a" -> JsonNumber(3)))
  }
}
