package catz

import cats.Semigroup

// Semigroup ecamples taken from https://github.com/typelevel/cats/blob/master/docs/src/main/tut/typeclasses/semigroup.md
object SemigroupEx1 {

  implicit val intAdditionSemigroup: Semigroup[Int] = new Semigroup[Int] {
    def combine(x: Int, y: Int): Int = x + y
  }

  val x = 1
  val y = 2
  val z = 3

  val combine1 = Semigroup[Int].combine(x, y)

  val combine2 = Semigroup[Int].combine(x, Semigroup[Int].combine(y, z))

  val combine3 = Semigroup[Int].combine(Semigroup[Int].combine(x, y), z)
}
