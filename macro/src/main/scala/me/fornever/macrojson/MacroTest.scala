package me.fornever.macrojson

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

object MacroTest {

  def impl(c: Context)(): c.Expr[Int] = {
    import c.universe._

    val result = 2 + 2
    c.Expr(Literal(Constant(result)))
  }

  def test(): Int = macro MacroTest.impl

}
