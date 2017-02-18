import sbt._
import Keys._
import sbtcross.{crossProject, CrossType}


addCommandAlias("validateJVM", ";catsJVM/test")
addCommandAlias("validateJS",  ";catsJS/test")
addCommandAlias("validate",    ";validateJS;validateJVM")
addCommandAlias("validateAll", s""";+clean;+validate""") 
addCommandAlias("gitSnapshots", ";set version in ThisBuild := git.gitDescribedVersion.value.get + \"-SNAPSHOT\"")

organization in ThisBuild := "org.typelevel"

lazy val commonSettings = Seq(
  scalacOptions ++= scalacAllOptions,
  incOptions := incOptions.value.withLogRecompileOnMacro(false),
  parallelExecution in Test := false,
 updateOptions := updateOptions.value.withCachedResolution(true)
) ++ warnUnusedImport ++ update2_12

lazy val sharedSettings = commonSettings //++ publishSettings ++ scoverageSettings


lazy val root = project.in(file("."))
  .aggregate(catzJS, catzJVM, catzTlsJvm, ratzJS, ratzJVM, ratzNative, ratzTlsJvm)
  .settings(sharedSettings:_*)
  .settings(scalaVersion := "2.11.8")
  .settings(noPublishSettings)
.enablePlugins(CrossPerProjectPlugin)

lazy val catz = crossProject(JSPlatform, JVMPlatform, TlsJvmPlatform)
    .crossType(CrossType.Pure)
    .settings(sharedSettings)
    .settings(
      scalaVersion := "2.12.1",
      crossScalaVersions := Seq("2.10.6", "2.11.8", "2.12.1")
    )
    .settings(libraryDependencies += "org.typelevel" %%% "cats-core" % "0.9.0" % "provided")
    .jsSettings(sharedJsSettings)
    .jvmSettings(sharedJvmSettings)
     .tlsJvmSettings(
       scalaVersion := "2.12.1",
       crossScalaVersions := Seq("2.11.8", "2.12.1")
    )

lazy val catzJS     = catz.js
lazy val catzJVM    = catz.jvm
lazy val catzTlsJvm    = catz.tlsJvm

lazy val ratz =
  crossProject(JSPlatform, JVMPlatform, NativePlatform, TlsJvmPlatform)
    .crossType(CrossType.Pure)
    .settings(sharedSettings)
    .jsSettings(
      sharedJsSettings,
      scalaVersion := "2.12.1",
      crossScalaVersions := Seq("2.10.6", "2.11.8", "2.12.1")
    )
    .jvmSettings(
      sharedJvmSettings,
      scalaVersion := "2.12.1",
      crossScalaVersions := Seq("2.10.6", "2.11.8", "2.12.1")
    )
    .nativeSettings(sharedNativeSettings)
    .tlsJvmSettings(
      scalaVersion := "2.12.1",
      crossScalaVersions := Seq("2.11.8", "2.12.1")
    )

lazy val ratzJS     = ratz.js
lazy val ratzJVM    = ratz.jvm
lazy val ratzNative = ratz.native
lazy val ratzTlsJvm    = ratz.tlsJvm

// SBT Boilerplate

lazy val noPublishSettings = Seq(
  publish := (),
  publishLocal := (),
  publishArtifact := false
)

/** Common scalac options useful to most (if not all) projects.*/
lazy val scalacCommonOptions = Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-unchecked",
  "-Xlint"
)

/** Scalac options for additional language options.*/
lazy val scalacLanguageOptions = Seq(
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:experimental.macros"
)

/** Scalac strict compilation options.*/
lazy val scalacStrictOptions = Seq(
  "-Xfatal-warnings",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture"
)

/** Combines all scalac options.*/
lazy val scalacAllOptions = scalacCommonOptions ++ scalacLanguageOptions ++ scalacStrictOptions

lazy val update2_12 = Seq(
    scalacOptions -= {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, n)) if n >= 12 =>
          "-Yinline-warnings"
        case _ =>
          ""
      }
    }
  )

lazy val fix2_12 = Seq(
    scalacOptions -= {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, n)) if n >= 12 =>
          "-Xfatal-warnings"
        case _ =>
          ""
      }
    }
  )

/** Add the "unused import" warning to scala 2.11+, but not for the console.*/
lazy val warnUnusedImport = Seq(
  scalacOptions ++= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, 10)) =>
        Seq()
      case Some((2, n)) if n >= 11 =>
        Seq("-Ywarn-unused-import")
    }
  },
  //use this when activator moved to 13.9
 // scalacOptions in (Compile, console) -= "-Ywarn-unused-import",
  scalacOptions in (Compile, console) ~= {_.filterNot("-Ywarn-unused-import" == _)},
  scalacOptions in (Test, console) := (scalacOptions in (Compile, console)).value
)

/**
 * Scala JS settings shared by many projects.
 *
 * Forces the use of node.js in tests and batchmode under travis
 */
lazy val sharedJsSettings = Seq(
  scalaJSStage in Global := FastOptStage,
  parallelExecution := false,
  requiresDOM := false,
  jsEnv := NodeJSEnv().value
)

/**
 * Scala JVM settings shared by many projects.
 *
 * Currently empty.
 */
lazy val sharedJvmSettings = Seq()

lazy val sharedNativeSettings = Seq(
  resolvers += Resolver.sonatypeRepo("snapshots"),
  scalaVersion := "2.11.8",
  crossScalaVersions := Seq("2.11.8")
)

