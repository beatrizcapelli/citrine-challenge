name := "citrine-challenge"
organization := "com.citrine"

version := "1.0-SNAPSHOT"

// TODO build docker image passing the env as parameter
val env = "dev"

scalaVersion := "2.13.3"

libraryDependencies += guice

libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value

libraryDependencies += "org.scalatest" % "scalatest_2.13" % "3.2.2" % Test

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.13" % "2.6.5"

libraryDependencies += "ch.timo-schmid" % "slf4s-api_2.13" % "1.7.30"

lazy val commonSettings = Seq(
  name := "citrine-challenge",
  dockerExposedPorts := Seq(9000),
  dockerBaseImage := "openjdk:8"
)

lazy val app = project
  .in(file("."))
  .enablePlugins(PlayScala)
  .enablePlugins(DockerPlugin)
  .settings(
    commonSettings,
    mappings in Universal += {
      ((resourceDirectory in Compile).value / s"application.${env}.ini") -> "conf/application.ini"
    },
  )
