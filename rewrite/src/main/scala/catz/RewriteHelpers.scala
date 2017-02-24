package catz

import scala.collection.immutable.Seq
import scalafix.rewrite._
import scala.meta.contrib._
import scala.meta.{ Symbol => _, _ }
import scalafix.util.{ Patch, TokenPatch }
import scalafix.util.TreePatch._

object RewriteHelpers {
  //If import is found replace it
  def replaceGlobalImport(importer: Importer, replacement: Importer)(implicit tree: Tree): Seq[Patch] =
    tree
      .collectFirst {
        case i: Importer if i.structure == importer.structure =>
          Seq(
            AddGlobalImport(replacement),
            RemoveGlobalImport(importer)
          )
      }
      .getOrElse(Nil)

  def replaceGlobalImport(imports: (Importer, Importer))(implicit tree: Tree): Seq[Patch] =
    replaceGlobalImport(imports._1, imports._2)

  def replaceGlobalImports[T](imports: (Importer, Importer)*)(
    implicit code: Tree,
    rewriteCtx: RewriteCtx[T]
  ): Seq[Patch] =
    imports.toIndexedSeq.flatMap(replaceGlobalImport)

  //I am infering this could be a typeclass by the matching name and implicit modifier
  //This only works for explicitly typed definitions and is just a workaround until we have
  //Symbol.tpe: Type and Type.isSubtype(other: Type) which will come in future versions of Mirror.
  def typeclassDefns[T](simpleTypeName: String)(
    implicit code: Tree,
    rewriteCtx: RewriteCtx[T]
  ): Seq[Tree] =
    code.collect {
      case t @ Defn.Val(mods, _, Some(tpe), _)
          if mods.exists(_.syntax == "implicit") && tpe.syntax.contains(simpleTypeName) =>
        t
      case t @ Defn.Def(mods, _, _, _, Some(tpe), _)
          if mods.exists(_.syntax == "implicit") && tpe.syntax.contains(simpleTypeName) =>
        t
    }

  def replaceTypeclassMember[T](
    typeclass: String,
    memberMapping: (String, String)
  )(
    implicit code: Tree,
    rewriteCtx: RewriteCtx[T]
  ): Seq[Patch] =
    typeclassDefns(typeclass)
      .flatMap(_.collect {
        case t @ Defn.Def(mods, name, _, _, _, _) if name.syntax == memberMapping._1 =>
          TokenPatch.AddLeft(name.tokens.head, memberMapping._2) +: name.tokens
            .map(TokenPatch.Remove)
      })
      .flatten

}
