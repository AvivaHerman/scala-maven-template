package json_ast

import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope
//import scala.collection.immutable.HashMap


/**
 * Created by Aviva_Herman on 7/22/14.
 */
class JsonObjectTest extends SpecificationWithJUnit {

  trait Context extends Scope {
    var parser = new Parser()
  }

  "JsonObject" should {
    "be empty" in new Context {
      parser.parse("{}") must_== (JsonObject.empty)
    }

    "with single number" in new Context {
      parser.parse("{\"a\":3}") must_== JsonObject(Map("a" -> JsonNumber(3)))
    }

    "contain many numbers" in new Context {
      parser.parse("{\"a\":3,\"b\":2}") must_== JsonObject(Map("a" -> JsonNumber(3), "b" -> JsonNumber(2)))
    }

    "contain string" in new Context {
      parser.parse("{\"name\":\"Aviva\"}") must_== JsonObject(Map("name" -> JsonString("Aviva")))
    }

    "contain many strings" in new Context {
      parser.parse("{\"name\":\"Aviva\",\"birthday\":\"March 3rd 1990\"}") must_==
        JsonObject(Map("name" -> JsonString("Aviva"), "birthday" -> JsonString("March 3rd 1990")))
    }

    "contain strings and numbers" in new Context {
      parser.parse("{\"a\":2,\"name\":\"Aviva\",\"b\":5,\"birthday\":\"March 3rd 1990\"}") must_==
        JsonObject(Map("a" -> JsonNumber(2),
          "name" -> JsonString("Aviva"),
          "b" -> JsonNumber(5),
          "birthday" -> JsonString("March 3rd 1990")))
    }

    "contain true" in new Context {
      parser.parse("{\"a\":true}") must_== JsonObject(Map("a" -> JsonTrue))
    }

    "contain false" in new Context {
      parser.parse("{\"a\":false}") must_== JsonObject(Map("a" -> JsonFalse))
    }

    "contain null" in new Context {
      parser.parse("{\"a\":null}") must_== JsonObject(Map("a" -> JsonNull))
    }

  }
}
