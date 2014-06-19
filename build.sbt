name := "Akka demo "

version := "1.0"

scalaVersion := "2.10.2"

resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases" at "http://oss.sonatype.org/content/repositories/releases",
  "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/")

libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor_2.10" % "2.3.3",
  "com.typesafe.akka" % "akka-slf4j_2.10" % "2.3.3",
  "com.typesafe.akka" % "akka-remote_2.10" % "2.3.3",
  "com.typesafe.akka" % "akka-agent_2.10" % "2.3.3",
  "com.typesafe.akka" % "akka-testkit_2.10" % "2.3.3"
)
