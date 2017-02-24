package catz

import cats.Functor
import cats.instances.list._
import cats.instances.option._
import cats.data.Nested
import cats.syntax.functor._

// Functor examples taken from https://github.com/typelevel/cats/blob/master/docs/src/main/tut/typeclasses/functor.md
object FunctorEx1 {

  val listOption1 = List(Some(1), None, Some(2))
  // listOption1: List[Option[Int]] = List(Some(1), None, Some(2))

  // Through Functor#compose
  val listOption2 = Functor[List].compose[Option].map(listOption1)(_ + 1)
  // listOption2: List[Option[Int]] = List(Some(2), None, Some(3))

}

object FunctorEx2 {

  val listOption = List(Some(1), None, Some(2))

  val nested1 = Nested(listOption)
  // nested1: cats.data.Nested[List,Option,Int] = Nested(List(Some(1), None, Some(2)))

  val nested2 = nested1.map(_ + 1)
  // nested2: cats.data.Nested[[+A]List[A],Option,Int] = Nested(List(Some(2), None, Some(3)))

}
