package me.fornever.macrojson

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import spray.json._

object Parsers {

  def impl[T: c.WeakTypeTag](c: Context)(typeName: c.Expr[String], map: c.Expr[JsObject]): c.Expr[T] = {
    import c.universe._

    val cls = weakTypeOf[T].typeSymbol.asClass
    val types = cls.knownDirectSubclasses.map(_.info)

    val clauses = types.map(tpe => {
      val constructor = tpe.members.filter(_.isMethod).map(_.asInstanceOf[MethodSymbol]).filter(_.isConstructor).head
      val constructorParameters = constructor.paramLists.head
      val parameterNames = constructorParameters.map(_.name)
      val parameterNameStrings = parameterNames.map(_.toString)
      val parameterBindings = parameterNames.map(name => pq"$name @ _")

      val args = constructorParameters.map { param =>
        val parameterName = TermName(param.name.toString)
        val parameterType = param.typeSignature

        q"$parameterName.convertTo[$parameterType]"
      }

      val typeName = tpe.typeSymbol
      val typeNameString = typeName.name.toString
      cq"""$typeNameString =>
             $map.getFields(..$parameterNameStrings) match {
               case Seq(..$parameterBindings) => new $typeName(..$args)
             }"""
    })

    val tree = q"typeName match { case ..$clauses }"
    c.Expr[T](tree)
  }

  def parseMessage[T](typeName: String, map: JsObject): T = macro Parsers.impl[T]

}
