package catz

class IdTests extends CatzSuite {

  test("IdEx1 Functor") {
    IdEx1.onePlusOne shouldBe 2
  }

  test("IdEx2 Monad Map") {
    IdEx2.mapOnePlusOne shouldBe 2
  }

  test("IdEx2 Monad Flatmap") {
    IdEx2.flatMapOnePlusOne shouldBe 2
  }

  test("IdEx3 Coflatmap") {
    IdEx3.coflatMapOnePlusOne shouldBe 2
  }

}
