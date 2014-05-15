name := "macrojson"

scalacOptions += "-Ymacro-debug-lite"

scalaVersion := "2.11.0"

resolvers += "spray" at "http://repo.spray.io/"

libraryDependencies ++= Seq(
  "io.spray" %%  "spray-json" % "1.2.6",
  "org.scalatest" %% "scalatest" % "2.1.3" % "test"
)
