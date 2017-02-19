package catz

import scalaz.Functor
import scalaz.std.list._
import scalaz.std.option._

// Functor examples taken from https://github.com/typelevel/cats/blob/master/docs/src/main/tut/typeclasses/functor.md
object FunctorEx1 {

  val listOption1 = List(Some(1), None, Some(2))
  // listOption: List[Option[Int]] = List(Some(1), None, Some(2))

  // Through Functor#compose
  val listOption2 = Functor[List].compose[Option].map(listOption1)(_ + 1)

}
