package json_ast

import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope

/**
 * Created by Aviva_Herman on 7/22/14.
 */
class ParserTest extends SpecificationWithJUnit {

  trait Context extends Scope {
    var parser = new Parser()
  }

  "Parser" should {
    "parse an empty JsonObject" in new Context {
      parser.convertToJsonObject("{}") must_== (JsonObject.empty)
    }

    "parse JsonObjects with integers" in new Context {
      parser.convertToJsonObject("""{"a":3,"b":2}""") must_== JsonObject(Map("a" -> JsonInt(3), "b" -> JsonInt(2)))
    }

    "parse JsonObject with a string" in new Context {
      parser.convertToJsonObject("""{"name":"Aviva"}""") must_== JsonObject(Map("name" -> JsonString("Aviva")))
    }

    "parse JsonObject with strings" in new Context {
      parser.convertToJsonObject("""{"name":"Aviva","birthday":"March 3rd 1990"}""") must_==
        JsonObject(Map("name" -> JsonString("Aviva"), "birthday" -> JsonString("March 3rd 1990")))
    }

    "parse JsonObject with boolean true" in new Context {
      parser.convertToJsonObject("""{"a":true}""") must_== JsonObject(Map("a" -> JsonTrue))
    }

    "parse JsonObject with boolean false" in new Context {
      parser.convertToJsonObject("""{"a":false}""") must_== JsonObject(Map("a" -> JsonFalse))
    }

    "parse JsonObject with null" in new Context {
      parser.convertToJsonObject("""{"a":null}""") must_== JsonObject(Map("a" -> JsonNull))
    }

    "parse JsonObject with double" in new Context {
      parser.convertToJsonObject("""{"a":3.5}""") must_== JsonObject(Map("a" -> JsonDouble(3.5)))
    }

    "parse JsonObject with an empty array" in new Context {
      parser.convertToJsonObject("""{"a":[]}""") must_== JsonObject(Map("a" -> JsonArray()))
    }

    "parse JsonObject with one element" in new Context {
      parser.convertToJsonObject("""{"a":[2]}""") must_== JsonObject(Map("a" -> JsonArray(JsonInt(2))))
    }

    "parse JsonObject with array with many values" in new Context {
      parser.convertToJsonObject("""{"a":[2,4]}""") must_== JsonObject(Map("a" -> JsonArray(JsonInt(2),JsonInt(4))))
    }

    "parse JsonObject with nested array" in new Context {
      parser.convertToJsonObject("""{"a":[2,[3,3,3],4,[3,[3,3,3],3]]}""") must_==
        JsonObject(Map("a" -> JsonArray(JsonInt(2),JsonArray(JsonInt(3),JsonInt(3),JsonInt(3)),JsonInt(4),JsonArray(JsonInt(3),JsonArray(JsonInt(3),JsonInt(3),JsonInt(3)),JsonInt(3)))))
    }

    "parse JsonObject with nested object" in new Context {
      parser.convertToJsonObject("""{"a":2,"b":{}}""") must_== JsonObject(Map("a" -> JsonInt(2),"b" -> JsonObject(Map())))
    }

    "parse JsonObject with nested object" in new Context {
      parser.convertToJsonObject("""{"a":2,"b":{"c":4,"d":[true,3.5,false]}}""") must_==
        JsonObject(Map("a" -> JsonInt(2),"b" -> JsonObject(Map("c" -> JsonInt(4),"d" -> JsonArray(JsonTrue,JsonDouble(3.5),JsonFalse)))))
    }

    "parse JsonObject with mixed types" in new Context {
      parser.convertToJsonObject("""{"a":3.5,"b":2,"c":"","d":"Aviva","e":[1,false],"f":null,"g":false,"h":true}""") must_==
        JsonObject(Map(
          "a" -> JsonDouble(3.5),
          "b" -> JsonInt(2),
          "c" -> JsonString(""),
          "d" -> JsonString("Aviva"),
          "e" -> JsonArray(JsonInt(1),JsonFalse),
          "f" -> JsonNull,
          "g" -> JsonFalse,
          "h" -> JsonTrue)
        )
    }
  }
}
