macrojson
=========

This is a scala macro playground. The main macro is `Parsers.parseMessage[T]`.

Consider the following hierarchy of `Message` subclasses:

```scala
abstract sealed class Message()

case class SimpleMessage() extends Message
case class FieldMessage(field: String) extends Message
case class NestedMessage(nested: Message) extends Message
case class MultiMessage(field: Int, nested: Message) extends Message
```

Here is the macro usage example:

```scala
Parsers.parseMessage[Message](typeName, map)
```

This usage constructs the following code at the compile time:

```scala
typeName match {
  case "FieldMessage" => map.getFields("field") match {
    case Seq(field) => new FieldMessage(field.convertTo[String])
  }
  case "NestedMessage" => map.getFields("nested") match {
    case Seq(nested) => new NestedMessage(nested.convertTo[Message])
  }
  case "MultiMessage" => map.getFields("field", "nested") match {
    case Seq(field, nested) => new MultiMessage(field.convertTo[Int], nested.convertTo[Message])
  }
  case "SimpleMessage" => map.getFields() match {
    case Seq() => new SimpleMessage()
  }
}
```
