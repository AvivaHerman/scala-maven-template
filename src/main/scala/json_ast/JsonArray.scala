package json_ast

/**
 * Created by Aviva_Herman on 7/22/14.
 */
case class JsonArray(jsonValues: JsonValue*) extends JsonValue {
  override def compactString: String = jsonValues.map(v => v.compactString).mkString("[",",","]")
}
