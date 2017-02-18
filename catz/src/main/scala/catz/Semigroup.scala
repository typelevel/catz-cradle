package catz

import cats.Semigroup


object SemigroupEx1 {

  implicit val intAdditionSemigroup: Semigroup[Int] = new Semigroup[Int] {
    def combine(x: Int, y: Int): Int = x + y
  }

  val x = 1
  val y = 2
  val z = 3

}
