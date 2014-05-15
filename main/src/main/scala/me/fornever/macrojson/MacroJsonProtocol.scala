package me.fornever.macrojson

import spray.json._

object MacroJsonProtocol extends DefaultJsonProtocol {

  implicit object MessageJsonFormat extends RootJsonFormat[Message] {

    def write(c: Message) =
      throw new Exception("Still not implemented")

    def read(value: JsValue) = {
      value match {
        case map: JsObject =>
          map.getFields("type")(0) match {
            case JsString(typeName) =>
              Parsers.parseMessage(typeName, map)
          }
        case _ => throw new Exception("Error parsing " + value)
      }
    }

  }

}

