package catz

import scalaz.Semigroup

// Semigroup ecamples taken from https://github.com/typelevel/cats/blob/master/docs/src/main/tut/typeclasses/semigroup.md
object SemigroupEx1 {

  implicit val intAdditionSemigroup: Semigroup[Int] = new Semigroup[Int] {
    def append(x: Int, y: => Int): Int = x + y
  }

  val x = 1
  val y = 2
  val z = 3

  val combine1 = Semigroup[Int].append(x, y)

  val combine2 = Semigroup[Int].append(x, Semigroup[Int].append(y, z))

  val combine3 = Semigroup[Int].append(Semigroup[Int].append(x, y), z)
}
