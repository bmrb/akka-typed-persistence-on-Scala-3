import Dependencies._

lazy val root = project
  .in(file("."))
  .settings(
    name := "SerializerTest",
    version := "0.1.0",
    //scalaVersion := "2.13.6",
    scalaVersion := "3.0.2",
    crossScalaVersions ++= Seq("2.13.6", "3.0.2"),
    libraryDependencies ++= akka ++ misc,
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-unchecked",
      "-language:implicitConversions"
    )
  )
