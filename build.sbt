
scalaVersion := "2.13.8"
name := "treasure map"
organization := "fr.AP"
version := "1.0"


libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"


libraryDependencies += "org.scalameta" %% "munit" % "0.7.22" % Test

testFrameworks += new TestFramework("munit.Framework")

