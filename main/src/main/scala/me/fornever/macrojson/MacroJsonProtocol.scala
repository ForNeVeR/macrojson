package me.fornever.macrojson

import spray.json._

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

