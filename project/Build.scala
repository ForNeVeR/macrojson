import sbt._

object MacroJsonBuild extends Build {

  lazy val root = Project(id = "Parent",
    base = file(".")) aggregate(macroProject, mainProject)

  lazy val macroProject = Project(
	id = "macro",
    base = file("macro"))

  lazy val mainProject = Project(
	id = "main",
    base = file("main")
  ) dependsOn macroProject

}
