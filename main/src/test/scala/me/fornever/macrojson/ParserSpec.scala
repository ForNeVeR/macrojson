package me.fornever.macrojson

import org.scalatest.{Matchers, FlatSpec}

class ParserSpec extends FlatSpec with Matchers {

  "A Parser" should "parse the SimpleMessage" in {
    val request =
      """{
        | "type": "SimpleMessage"
        |}""".stripMargin
    val message = Parser.parse(request)
    assert(message === SimpleMessage())
  }

  it should "parse the FieldMessage" in {
    val request =
      """{
        | "type": "FieldMessage",
        | "field": "FieldValue"
        |}""".stripMargin
    val message = Parser.parse(request)
    assert(message === FieldMessage("FieldValue"))
  }

  it should "parse the NestedMessage" in {
    val request =
      """{
        | "type": "NestedMessage",
        | "nested": {
        |   "type": "SimpleMessage"
        | }
        |}""".stripMargin
    val message = Parser.parse(request)
    assert(message === NestedMessage(SimpleMessage()))
  }

  it should "parse the MultiMessage" in {
    val request =
    """{
      | "type": "MultiMessage",
      | "field": 100,
      | "nested": {
      |   "type": "SimpleMessage"
      | }
      |}
    """.stripMargin
    val message = Parser.parse(request)
    assert(message === MultiMessage(100, SimpleMessage()))
  }

}
