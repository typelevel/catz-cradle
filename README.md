## catz-cradle

### Dedication

> "It's a very difficult job and the only way to get through it is we all work together as a team. 
> 
> "And that means you do everything I say."
>
> --Charlie Croker, "The Italian Job" (1969)

### Overview

Testbed for scala libraries and tools, based on examples from cats docs, especially for testing
methods to cross build code written for specific compiler and library versions to other 
versions of the compiler and libraries and even to different compilers, libraries and more; projects must be able to work independently, at the their own speed, _and_ still be compatible.

In this project we use the cats examples to represent a project that wants to be at the cutting edge, using the latest versions
of technology. And as cats does, we cross compile to different versions of the scala compiler (2.10.x, 2.11.x. 2.12.x) and also to different platforms; JVM and JS.

But we want more. 

We want to use Typelevel scala (TLS) as well as Lightbend scala (LBS). But what if we want to use features in TLS not available in LBS? Sure,the code emitted from TLS will work with libraries compiled by LBS - but the code itself cannot compile by LBS. And for some, that represents a risk. But if we could "patch" the newer TLS code by doing a downgrade rewrite at compile time, then we get the best of both worlds - binary and source compatibility.

And in the same vein, consider a project built using scalaz that would like to migrate to cats eventually but needs to implement 
new functionality for the project now. On the one hand, they would like to use cats as that is their strategic goal, but on the other hand,
they are still actually using scalaz. So they are really tied in to use scalaz, and this makes migration even harder as new code is added.
So what if the new code could be written as cats with a downgrade rewrite performed at compile time? Again, best of both worlds.

And the new code could also not just use cats, but TLS too. And a newer version of Akka, Spark,....

## Solution

We are asking a lot to be able to realize our goals, fortunately help is at hand:

- Example code: This is taken directly from the [cats documentation](https://github.com/typelevel/cats/tree/master/docs/src/main/tut)
- Cross Building: sbt-cross from [the scala-native project](https://github.com/scala-native/sbt-cross) allows new targets to be defined
- Rewriting: [scalafix](https://github.com/scalacenter/scalafix) adds general methods for rewriting scala code, on top of the core functionality provided by [scalameta](https://github.com/scalameta/scalameta)
- Bigger testing: the [scala community build](https://github.com/scala/community-builds) can [be adapted](https://github.com/scala/community-builds/pull/478) to test the rewriting on many projects

For now, we need to:
- Add TLS as a platform to sbt-cross
- add some SBT support for rewriting source before compilation

## Projects:

The main projects in this repository catz, catzXor and catzScalaz have effectively the same code, but 
based on different base libraries and/or versions.

### catz

This is an example of a library written using the latest cats version (0.9.0), cross built using 
LBS and TLS on 2.10, 2.11 and 2.12 for JVM and JS (where applicable). 
catz1 is built against cats 0.7.2 using TLS, so is 2.11 only


Current Artifacts:
- catzjs_sjs0.6_2.10.jar
- catzjs_sjs0.6_2.11.jar
- catzjs_sjs0.6_2.12.jar
- catzjvm_2.10.jar
- catzjvm_2.11.jar
- catzjvm_2.12.jar
- catztlsjvm_2.11.jar
- catztlsjvm_2.12.jar
- catz1_2.11.jar
- catz1_sjs0.6_2.11.jar

There are 3 SBT projects for this - catzJS, catzJVM and catzTlsJvm.

As a maintainer of this library, I would also like to build a version rewritten for scalaz 7.2.8 and
cats 0.7.2 using the existing source

This would require 6 extra SBT projects for this, currently provided by the catzScalaz and catsXor projects

### catzScalaz

This is an example of a library written using the latest scalaz version (7.2.8), cross built using 
LBS and TLS on 2.10, 2.11 and 2.12 for JVM and JS (where applicable).

Current Artifacts:
- catzscalazjs_sjs0.6_2.10.jar
- catzscalazjs_sjs0.6_2.11.jar
- catzscalazjs_sjs0.6_2.12.jar
- catzscalazjvm_2.10.jar
- catzscalazjvm_2.11.jar
- catzscalazjvm_2.12.jar
- catzscalaztlsjvm_2.11.jar
- catzscalaztlsjvm_2.12.jar

There are 3 SBT projects for this - catzScalazJS, catzScalazJVM and catzScalazTlsJvm.

As a maintainer of this library, I would also like to build a version rewritten for cats 0.7.2 and
cats 0.9.0 using the existing source

### catzXoR
This is an example of a library written using an older cats version (0.7.2), cross built using 
LBS and TLS on 2.10 and 2.11  for JVM and JS (where applicable).

Current Artifacts:

- catzxorjs_sjs0.6_2.10.jar
- catzxorjs_sjs0.6_2.11.jar
- catzxorjvm_2.10.jar
- catzxorjvm_2.11.jar
- catzxortlsjvm_2.11.jar

There are 3 SBT projects for this - catzXorJS, catzXorJVM and catzXorTlsJvm.

As a maintainer of this library, I would also like to build a version rewritten for cats 0.9.0 and
scalaz 7.2.8 using the existing source, and also for 2.12 

## EXPERIMENTAL

We are in the very early stages of development. To compile the code,
[sbt-tls-crossproject](https://github.com/BennyHill/sbt-tls-crossproject) has to be compiled and
installed with `publishLocal`.

The rewrite code that will eventually live in its own project `catz` is currently in 
[a branch of scalafix](https://github.com/ShaneDelmore/scalafix/tree/catz)

## Maintainers

The current maintainers (people who can merge pull requests) are:

 * [BennyHill](https://github.com/BennyHill) Alistair Johnson
 * [ShaneDelmore](https://github.com/ShaneDelmore) Shane Delmore
  
## Participation

The catz-cradle project supports the [Typelevel Code of Conduct](http://typelevel.org/conduct.html) and wants all of its
channels (mailing list, Gitter, IRC, Github, etc.) to be welcoming environments for everyone.
