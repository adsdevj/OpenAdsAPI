name := """OpenAdsAPI"""

version := "2.3.5"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging"           %% "scala-logging"                     % "3.1.0",
  "com.github.nscala-time"               %% "nscala-time"                       % "1.4.0",
  "org.scalatest"                        % "scalatest_2.11"                     % "2.2.1"     % "test"
)

parallelExecution in Test := true
