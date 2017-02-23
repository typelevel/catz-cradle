addSbtPlugin("com.eed3si9n"        % "sbt-doge"            % "0.1.5")
addSbtPlugin("com.github.gseitz"   % "sbt-release"           % "1.0.3")
addSbtPlugin("com.jsuereth"        % "sbt-pgp"               % "1.0.0")
addSbtPlugin("org.scoverage"       % "sbt-scoverage"         % "1.5.0")
addSbtPlugin("com.typesafe.sbt"    % "sbt-git"               % "0.8.5")
addSbtPlugin("org.scala-js"        % "sbt-scalajs"           % "0.6.14")
addSbtPlugin("org.xerial.sbt"      % "sbt-sonatype"          % "1.1")

resolvers += Resolver.sonatypeRepo("snapshots")
addSbtPlugin("org.scala-native" % "sbt-cross"             % "0.1.0-SNAPSHOT")
addSbtPlugin("org.scala-native" % "sbt-scalajs-cross"     % "0.1.0-SNAPSHOT")
addSbtPlugin("org.scala-native" % "sbt-scala-native"      % "0.1.0-SNAPSHOT")
addSbtPlugin("org.typelevel"    % "sbt-tls-crossproject"  % "0.1.0-SNAPSHOT")

//First publish the sbt-scalahost plugin to get the correct version to use.
//The easiest way to publish the plugin and all of it's dependencies is to
//clone scalameta then run sbt scalahostSbt/test which will publish all
//dependencies, publish the plugin, then test the plugin.
addSbtPlugin("org.scalameta"    % "sbt-scalahost"         % "1.6.0")
