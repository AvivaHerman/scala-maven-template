package json_ast

import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope

import scala.collection.immutable.HashMap
import scala.collection.mutable


/**
 * Created by Aviva_Herman on 7/22/14.
 */
class JsonObjectTest extends SpecificationWithJUnit {

  trait Context extends Scope {
    var parser = new Parser()
  }

  "JsonObject" should {
    "be empty" in new Context {
      var jsonObj = parser.parse("{}")

      jsonObj must_== (JsonObject.empty)
    }

    "with single number" in new Context {
      var jsonObj = parser.parse("{\"a\":3}")

      var map = HashMap[String, JsonNumber]("a" -> JsonNumber(3))

      jsonObj must_== JsonObject(map)
    }

    "contain many numbers" in new Context {
      var jsonObj = parser.parse("{\"a\":3,\"b\":2}")

      var map = HashMap[String, JsonNumber]("a" -> JsonNumber(3), "b" -> JsonNumber(2))

      jsonObj must_== JsonObject(map)
    }

  }
}
