package catz

import scala.meta._
import scalafix.rewrite._
import scala.collection.immutable.Seq
import scalafix.util.Patch
import RewriteHelpers._

object FunctorToZ {
  def rewriteFunctor[T](
    implicit code: Tree,
    rewriteCtx: RewriteCtx[T]
  ): Seq[Patch] =
    replaceGlobalImports(
      importer"cats.instances.option._" -> importer"scalaz.std.option._",
      importer"cats.instances.list._" -> importer"scalaz.std.list._",
      importer"cats.Functor" -> importer"scalaz.Functor"
    )
}
