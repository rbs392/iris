name := "Iris"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "org.scalaj" % "scalaj-http_2.11" % "2.3.0"

libraryDependencies += "org.mockito" % "mockito-all" % "1.9.5"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"


lazy val root = (project in file(".")).enablePlugins(PlayScala)
