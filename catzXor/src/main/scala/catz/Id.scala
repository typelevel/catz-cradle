package catz

object IdEx1 {
  import cats.Id
  import cats.Functor

  val one : Int = 1
  val onePlusOne: Id[Int] = Functor[Id].map(one)(_ + 1)
  // onePlusOne: cats.Id[Int] = 2
}

object IdEx2 {
  import cats.Id
  import cats.Monad

  val one : Int = 1
  val mapOnePlusOne : Id[Int] = Monad[Id].map(one)(_ + 1)
  // mapOnePlusOne: cats.Id[Int] = 2
  val flatMapOnePlusOne : Id[Int] = Monad[Id].flatMap(one)(_ + 1)
  // flatMapOnePlusOne: cats.Id[Int] = 2
}

object IdEx3 {
  import cats.Id
  import cats.Comonad

  val one : Int = 1
  val coflatMapOnePlusOne : Id[Int] = Comonad[Id].coflatMap(one)(_ + 1)
  // coflatMapOnePlusOne: cats.Id[Int] = 2
}
