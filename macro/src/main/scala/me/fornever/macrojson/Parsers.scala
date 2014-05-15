package me.fornever.macrojson

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import spray.json._

object Parsers {

  def impl(c: Context)(typeName: c.Expr[String], map: c.Expr[JsObject]): c.Expr[Message] = {
    import c.universe._

    val types = Seq(typeOf[Message1], typeOf[Message2])
    val clauses = types.map(t => {
      val name = t.typeSymbol.name.toString
      cq"$name => new $t()"
    })

    val tree = q"typeName match { case ..$clauses }"
    c.Expr[Message](tree)
  }

  def parseMessage(typeName: String, map: JsObject): Message = macro Parsers.impl

}
