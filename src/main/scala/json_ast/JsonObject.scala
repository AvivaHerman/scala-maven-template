package json_ast

import scala.collection.immutable.HashMap

/**
 * Created by Aviva_Herman on 7/22/14.
 */
case class JsonObject(pairs: Map[String, JsonValue]) extends JsonValue {

}

object JsonObject {
  def empty = new JsonObject(Map())
}
