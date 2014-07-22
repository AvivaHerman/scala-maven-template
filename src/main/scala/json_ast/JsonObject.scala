package json_ast

import scala.collection.immutable.HashMap

/**
 * Created by Aviva_Herman on 7/22/14.
 */
case class JsonObject(pairs: HashMap[String, JsonValue]) {

}

object JsonObject {
  def empty = new JsonObject(HashMap())
}
