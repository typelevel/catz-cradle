package catz

import scala.meta._
import scalafix.rewrite._
import scala.collection.immutable.Seq
import scalafix.util.Patch
import RewriteHelpers._

object SemigroupToZ {
  def rewriteSemigroup[T](
    implicit code: Tree,
    rewriteCtx: RewriteCtx[T]
  ): Seq[Patch] =
    replaceGlobalImport(importer"cats.Semigroup" -> importer"scalaz.Semigroup") ++
      replaceTypeclassMember("Semigroup", "combine" -> "append")

}
