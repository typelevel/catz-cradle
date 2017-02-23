package catz

import cats.Semigroup
import cats.syntax.semigroup._

// Semigroup examples taken from https://github.com/typelevel/cats/blob/master/docs/src/main/tut/typeclasses/semigroup.md
object SemigroupEx1 {

  implicit val intAdditionSemigroup: Semigroup[Int] = new Semigroup[Int] {
    def combine(x: Int, y: Int): Int = x + y
  }

  val x = 1
  val y = 2
  val z = 3

  val combine1 = Semigroup[Int].combine(x, y)
  // combine1: Int = 3

  val combine2 = Semigroup[Int].combine(x, Semigroup[Int].combine(y, z))
  // combine2: Int = 6

  val combine3 = Semigroup[Int].combine(Semigroup[Int].combine(x, y), z)
  // combine3: Int = 6
}

object SemigroupEx2 {

  implicit val intAdditionSemigroup: Semigroup[Int] = new Semigroup[Int] {
    def combine(x: Int, y: Int): Int = x + y
  }

  val combine1 = 1 |+| 2
  // combine1: Int = 3

}

object SemigroupEx3 {
  import cats.instances.map._

  implicit val intAdditionSemigroup: Semigroup[Int] = new Semigroup[Int] {
    def combine(x: Int, y: Int): Int = x + y
  }

  val map1 = Map("hello" -> 0, "world" -> 1)
  val map2 = Map("hello" -> 2, "cats"  -> 3)

  val combineMap1 = Semigroup[Map[String, Int]].combine(map1, map2)
  // combineMap1: Map[String,Int] = Map(hello -> 2, cats -> 3, world -> 1)

  val combineMap2 = map1 |+| map2
  // combineMap2: Map[String,Int] = Map(hello -> 2, cats -> 3, world -> 1)

}

object SemigroupEx4 {
  import cats.instances.all._

  def optionCombine[A: Semigroup](a: A, opt: Option[A]): A =
    opt.map(a |+| _).getOrElse(a)

  def mergeMap[K, V: Semigroup](lhs: Map[K, V], rhs: Map[K, V]): Map[K, V] =
    lhs.foldLeft(rhs) {
      case (acc, (k, v)) => acc.updated(k, optionCombine(v, acc.get(k)))
    }

  val xm1 = Map('a' -> 1, 'b' -> 2)
  // xm1: scala.collection.immutable.Map[Char,Int] = Map(a -> 1, b -> 2)

  val xm2 = Map('b' -> 3, 'c' -> 4)
  // xm2: scala.collection.immutable.Map[Char,Int] = Map(b -> 3, c -> 4)

  val x = mergeMap(xm1, xm2)
  // x: Map[Char,Int] = Map(b -> 5, c -> 4, a -> 1)

  val ym1 = Map(1 -> List("hello"))
  // ym1: scala.collection.immutable.Map[Int,List[String]] = Map(1 -> List(hello))

  val ym2 = Map(2 -> List("cats"), 1 -> List("world"))
  // ym2: scala.collection.immutable.Map[Int,List[String]] = Map(2 -> List(cats), 1 -> List(world))

  val y = mergeMap(ym1, ym2)
  // y: Map[Int,List[String]] = Map(2 -> List(cats), 1 -> List(hello, world))


  val xb = Semigroup[Map[Char, Int]].combine(xm1, xm2) == x
  // xb: Boolean = true

  val yb = Semigroup[Map[Int, List[String]]].combine(ym1, ym2) == y
  // yb: Boolean = true

}

object SemigroupEx5 {
  import cats.instances.all._

  val leftwards = List(1, 2, 3).foldLeft(0)(_ |+| _)
  // leftwards: Int = 6

  val rightwards = List(1, 2, 3).foldRight(0)(_ |+| _)
  // rightwards: Int = 6

  val list = List(1, 2, 3, 4, 5)
  val (left, right) = list.splitAt(2)

  val sumLeft = left.foldLeft(0)(_ |+| _)
  // sumLeft: Int = 3

  val sumRight = right.foldLeft(0)(_ |+| _)
  // sumRight: Int = 12

  val result = sumLeft |+| sumRight
  // result: Int = 15

}
