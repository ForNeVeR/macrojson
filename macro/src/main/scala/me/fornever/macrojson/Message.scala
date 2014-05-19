package me.fornever.macrojson

abstract sealed class Message()

case class SimpleMessage() extends Message
case class FieldMessage(field: String) extends Message
case class NestedMessage(nested: Message) extends Message
