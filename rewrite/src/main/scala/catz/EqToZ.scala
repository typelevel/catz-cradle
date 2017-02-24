package catz

import scala.collection.immutable.Seq
import scalafix.rewrite._
import scala.meta.semantic.v1._
import scala.meta.{ Symbol => _, _ }
import scalafix.util.Patch
import scalafix.util.TreePatch._
import RewriteHelpers._

object EqToZ {
  def rewriteEq[T](
    implicit code: Tree,
    rewriteCtx: RewriteCtx[T]
  ): Seq[Patch] =
    replaceGlobalImport(importer"cats.kernel.instances.int._" -> importer"scalaz.std.anyVal.intInstance") ++
      Seq(
        Replace(Symbol("_root_.cats.kernel.Eq.by."), q"Equal.equalBy", List(importer"scalaz.Equal")),
        Replace(Symbol("_root_.cats.kernel.Eq."), q"Equal", List(importer"scalaz.Equal")),
        RemoveGlobalImport(importer"cats.kernel.Eq")
      )

}
