ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.18"

lazy val root = (project in file("."))
  .settings(
    name := "spark_scala",
    idePackagePrefix := Some("com.nuwas")
  )


libraryDependencies ++= Seq( "org.apache.spark" % "spark-core_2.12" % "3.4.1")
libraryDependencies ++= Seq( "org.apache.spark" % "spark-sql_2.12" % "3.4.1")
//libraryDependencies ++= Seq( "org.scala-lang" % "scala-library" % "2.12.18")


// https://mvnrepository.com/artifact/org.apache.spark/spark-core
//libraryDependencies += "org.apache.spark" %% "spark-core" % ""

// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
//libraryDependencies += "org.apache.spark" %% "spark-sql_2.12" % "3.4.1" % "provided"