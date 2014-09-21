name := """OpenAdsAPI"""

version := "2.3.5"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
  "com.typesafe.akka" %% "akka-actor" % "2.3.5",
  "org.scalaz" %% "scalaz-core" % "7.1.0"
)

parallelExecution in Test := true
