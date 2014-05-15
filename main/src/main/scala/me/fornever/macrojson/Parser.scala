package me.fornever.macrojson

import spray.json._

object Parser {

  def parse(request: String): Message = {
    import MacroJsonProtocol._
    request.parseJson.convertTo[Message]
  }

}
