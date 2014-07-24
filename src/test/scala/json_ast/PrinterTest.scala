package json_ast

import org.specs2.specification.Scope
import org.specs2.mutable.SpecificationWithJUnit

/**
 * Created by Aviva_Herman on 7/23/14.
 */
class PrinterTest extends SpecificationWithJUnit {

  trait Context extends Scope {
    val printer = new Printer()
  }

  "Printer" should {
    "print empty object" in new Context {
      printer.compactPrint(JsonObject(Map())) must_== "{}"
    }

    "print JsonObjects with integers" in new Context {
      printer.compactPrint(JsonObject(Map("a" -> JsonInt(3), "b" -> JsonInt(2)))) must_== """{"a":3,"b":2}"""
    }

    "print JsonObjects with doubles" in new Context {
      printer.compactPrint(JsonObject(Map("a" -> JsonDouble(3.0), "b" -> JsonDouble(2.0)))) must_== """{"a":3.0,"b":2.0}"""
    }
  }

}
