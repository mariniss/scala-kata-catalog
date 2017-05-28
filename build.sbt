name := "kataCatalog"

version := "1.0"

scalaVersion := "2.11.8"

parallelExecution in Test := false

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % "test,it",
  "org.mockito" % "mockito-core" % "1.10.8" % "test,it"
)

lazy val itConfig = config("it") extend Test

lazy val kataCatalog = project
  .in(file("."))
  .configs(config("it") extend Test)
  .settings(inConfig(itConfig)(Defaults.testSettings): _*)
