package json_ast

/**
 * Created by Aviva_Herman on 7/22/14.
 */
case class JsonInt(x: Int) extends JsonNumber {
  override def compactString: String = x.toString
}
