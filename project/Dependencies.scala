import sbt._
import sbt.librarymanagement.CrossVersion

object Dependencies {

  object Version {
    val Akka = "2.6.16"
    val LogBack = "1.2.5"
    val Janino = "3.1.6"
    val LevelDBJni = "1.8"
    val Jackson = "2.13.0-rc1"
  }

  lazy val akka = Seq(
    ("com.typesafe.akka" %% "akka-actor-typed"),
    ("com.typesafe.akka" %% "akka-persistence-typed"),
    ("com.typesafe.akka" %% "akka-stream-typed"),
    ("com.typesafe.akka" %% "akka-serialization-jackson"),
  ).map(_ % Version.Akka).map(_.cross(CrossVersion.for3Use2_13))

  lazy val misc = Seq(
    "ch.qos.logback" % "logback-classic" % Version.LogBack,
    "org.codehaus.janino" % "janino" % Version.Janino,
    "org.fusesource.leveldbjni" % "leveldbjni-all" % Version.LevelDBJni,

    "com.fasterxml.jackson.core" % "jackson-databind" % Version.Jackson,
    ("com.fasterxml.jackson.module" %% "jackson-module-scala" % Version.Jackson).cross(CrossVersion.for3Use2_13),

  )

}
