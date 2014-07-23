package json_ast

import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope

/**
 * Created by Aviva_Herman on 7/22/14.
 */
class JsonObjectTest extends SpecificationWithJUnit {

  trait Context extends Scope {
    var parser = new Parser()
  }

  "JsonObject" should {
    "be empty" in new Context {
      parser.convertToJsonObject("{}") must_== (JsonObject.empty)
    }

    "with single number" in new Context {
      parser.convertToJsonObject("{\"a\":3}") must_== JsonObject(Map("a" -> JsonInt(3)))
    }

    "contain many numbers" in new Context {
      parser.convertToJsonObject("{\"a\":3,\"b\":2}") must_== JsonObject(Map("a" -> JsonInt(3), "b" -> JsonInt(2)))
    }

    "contain string" in new Context {
      parser.convertToJsonObject("{\"name\":\"Aviva\"}") must_== JsonObject(Map("name" -> JsonString("Aviva")))
    }

    "contain empty string" in new Context {
      parser.convertToJsonObject("{\"name\":\"\"}") must_== JsonObject(Map("name" -> JsonString("")))
    }

    "contain many strings" in new Context {
      parser.convertToJsonObject("{\"name\":\"Aviva\",\"birthday\":\"March 3rd 1990\"}") must_==
        JsonObject(Map("name" -> JsonString("Aviva"), "birthday" -> JsonString("March 3rd 1990")))
    }

    "contain strings and numbers" in new Context {
      parser.convertToJsonObject("{\"a\":2,\"name\":\"Aviva\",\"b\":5,\"birthday\":\"March 3rd 1990\"}") must_==
        JsonObject(Map(
          "a" -> JsonInt(2),
          "name" -> JsonString("Aviva"),
          "b" -> JsonInt(5),
          "birthday" -> JsonString("March 3rd 1990"))
        )
    }

    "contain true" in new Context {
      parser.convertToJsonObject("{\"a\":true}") must_== JsonObject(Map("a" -> JsonTrue))
    }

    "contain false" in new Context {
      parser.convertToJsonObject("{\"a\":false}") must_== JsonObject(Map("a" -> JsonFalse))
    }

    "contain null" in new Context {
      parser.convertToJsonObject("{\"a\":null}") must_== JsonObject(Map("a" -> JsonNull))
    }

    "contain double" in new Context {
      parser.convertToJsonObject("{\"a\":3.5}") must_== JsonObject(Map("a" -> JsonDouble(3.5)))
    }

    "contain empty array" in new Context {
      parser.convertToJsonObject("{\"a\":[]}") must_== JsonObject(Map("a" -> JsonArray(List())))
    }

    "contain array with one element" in new Context {
      parser.convertToJsonObject("{\"a\":[2]}") must_== JsonObject(Map("a" -> JsonArray(List(JsonInt(2)))))
    }

    "contain array with many values" in new Context {
      parser.convertToJsonObject("{\"a\":[2,4]}") must_== JsonObject(Map("a" -> JsonArray(List(JsonInt(2),JsonInt(4)))))
    }

    "contain nested array" in new Context {
      parser.convertToJsonObject("{\"a\":[2,[3,3,3],4]}") must_==
        JsonObject(Map("a" -> JsonArray(List(JsonInt(2),JsonArray(List(JsonInt(3),JsonInt(3),JsonInt(3))),JsonInt(4)))))
    }

    "contain mixed types" in new Context {
      parser.convertToJsonObject("{\"a\":3.5,\"b\":2,\"c\":\"\",\"d\":\"Aviva\",\"d1\":[1,false],\"e\":null,\"f\":false,\"g\":true}") must_==
        JsonObject(Map(
          "a" -> JsonDouble(3.5),
          "b" -> JsonInt(2),
          "c" -> JsonString(""),
          "d" -> JsonString("Aviva"),
          "d1" -> JsonArray(List(JsonInt(1),JsonFalse)),
          "e" -> JsonNull,
          "f" -> JsonFalse,
          "g" -> JsonTrue)
        )
    }
  }
}
