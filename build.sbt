lazy val root = (project in file("."))
  .settings(name := "votebot")
  .aggregate(
    votebotCommons,
    votebotTestKit,
    voteApi,
    voteImpl,
    collectionApi,
    collectionImpl,
    candidateApi,
    candidateImpl
  )
  .settings(commonSettings: _*)

name := "votebot"

version := "1.0"

scalaVersion in ThisBuild := "2.11.8"

organization in ThisBuild := "com.lejoow"

val playJsonDerivedCodecs = "org.julienrf" %% "play-json-derived-codecs" % "3.3"
val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val cassandraDriverExtras = "com.datastax.cassandra" % "cassandra-driver-extras" % "3.1.2"
val lagomScaladslPlayJson = "com.lightbend.lagom" % "lagom-scaladsl-play-json_2.11" % "1.3.0"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % "test"

lazy val voteApi = (project in file("vote-api"))
  .settings(commonSettings: _*)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      playJsonDerivedCodecs
    )
  ).dependsOn(votebotCommons)

lazy val voteImpl = (project in file("vote-impl"))
  .settings(commonSettings: _*)
  .enablePlugins(LagomScala)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslTestKit,
      lagomScaladslKafkaBroker,
      "com.datastax.cassandra" % "cassandra-driver-extras" % "3.0.0",
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(voteApi, votebotCommons, votebotTestKit % Test)


lazy val collectionApi = (project in file("collection-api"))
  .settings(commonSettings: _*)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      playJsonDerivedCodecs
    )
  )

lazy val collectionImpl = (project in file("collection-impl"))
  .settings(commonSettings: _*)
  .enablePlugins(LagomScala)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslTestKit,
      lagomScaladslKafkaBroker,
      "com.datastax.cassandra" % "cassandra-driver-extras" % "3.0.0",
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(collectionApi, votebotCommons, votebotTestKit % Test)



lazy val candidateApi = (project in file("candidate-api"))
  .settings(commonSettings: _*)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      playJsonDerivedCodecs
    )
  ).dependsOn(votebotCommons)

lazy val candidateImpl = (project in file("candidate-impl"))
  .settings(commonSettings: _*)
  .enablePlugins(LagomScala)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslTestKit,
      lagomScaladslKafkaBroker,
      "com.datastax.cassandra" % "cassandra-driver-extras" % "3.0.0",
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(candidateApi, votebotCommons, votebotTestKit % Test)


lazy val votebotCommons = (project in file("votebot-commons"))
  .settings(commonSettings: _*)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      playJsonDerivedCodecs,
      cassandraDriverExtras,
      lagomScaladslPlayJson,
      macwire
    )
  )


lazy val votebotTestKit = (project in file("votebot-testkit"))
  .settings(commonSettings: _*)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      "com.lightbend.lagom" % "lagom-scaladsl-testkit_2.11" % "1.3.0",
      "org.scalatest" %% "scalatest" % "3.0.1",
      "org.scalamock" %% "scalamock-scalatest-support" % "3.1.1",
      "org.scalamock" %% "scalamock-core" % "3.1.1",
      "org.scalacheck" %% "scalacheck" % "1.13.0",
      "org.mockito" % "mockito-all" % "1.10.19",
      // utils
      // only for ServiceTest, can probably remove later
      "org.apache.cassandra" % "cassandra-all" % "3.0.9" exclude("io.netty", "netty-all")
    )
  )

def commonSettings: Seq[Setting[_]] = Seq(
)

lagomCassandraCleanOnStart in ThisBuild := true

