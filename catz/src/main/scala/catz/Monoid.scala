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

object MonoidEx2 {
  import cats.instances.all._

  // Written here, provided in Monoid.combineAll
  def combineAll[A: Monoid](as: List[A]): A =
    as.foldLeft(Monoid[A].empty)(Monoid[A].combine)

  val combineAll1 = combineAll(List(1, 2, 3))
  // combineAll1: Int = 6

  val combineAll2 = combineAll(List("hello", " ", "world"))
  // combineAll2: String = hello world

  val combineAll3 = combineAll(List(Map('a' -> 1), Map('a' -> 2, 'b' -> 3), Map('b' -> 4, 'c' -> 5)))
  // combineAll3: scala.collection.immutable.Map[Char,Int] = Map(b -> 7, c -> 5, a -> 3)

  val combineAll4 = combineAll(List(Set(1, 2), Set(2, 3, 4, 5)))
  // combineAll4: scala.collection.immutable.Set[Int] = Set(5, 1, 2, 3, 4)

}
