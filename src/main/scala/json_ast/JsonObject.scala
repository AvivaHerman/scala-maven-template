package json_ast

import scala.collection.immutable.HashMap

/**
 * Created by Aviva_Herman on 7/22/14.
 */
case class JsonObject(fields: Map[String, JsonValue]) extends JsonValue {

  private def addQuotesToKey(key: String) = "\"" + key + "\""

  override def compactString: String = {
    fields.map{ x => s"${addQuotesToKey(x._1)}:${x._2.compactString}" }.mkString("{",",","}")
  }
}

object JsonObject {
  def empty = new JsonObject(Map())
}
