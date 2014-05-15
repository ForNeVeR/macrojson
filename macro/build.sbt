scalaVersion := "2.11.0"

resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "io.spray" %% "spray-json" % "1.2.6"
)
