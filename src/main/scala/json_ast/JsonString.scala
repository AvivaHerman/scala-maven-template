package json_ast

/**
 * Created by Aviva_Herman on 7/22/14.
 */
case class JsonString(str: String) extends JsonValue {
  override def compactString: String = "\"" + str + "\""
}
