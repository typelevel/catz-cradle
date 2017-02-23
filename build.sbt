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
  .aggregate(
    catz,
    rewrite,
    //catzJS, catzJVM, catzTlsJvm,
    catzXorJS, catzXorJVM, catzXorTlsJvm,
    catzScalazJS, catzScalazJVM, catzScalazTlsJvm,
    ratzJS, ratzJVM, ratzNative, ratzTlsJvm)
  .settings(sharedSettings:_*)
  .settings(scalaVersion := "2.11.8")
  .settings(noPublishSettings)
.enablePlugins(CrossPerProjectPlugin)

// sbt project for the CrossProject catzCP
lazy val catz = project.in(file("catz/.prj"))
  .settings(sharedSettings)
  .settings(noPublishSettings)
  .aggregate(catzJS, catzJVM, catzTlsJs, catzTlsJs1, catzTlsJvm, catzTlsJvm1)
  .enablePlugins(CrossPerProjectPlugin)

// Todo: Add JSPlatform1, JVMPlatform1
// Todo: If the core code also used another library, eg shapeless, we might also need to have two versions of that, too.
//       So then we might need *2* for each platforms...or for even *4* for all combinations. And for three libraries.....
// Todo: publishing JSPlatform and TlsJsPlatform give the same artifact name, so we either have to add a new
//       groupid (bad!) or decide which one we want to publish. Same for jvm and native
lazy val catzCP = crossProject(JSPlatform, JVMPlatform, TlsJsPlatform, TlsJs1Platform, TlsJvmPlatform, TlsJvm1Platform)
  .crossType(CrossType.Pure)
  .in(file("catz"))
  .settings(moduleName := "catz")
  .settings(sharedSettings)
  .settings(
    scalaVersion := "2.12.1",
    crossScalaVersions := Seq("2.10.6", "2.11.8", "2.12.1")
  )
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core" % "0.9.0",
      "org.scalatest" %%% "scalatest" % "3.0.0" % "test")
  )
  .jsSettings(sharedJsSettings)
  .jvmSettings(sharedJvmSettings)
  .tlsJsSettings(sharedJsSettings, sharedTlsSettings)
  .tlsJs1Settings(
    // Same as tlsJs, but compiled againgt cat 0.7.2 - see comments in tlsJvm1Settings fro more details
    sharedJsSettings,
    sharedTlsSettings,scalaVersion := "2.11.8",
    moduleName := "catz1",
    crossScalaVersions := Seq("2.11.8"),
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core" % "0.7.2"))
  .tlsJvmSettings(sharedTlsSettings)
  .tlsJvm1Settings(
    // Same as tlsJvm, but compiled againgt cat 0.7.2
    // As the main code is written for cats 0.9.0, some code may not work out-of-the-box.
    // So we would either have to make this a CrossType.Full and add changes in the
    // non-shared code directory, or (preferably) rewrite the code before compiling.
    // Note (1) that TLS is 2.11/2.12 only, but cats 0.7.2 is 2.10/2.11 only
    // Note (2) that we would also want to do this for JS1Platform and JVM1 platform
    // Note (3) that there is way too much boilerplate!!
    moduleName := "catz1",
    scalaVersion := "2.11.8",
    crossScalaVersions := Seq("2.11.8"),
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core" % "0.7.2")
    //.settings(sourceGenerators in Compile += (sourceManaged in Compile).map(Cats9To7rewrite.gen).taskValue)
  )

lazy val catzJS      = catzCP.js
lazy val catzJVM     = catzCP.jvm
lazy val catzTlsJvm  = catzCP.tlsJvm
lazy val catzTlsJvm1 = catzCP.tlsJvm1
lazy val catzTlsJs   = catzCP.tlsJs
lazy val catzTlsJs1  = catzCP.tlsJs1

//
lazy val catzXor = crossProject(JSPlatform, JVMPlatform, TlsJvmPlatform)
  .crossType(CrossType.Pure)
  .settings(sharedSettings)
  .settings(
    scalaVersion := "2.11.8",
    crossScalaVersions := Seq("2.10.6", "2.11.8")
  )
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core" % "0.7.2",
      "org.scalatest" %%% "scalatest" % "3.0.0" % "test")
  )
  .jsSettings(sharedJsSettings)
  .jvmSettings(sharedJvmSettings)
  .tlsJvmSettings(
    scalaVersion := "2.11.8",
    crossScalaVersions := Seq("2.11.8")
  )

lazy val catzXorJS     = catzXor.js
lazy val catzXorJVM    = catzXor.jvm
lazy val catzXorTlsJvm = catzXor.tlsJvm

lazy val catzScalaz = crossProject(JSPlatform, JVMPlatform, TlsJvmPlatform)
  .crossType(CrossType.Pure)
  .settings(sharedSettings)
  .settings(
    scalaVersion := "2.12.1",
    crossScalaVersions := Seq("2.10.6", "2.11.8", "2.12.1")
  )
  .settings(
    libraryDependencies ++= Seq(
      "org.scalaz"    %%% "scalaz-core" % "7.2.8",
      "org.scalatest" %%% "scalatest"   % "3.0.0" % "test")
  )
  .jsSettings(sharedJsSettings)
  .jvmSettings(sharedJvmSettings)
  .tlsJvmSettings(
    scalaVersion := "2.12.1",
    crossScalaVersions := Seq("2.11.8", "2.12.1")
  )

lazy val catzScalazJS     = catzScalaz.js
lazy val catzScalazJVM    = catzScalaz.jvm
lazy val catzScalazTlsJvm = catzScalaz.tlsJvm

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

lazy val rewrite = project
 .settings(sharedSettings)
    .settings(
      resolvers += Resolver.bintrayIvyRepo("scalameta", "maven"),
      libraryDependencies += "ch.epfl.scala" %% "scalafix-core" % "0.3.0",
      scalaVersion := "2.12.1",
      crossScalaVersions := Seq("2.11.8", "2.12.1")
    ).dependsOn(catzJVM % Scalameta)

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

lazy val sharedTlsSettings = Seq(
  scalaVersion := "2.12.1",
  crossScalaVersions := Seq("2.11.8", "2.12.1")
)
