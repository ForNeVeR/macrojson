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

    val clauses = types.map(tpe => {
      val constructor = tpe.members.filter(_.isMethod).map(_.asInstanceOf[MethodSymbol]).filter(_.isConstructor).head
      val constructorParameters = constructor.paramLists.head
      val parameterNameStrings = constructorParameters.map(_.name.toString)
      val parameterNames = parameterNameStrings.map(newTermName(_))

      val args = constructorParameters.zipWithIndex.map {
        case (param, index) =>
          val parameter = parameterNames(index)
          val parameterType = param.typeSignature

          q"$parameter.convertTo[$parameterType]"
      }

      val parameters = parameterNames.map { name =>
        pq"$name @ _"
      }

      val name = tpe.typeSymbol.name.toString
      cq"""$name =>
             $map.getFields(..$parameterNameStrings) match {
               case Seq(..$parameters) => new $tpe(..$args)
             }"""
    })

    val tree = q"typeName match { case ..$clauses }"
    c.Expr[T](tree)
  }

  def parseMessage[T](typeName: String, map: JsObject): T = macro Parsers.impl[T]

}
