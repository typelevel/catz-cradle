package catz

import cats.Monoid

object MonoidEx1 {

  implicit val intAdditionMonoid: Monoid[Int] = new Monoid[Int] {
    def empty: Int = 0
    def combine(x: Int, y: Int): Int = x + y
  }

  val x = 1

  val combine1 = Monoid[Int].combine(x, Monoid[Int].empty)
  // combine1: Int = 1

  val combine2 = Monoid[Int].combine(Monoid[Int].empty, x)
  // combine2: Int = 1

}
