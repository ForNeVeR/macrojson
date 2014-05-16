package me.fornever.macrojson

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import spray.json._

object Parsers {

  def impl[T: c.WeakTypeTag](c: Context)(typeName: c.Expr[String], map: c.Expr[JsObject]): c.Expr[T] = {
    import c.universe._

    val symbol = weakTypeOf[T].typeSymbol.asInstanceOf[TypeSymbol]
    val internal = symbol.asInstanceOf[scala.reflect.internal.Symbols#Symbol]
    val types = (internal.sealedDescendants.map(_.asInstanceOf[TypeSymbol]) - symbol).map(_.toType)
    val clauses = types.map(t => {
      val name = t.typeSymbol.name.toString
      cq"$name => new $t()"
    })

    val tree = q"typeName match { case ..$clauses }"
    c.Expr[T](tree)
  }

  def parseMessage[T](typeName: String, map: JsObject): T = macro Parsers.impl[T]

}
