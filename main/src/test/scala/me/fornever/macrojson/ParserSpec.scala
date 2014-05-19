package me.fornever.macrojson

import org.scalatest.{Matchers, FlatSpec}

class ParserSpec extends FlatSpec with Matchers {

  "A Parser" should "parse the Message1" in {
    val request =
      """{
        |   "type": "Message1"
        |}""".stripMargin
    val message = Parser.parse(request).asInstanceOf[Message1]
    assert(message != null)
  }

  "A Parser" should "parse the Message2" in {
    val request =
      """{
        |   "type": "Message2",
        |   "field": "FieldValue"
        |}""".stripMargin
    val message = Parser.parse(request).asInstanceOf[Message2]
    assert(message.field == "FieldValue")
  }

}
