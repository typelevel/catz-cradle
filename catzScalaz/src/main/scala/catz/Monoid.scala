package catz

object MonoidEx1 {
  import scalaz.Monoid

  implicit val intAdditionMonoid: Monoid[Int] = new Monoid[Int] {
    def zero: Int = 0
    def append(x: Int, y: => Int): Int = x + y
  }

  val x = 1

  val combine1 = Monoid[Int].append(x, Monoid[Int].zero)
  // combine1: Int = 1

  val combine2 = Monoid[Int].append(Monoid[Int].zero, x)
  // combine2: Int = 1

}

object MonoidEx2 {
  import scalaz.Monoid
  import scalaz._
  import Scalaz._

  def combineAll[A: Monoid](as: List[A]): A =
    as.foldl(Monoid[A].zero){ acc => a => Monoid[A].append(acc, a) }

  val combineAll1 = combineAll(List(1, 2, 3))
  // combineAll1: Int = 6

  val combineAll2 = combineAll(List("hello", " ", "world"))
  // combineAll2: String = hello world

  val combineAll3 = combineAll(List(Map('a' -> 1), Map('a' -> 2, 'b' -> 3), Map('b' -> 4, 'c' -> 5)))
  // combineAll3: scala.collection.immutable.Map[Char,Int] = Map(b -> 7, c -> 5, a -> 3)

  val combineAll4 = combineAll(List(Set(1, 2), Set(2, 3, 4, 5)))
  // combineAll4: scala.collection.immutable.Set[Int] = Set(5, 1, 2, 3, 4)

}

object MonoidEx3 {
  import scalaz.Monoid
  import scalaz.NonEmptyList
  import scalaz._
  import Scalaz._

  // As Monoid.combineAll is unavailable so we rewrite combineAll
  def combineAll[A: Monoid](as: List[A]): A =
    as.foldl(Monoid[A].zero){ acc => a => Monoid[A].append(acc, a) }

  val list1 = List(NonEmptyList(1, 2, 3), NonEmptyList(4, 5, 6))
  val lifted1 = list1.map(nel => Option(nel))

  val optionMonoid1 = combineAll(lifted1)
  // optionMonoid1: Option[scalaz.NonEmptyList[Int]] = Some(NonEmptyList(1, 2, 3, 4, 5, 6))

  val list2 = List.empty[NonEmptyList[Int]]
  val lifted2 = list2.map(nel => Option(nel))

  val optionMonoid2 = combineAll(lifted2)
  // optionMonoid2: Option[scalaz.NonEmptyList[Int]] = None

  // optionMonoid3 must be done manually.
  // Internal assignment evaluates call by name.
  def semigroupCombineAllOption[A: Semigroup](as: TraversableOnce[A]): Option[A] = {
    as.reduceOption{ (acc, b) =>
      val a = Semigroup[A].append(acc, b)
      a
    }
  }
  val list3 = List(NonEmptyList(1, 2, 3), NonEmptyList(4, 5, 6))
  val optionMonoid3 = semigroupCombineAllOption(list3)

}
