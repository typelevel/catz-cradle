package catz

// Monad Examples Taken From http://typelevel.org/cats/typeclasses/monad.html
object MonadEx1 {

  val flatten1 : Option[Int] = Option(Option(1)).flatten
  // flatten1: Option[Int] = Some(1)
  val flatten2 : Option[Int] = Option(None).flatten
  //  flatten2: Option[Nothing] = None
  val flatten3 : List[Int] = List(List(1), List(2,3)).flatten
  // flatten3: List[Int] = List(1,2,3)

}

object MonadEx2 {
  import scalaz._
  import Scalaz._

  val ifM : List[Int] = Monad[List].ifM(List(true, false, true), List(1,2), List(3,4))

}
