package me.fornever.macrojson

import spray.json._

abstract sealed class Message()

case class SimpleMessage() extends Message
case class FieldMessage(field: String) extends Message
case class NestedMessage(nested: Message) extends Message
case class MultiMessage(field: Int, nested: Message) extends Message

object MacroJsonProtocol extends DefaultJsonProtocol {

  implicit object MessageJsonFormat extends RootJsonFormat[Message] {

    def write(c: Message) =
      throw new Exception("Still not implemented")

    def read(value: JsValue) = {
      value match {
        case map: JsObject =>
          map.getFields("type") match {
            case Seq(JsString(typeName)) => Parsers.parseMessage[Message](typeName, map)
            case _ => deserializationError("Invalid 'type' parameter")
          }
        case _ => deserializationError("Error parsing" + value)
      }
    }

  }

}
