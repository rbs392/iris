name := "Iris"

version := "1.0"

scalaVersion := "2.11.7"

// https://mvnrepository.com/artifact/org.scalaj/scalaj-http_2.11
libraryDependencies += "org.scalaj" % "scalaj-http_2.11" % "2.3.0"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.8.5"
lazy val root = (project in file(".")).enablePlugins(PlayScala)
