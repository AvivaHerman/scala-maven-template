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

    "contain string" in new Context {
      var jsonObj = parser.parse("{\"name\":\"Aviva\"}")

      var map = HashMap[String, JsonString]("name" -> JsonString("Aviva"))

      jsonObj must_== JsonObject(map)
    }

    "contain many strings" in new Context {
      var jsonObj = parser.parse("{\"name\":\"Aviva\",\"birthday\":\"March 3rd 1990\"}")

      var map = HashMap[String, JsonString]("name" -> JsonString("Aviva"), "birthday" -> JsonString("March 3rd 1990"))

      jsonObj must_== JsonObject(map)
    }

    "contain strings and numbers" in new Context {
      var jsonObj = parser.parse("{\"a\":2,\"name\":\"Aviva\",\"b\":5,\"birthday\":\"March 3rd 1990\"}")

      var map = HashMap[String, JsonValue]("a" -> JsonNumber(2), "name" -> JsonString("Aviva"), "b" -> JsonNumber(5),"birthday" -> JsonString("March 3rd 1990"))

      jsonObj must_== JsonObject(map)
    }
  }
}
