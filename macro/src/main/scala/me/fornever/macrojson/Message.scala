package me.fornever.macrojson

abstract sealed class Message()

case class Message1() extends Message
case class Message2(field: String) extends Message
