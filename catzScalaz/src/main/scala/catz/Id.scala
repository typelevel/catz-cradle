package catz

object IdEx1 {
  import scalaz._
  import Scalaz._

  val one : Int = 1
  val onePlusOne: Id[Int] = Functor[Id].map(one)(_ + 1)
  // onePlusOne: scalaz.Scalaz.Id[Int] = 2
}

object IdEx2 {
  import scalaz._
  import Scalaz._

  val one : Int = 1
  val mapOnePlusOne : Id[Int] = Monad[Id].map(one)(_ + 1)
  // mapOnePlusOne: scalaz.Scalaz.Id[Int] = 2
  val flatMapOnePlusOne : Id[Int] = Monad[Id].bind(one)(_ + 1)
  // flatMapOnePlusOne: scalaz.Scalaz.Id[Int] = 2
}

object IdEx3 {
  import scalaz._
  import Scalaz._

  val one : Int = 1
  val coflatMapOnePlusOne : Id[Int] = Comonad[Id].cobind(one)(_ + 1)
  // coflatMapOnePlusOne: scalaz.Scalaz.Id[Int] = 2
}
