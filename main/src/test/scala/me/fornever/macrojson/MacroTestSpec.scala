package me.fornever.macrojson

import org.scalatest.{Matchers, FlatSpec}

class MacroTestSpec extends FlatSpec with Matchers {

  "test macro" should "return 4" in {
    assert(MacroTest.test() === 4)
  }

}
