package catz

import cats.Functor
import cats.instances.list._
import cats.instances.option._


object FunctorEx1 {

  val listOption = List(Some(1), None, Some(2))
  // listOption: List[Option[Int]] = List(Some(1), None, Some(2))

  // Through Functor#compose
  Functor[List].compose[Option].map(listOption)(_ + 1)

}
